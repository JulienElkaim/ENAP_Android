package com.example.julienelkaim.testyoutube.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.toolbox.Constants;

public class YoutubeDispatcherActivity extends AppCompatActivity {

    ImageButton YtbeChildDplButton;
    ImageButton YtbePlaylistsrchButton;

    @Override
    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Default initiation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_dispatcher);

        //Init Button
        YtbeChildDplButton = findViewById(R.id.YtbeChildDpl_Button);
        YtbeChildDplButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se connecter a l'activité lecteur youtube
                Intent myGame = new Intent(YoutubeDispatcherActivity.this, YoutubeChildDisplayerActivity.class);
                startActivity(myGame);
            }
        });
        YtbePlaylistsrchButton = findViewById(R.id.YtbePlaylistSrch_Button);
        YtbePlaylistsrchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Reaction pour ALLER Aprochaine fenetre possible
                Intent mySearcher = new Intent(YoutubeDispatcherActivity.this, YoutubePlaylistResearchActivity.class);
                startActivity(mySearcher);
            }
        });

    }

}
