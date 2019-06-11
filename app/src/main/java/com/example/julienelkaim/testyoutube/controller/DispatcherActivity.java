package com.example.julienelkaim.testyoutube.controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 *
 *  MAIN activity of ENAP project.
 */
public class DispatcherActivity extends AppCompatActivity {

    private Button logOut;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onRestart() { super.onRestart(); }


    @Override
    protected void onStart() {
        super.onStart();
        GlobalBox.windowAndSystemSettings(this);

        // Initialize Firebase Auth, Firebase database reference
        mAuth = FirebaseAuth.getInstance();

        //Deconnexion
        logOut = findViewById(R.id.deconnexionButton);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                RequestForAuth();
            }
        });

        currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            RequestForAuth();
        }
        //else : remettre la fonction pour afficher l'image et l'utilisateur, voir l'episode 4
        // de Chap App with Firebase KOD Dev

    }

    /**
     * No Auth recognized, request for authentication
     *
     */
    public void RequestForAuth(){
        startActivity(new Intent(DispatcherActivity.this, AuthActivity.class));
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
                Intent myGame = new Intent(DispatcherActivity.this, com.example.julienelkaim.testyoutube.controller.Youtube.DispatcherActivity.class);
                startActivity(myGame);
            }
        });

        //Wikipedia Button
        ImageButton wikiButton = findViewById(R.id.Wiki_Button);
        wikiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent myGame = new Intent(DispatcherActivity.this, com.example.julienelkaim.testyoutube.controller.Wikipedia.WikiMenu.class);
                startActivity(myGame);
            }
        });

        //Site web chENAPan Button
        ImageButton chenapanButton = findViewById(R.id.Chenapan_Button);
        chenapanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.chenapan.eu")));
            }
        });


        //Message Button
        ImageButton messageButton = findViewById(R.id.Mail_Button);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DispatcherActivity.this,ContactsActivity.class));
            }
        });

    }


}
