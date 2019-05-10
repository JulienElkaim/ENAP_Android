package com.example.julienelkaim.testyoutube.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;

    private Button mConnexionButton;

    private ProgressDialog mLoginProgress;

    @Override
    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Instanciation FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        mLoginProgress = new ProgressDialog(this);

        mLoginEmail = (TextInputLayout) findViewById(R.id.insertEmail);
        mLoginPassword = (TextInputLayout) findViewById(R.id.insertPassword);

        mConnexionButton = (Button) findViewById(R.id.connexion_Button);

        mConnexionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = mLoginEmail.getEditText().getText().toString();
                String password = mLoginPassword.getEditText().getText().toString();

                if ( !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) ){

                    mLoginProgress.setTitle("Identification ...");
                    mLoginProgress.setMessage("Veuillez patienter pendant que nous vérifions vos identifiants");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    loginUser(email,password);
                }
            }
        });

    }

    private void loginUser(String email, String password){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){

                    mLoginProgress.dismiss();

                    Intent mainIntent = new Intent(LoginActivity.this, MainDispatcherActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
                else {

                    mLoginProgress.hide();
                    Toast.makeText(LoginActivity.this,"Erreur d'authentification",Toast.LENGTH_LONG).show();

                }
            }
        });

    }
}