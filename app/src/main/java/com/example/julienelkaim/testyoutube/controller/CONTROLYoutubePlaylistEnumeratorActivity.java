package com.example.julienelkaim.testyoutube.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.adapter.PlaylistListAdapter;
import com.example.julienelkaim.testyoutube.model.Playlist;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.example.julienelkaim.testyoutube.toolbox.YoutubeHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class CONTROLYoutubePlaylistEnumeratorActivity extends AppCompatActivity {


    ListView mListView;
    ArrayList<Playlist> mPlaylistArrayList;
    PlaylistListAdapter mPlaylistListAdapter;/* TO DO: ADAPT THE CODE*/


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Constants.windowAndSystemSettings(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setMyPlaylistList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_youtube_playlist_enumerator);
        mListView = findViewById(R.id.listOfVideos);
        ImageButton launchCreate = findViewById(R.id.playlist_launch_create_process);
        launchCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCreatePlaylistActivity();
            }
        });


        setMyPlaylistList();

    }

    private void launchCreatePlaylistActivity() {
        startActivity(new Intent(this, YOUTUBECONTROLPlaylistCreatorActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        YoutubeHelper.saveListOfPlaylist(this , mPlaylistArrayList);
    }

    private void setMyPlaylistList() {
        mPlaylistArrayList = YoutubeHelper.retrieveListOfPlaylist(this);
        mPlaylistListAdapter = new PlaylistListAdapter(this, mPlaylistArrayList);

        mListView.setAdapter(mPlaylistListAdapter);
        mPlaylistListAdapter.notifyDataSetChanged();
    }


}
