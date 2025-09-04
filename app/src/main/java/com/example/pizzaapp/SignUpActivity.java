package com.example.pizzaapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etFullName, etEmail, etPhone, etPassword, etConfirmPassword;
    private Button btnSignUp;
    private TextView tvGoToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Bind views (IDs must match your XML)
        etFullName        = findViewById(R.id.etFullName);
        etEmail           = findViewById(R.id.etEmail);
        etPhone           = findViewById(R.id.etPhone);
        etPassword        = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSignUp         = findViewById(R.id.btnSignUp);
        tvGoToLogin       = findViewById(R.id.tvGoToLogin);

        btnSignUp.setOnClickListener(v -> attemptSignUp());
        tvGoToLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void attemptSignUp() {
        // Reset errors (optional)
        clearFieldErrors();

        String name     = safeText(etFullName);
        String email    = safeText(etEmail);
        String phone    = safeText(etPhone);
        String pass     = safeText(etPassword);
        String confirm  = safeText(etConfirmPassword);

        // Validate inputs
        if (TextUtils.isEmpty(name)) {
            etFullName.setError("Full name is required");
            etFullName.requestFocus();
            return;
        }
        if (!isValidEmail(email)) {
            etEmail.setError("Enter a valid email");
            etEmail.requestFocus();
            return;
        }
        if (!isValidPhone(phone)) {
            etPhone.setError("Enter a valid phone (7–15 digits)");
            etPhone.requestFocus();
            return;
        }
        if (pass.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return;
        }
        if (!pass.equals(confirm)) {
            etConfirmPassword.setError("Passwords do not match");
            etConfirmPassword.requestFocus();
            return;
        }

        // TODO: Replace with real registration (Firebase/API/SQLite)
        // e.g., createUserWithEmailAndPassword(email, pass) { ... }

        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();

        // After successful sign up, go to Login (or MainActivity if you auto-login)
        Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
        // Optionally pass email back to prefill login
        i.putExtra("prefill_email", email);
        startActivity(i);
        finish();
    }

    // Helpers

    private String safeText(EditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPhone(String phone) {
        // Basic numeric + length check (7–15 digits). Adjust for your locale if needed.
        if (TextUtils.isEmpty(phone)) return false;
        String digitsOnly = phone.replaceAll("\\D+", "");
        return digitsOnly.length() >= 7 && digitsOnly.length() <= 15;
    }

    private void clearFieldErrors() {
        etFullName.setError(null);
        etEmail.setError(null);
        etPhone.setError(null);
        etPassword.setError(null);
        etConfirmPassword.setError(null);
    }
}
