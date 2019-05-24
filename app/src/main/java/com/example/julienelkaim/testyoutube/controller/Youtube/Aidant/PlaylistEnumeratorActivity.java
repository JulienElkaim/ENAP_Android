package com.example.julienelkaim.testyoutube.controller.Youtube.Aidant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.adapter.Youtube.PlaylistListAdapter;
import com.example.julienelkaim.testyoutube.controller.Youtube.MotherActivity.PlaylistListDisplayerActivity;
import com.example.julienelkaim.testyoutube.model.Youtube.Playlist;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.example.julienelkaim.testyoutube.toolbox.Youtube.YoutubeBox;

import java.util.ArrayList;

public class PlaylistEnumeratorActivity extends PlaylistListDisplayerActivity {


    ListView mListView;
    ArrayList<Playlist> mPlaylistArrayList;
    PlaylistListAdapter mPlaylistListAdapter;/* TO DO: ADAPT THE CODE*/


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        GlobalBox.windowAndSystemSettings(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBox.windowAndSystemSettings(this);

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
                startActivity(new Intent(PlaylistEnumeratorActivity.this, PlaylistCreatorActivity.class));
            }
        });


        setMyPlaylistList();

    }

    @Override
    protected void onStop() {
        super.onStop();
        YoutubeBox.saveListOfPlaylist(this , mPlaylistArrayList);
    }

    /**
     * @author Julien Elkaim
     *
     * Initialize list of playlist displayed.
     */
    @Override
    public void setMyPlaylistList() {
        mPlaylistArrayList = YoutubeBox.retrieveListOfPlaylist(this);
        mPlaylistListAdapter = new PlaylistListAdapter(this, mPlaylistArrayList);

        mListView.setAdapter(mPlaylistListAdapter);
        mPlaylistListAdapter.notifyDataSetChanged();

        TextView emptyText = findViewById(android.R.id.empty);
        if (emptyText != null) {
            System.out.println("DEBUGGO::: On a vu que la liste de playlist pas null");
            mListView.setEmptyView(emptyText);
            if (mPlaylistArrayList.size() != 0){ ((ViewGroup) emptyText.getParent()).removeView(emptyText); }
        }


    }


}
