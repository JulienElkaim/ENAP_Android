package com.example.julienelkaim.testyoutube.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.Playlist;
import com.example.julienelkaim.testyoutube.toolbox.YoutubeHelper;

import java.util.ArrayList;

public class YOUTUBECONTROLPlaylistCreatorActivity extends AppCompatActivity {
    Button mButtonCreate;
    EditText mEditTextTitle;
    EditText mEditTextDescription;

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
                    Toast.makeText(YOUTUBECONTROLPlaylistCreatorActivity.this, "Veuillez remplir les champs titre et description.", Toast.LENGTH_SHORT).show();
                }else{
                    //Creer la playlist et l'ajouter
                    ArrayList<Playlist> mesPlaylists = YoutubeHelper.retrieveListOfPlaylist(YOUTUBECONTROLPlaylistCreatorActivity.this);
                    System.out.println("BUG::: ON va ajouter la new playlist. il y a " + mesPlaylists.size() + " playlists dans la liste.");
                    mesPlaylists.add(
                            new Playlist(YoutubeHelper.provideUniqueId(YOUTUBECONTROLPlaylistCreatorActivity.this), mEditTextTitle.getText().toString(),mEditTextDescription.getText().toString() )
                    );
                    System.out.println("BUG::: ON va save. il y a " + mesPlaylists.size() + " playlists dans la liste.");
                    YoutubeHelper.saveListOfPlaylist(YOUTUBECONTROLPlaylistCreatorActivity.this ,mesPlaylists);
                    System.out.println("BUG::: ON a fini de save");
                    onBackPressed(); //finish activity and go back to previous activity. Better than finish that will open a "copy".
                    //finish();

                }
            }
        });
    }
}
