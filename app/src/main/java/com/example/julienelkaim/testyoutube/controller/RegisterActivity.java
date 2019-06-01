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
import android.widget.EditText;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.User;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ScheduledExecutorService;

public class RegisterActivity extends AppCompatActivity {

    public EditText mDisplayName,mEmail,mPassword;
    public Button mSubmitAccount;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    private ProgressDialog mRegProgress;


    @Override
    protected void onStart() {
        super.onStart();
        GlobalBox.windowAndSystemSettings(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Linking ressources
        mDisplayName = findViewById(R.id.registerName);
        mEmail = findViewById(R.id.registerEmail);
        mPassword = findViewById(R.id.registerPassword);
        mSubmitAccount = findViewById(R.id.submitAccount);

        // Progress Dialog
        mRegProgress= new ProgressDialog(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // S'enregistrer
        mSubmitAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String display_name = Objects.requireNonNull(mDisplayName).getText().toString();
                String email = Objects.requireNonNull(mEmail).getText().toString();
                String password = Objects.requireNonNull(mPassword).getText().toString();

                if ( !TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password) ) {
                    mRegProgress.setTitle("Enregistrement de l'utilisateur");
                    mRegProgress.setMessage("Veuillez patienter ...");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    registerUser(display_name, email, password);
                }
            }
        });

    }

    private void registerUser(final String display_name, final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull final Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    mRegProgress.dismiss();

                    final FirebaseUser firebaseUser = mAuth.getCurrentUser();
                    assert firebaseUser != null;
                    String userId = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.push().setValue(new User(userId,display_name,email));

                    Intent mainIntent = new Intent(RegisterActivity.this, DispatcherActivity.class);
                    startActivity(mainIntent);
                    Toast.makeText(RegisterActivity.this, "Bienvenue, "+display_name+" !", Toast.LENGTH_SHORT).show();
                    finish();

                } else {

                    mRegProgress.dismiss();
                    Toast.makeText(RegisterActivity.this, "Erreur", Toast.LENGTH_LONG).show();
                    Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());

                }
            }
        });
    }
}
