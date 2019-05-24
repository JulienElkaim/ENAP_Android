package com.example.julienelkaim.testyoutube.controller.Youtube.Aidant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.Youtube.Playlist;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.example.julienelkaim.testyoutube.toolbox.Youtube.YoutubeBox;

import java.util.ArrayList;

public class PlaylistCreatorActivity extends AppCompatActivity {
    Button mButtonCreate;
    EditText mEditTextTitle;
    EditText mEditTextDescription;

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBox.windowAndSystemSettings(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtubecontrolplaylist_creator);

        mButtonCreate = findViewById(R.id.playlist_create_btn);
        mEditTextTitle = findViewById(R.id.playlist_create_title);
        mEditTextDescription = findViewById(R.id.playlist_create_description);

        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mEditTextDescription.getText().toString().equals("") || mEditTextTitle.getText().toString().equals("") ){
                    Toast.makeText(PlaylistCreatorActivity.this, "Veuillez remplir les champs titre et description.", Toast.LENGTH_SHORT).show();
                }else{
                    //Creer la playlist et l'ajouter
                    ArrayList<Playlist> mesPlaylists = YoutubeBox.retrieveListOfPlaylist(PlaylistCreatorActivity.this);
                    mesPlaylists.add(
                            new Playlist(YoutubeBox.provideUniqueId(PlaylistCreatorActivity.this), mEditTextTitle.getText().toString(),mEditTextDescription.getText().toString() )
                    );
                    YoutubeBox.saveListOfPlaylist(PlaylistCreatorActivity.this ,mesPlaylists);
                    onBackPressed();


                }
            }
        });
    }
}
