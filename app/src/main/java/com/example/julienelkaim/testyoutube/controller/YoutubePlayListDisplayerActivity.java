package com.example.julienelkaim.testyoutube.controller;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.example.julienelkaim.testyoutube.toolbox.YoutubeHelper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubePlayListDisplayerActivity extends YouTubeBaseActivity {
    private YouTubePlayerView mYouTubePlayerView; // View encapsulating YouTube Player.
    private String mVideoId;

    //================================ LISTENER -> YouTubePlayer: Initialisation ================================
    private YouTubePlayer.OnInitializedListener mPlayerInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            // YouTube Player.
            mYouTubePlayerView.setVisibility(View.VISIBLE);
            youTubePlayer.loadVideo(mVideoId);
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            System.out.println("PlayerInitializer::onFailure");/*Si ca rate*/
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video_details);
        mYouTubePlayerView = findViewById(R.id.youtubePlayerView);
        mYouTubePlayerView.initialize(Constants.API_KEY, mPlayerInitializedListener);
        mVideoId = getIntent().getStringExtra(Constants.YOUTUBE_VIDEO_ID_FROM_RESEARCH);

        Button backButton = findViewById(R.id.Ytbe_Return_To_Research);
        backButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Button addPlaylistButton = findViewById(R.id.Ytbe_Add_To_Playlist);
        addPlaylistButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoutubeHelper.modifyPlaylistListOfVideo(YoutubePlayListDisplayerActivity.this, Constants.YOUTUBE_PLAYLIST_ID_IN_MODIFICATION, mVideoId);
                Toast.makeText(YoutubePlayListDisplayerActivity.this, "Video ajoutée à la playlist!", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });
        String mode = getIntent().getStringExtra(Constants.YOUTUBE_DISPLAYER_MODE);

         if (mode.equals("VIEW")){
             ((ViewGroup) addPlaylistButton.getParent()).removeView(addPlaylistButton);

         }

    }




}
