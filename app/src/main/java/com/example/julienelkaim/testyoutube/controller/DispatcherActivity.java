package com.example.julienelkaim.testyoutube.controller;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.User;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DispatcherActivity extends AppCompatActivity {


    private Button logOut;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;
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
                sendToStart();
            }
        });

        currentUser = mAuth.getCurrentUser();
        if (currentUser == null){
            //sendToStart();        /* REMETTRE QUAND ON EST EN PROD !*/
        }
    }

    public void sendToStart(){

        Intent startIntent = new Intent(DispatcherActivity.this, AuthActivity.class);
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
                Intent myGame = new Intent(DispatcherActivity.this, com.example.julienelkaim.testyoutube.controller.Youtube.DispatcherActivity.class);
                startActivity(myGame);
            }
        });

        //Site chenapan Button
        ImageButton chenapanButton = findViewById(R.id.Chenapan_Button);
        chenapanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.chenapan.eu")));
            }
        });




    }


}
