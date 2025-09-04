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

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(SplashActivity.this, LoginActivity.class))
        );

        btnSignUp.setOnClickListener(v ->
                startActivity(new Intent(SplashActivity.this, SignUpActivity.class))
        );
    }
}
