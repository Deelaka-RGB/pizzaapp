package com.example.pizzaapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button btnLogin  = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        // ✅ Open Login and force it to show even if a user is already signed in
        btnLogin.setOnClickListener(v -> {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            i.putExtra("forceLogin", true); // 👈 important line
            startActivity(i);
        });

        // Open Sign Up normally
        btnSignUp.setOnClickListener(v ->
                startActivity(new Intent(SplashActivity.this, SignUpActivity.class)));
    }
}
