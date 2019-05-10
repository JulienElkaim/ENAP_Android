package com.example.julienelkaim.testyoutube.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainDispatcherActivity extends AppCompatActivity {
    ImageButton YtbeButton;

    private FirebaseAuth mAuth;
    private Button mLogOut;

    @Override
    protected void onRestart() { super.onRestart(); }


    @Override
    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);

        // Authentification

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){
            sendToStart();
        }
    }

    public void sendToStart(){

        Intent startIntent = new Intent(MainDispatcherActivity.this,StartAuthActivity.class);
        startActivity(startIntent);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Default initiation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);

        //Youtube Button
        YtbeButton = findViewById(R.id.Ytbe_Button);
        YtbeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myGame = new Intent(MainDispatcherActivity.this, YoutubeDispatcherActivity.class);
                startActivity(myGame);
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        mLogOut = findViewById(R.id.deconnexion_Button);
        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                sendToStart();
            }
        });

    }


}
