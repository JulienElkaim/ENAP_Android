package com.example.julienelkaim.testyoutube.controller.Youtube;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.controller.Youtube.Aidant.PlaylistEnumeratorActivity;
import com.example.julienelkaim.testyoutube.controller.Youtube.Child.DisplayerActivity;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;

public class DispatcherActivity extends AppCompatActivity {
    ImageButton YtbeChildDplButton;
    ImageButton YtbePlaylistsrchButton;

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBox.windowAndSystemSettings(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_dispatcher);


        YtbeChildDplButton = findViewById(R.id.YtbeChildDpl_Button);
        YtbeChildDplButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se connecter a l'activité lecteur youtube
                Intent myGame = new Intent(DispatcherActivity.this, DisplayerActivity.class);
                startActivity(myGame);
            }
        });
        YtbePlaylistsrchButton = findViewById(R.id.YtbePlaylistSrch_Button);
        YtbePlaylistsrchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myPlaylists = new Intent(DispatcherActivity.this, PlaylistEnumeratorActivity.class);
                startActivity(myPlaylists);

            }
        });

    }

}
