package com.example.julienelkaim.testyoutube.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    public TextInputLayout mDisplayName;
    public TextInputLayout mEmail;
    public TextInputLayout mPassword;
    public Button mSubmitAccount;

    private FirebaseAuth mAuth;

    private ProgressDialog mRegProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDisplayName = findViewById(R.id.registerName);
        mEmail = findViewById(R.id.registerEmail);
        mPassword = findViewById(R.id.registerPassword);
        mSubmitAccount = (Button) findViewById(R.id.submitAccount);

        // Progress Dialog
        mRegProgress= new ProgressDialog(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Se connecter
        mSubmitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String display_name = mDisplayName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if ( !TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) ){
                    mRegProgress.setTitle("Enregistrement de l'utilisateur");
                    mRegProgress.setMessage("Veuillez patienter ...");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();
                    registerUser(display_name, email, password);
                }
            }
        });

    }

    private void registerUser(String display_name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    mRegProgress.dismiss();

                    Intent mainIntent = new Intent(RegisterActivity.this, MainDispatcherActivity.class);
                    startActivity(mainIntent);
                    finish();

                } else {

                    mRegProgress.dismiss();
                    Toast.makeText(RegisterActivity.this, "Erreur", Toast.LENGTH_LONG).show();
                    Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                    ;
                }
            }
        });
    }
}
