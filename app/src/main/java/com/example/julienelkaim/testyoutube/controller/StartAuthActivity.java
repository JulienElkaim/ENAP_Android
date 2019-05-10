package com.example.julienelkaim.testyoutube.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.toolbox.Constants;

public class StartAuthActivity extends AppCompatActivity {

    private Button registerButton;
    private Button connexionButton;


    @Override
    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);
    }

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_auth);

        // Création de compte
        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(StartAuthActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();

            }
        });

        // S'identifier
        connexionButton = findViewById(R.id.connexionButton);
        connexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent connexionIntent = new Intent(StartAuthActivity.this, LoginActivity.class);
                startActivity(connexionIntent);
                finish();

            }
        });
    }
}
