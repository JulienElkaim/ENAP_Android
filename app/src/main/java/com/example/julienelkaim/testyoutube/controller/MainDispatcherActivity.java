package com.example.julienelkaim.testyoutube.controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class MainDispatcherActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;


    @Override
    protected void onRestart() { super.onRestart(); }


    @Override
    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);

        // Authentification

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){
            //sendToStart();        /* REMETTRE QUAND ON EST EN PROD !*/
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
        ImageButton ytbeButton = findViewById(R.id.Ytbe_Button);
        ytbeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myGame = new Intent(MainDispatcherActivity.this, YoutubeDispatcherActivity.class);
                startActivity(myGame);
            }
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        Button logOut = findViewById(R.id.deconnexion_Button);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                sendToStart();
            }
        });

        ImageButton chenapanButton = findViewById(R.id.Chenapan_Button);
        chenapanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.chenapan.eu")));
            }
        });


    }


}
