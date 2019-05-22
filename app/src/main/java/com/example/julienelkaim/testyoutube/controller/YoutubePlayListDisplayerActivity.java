package com.example.julienelkaim.testyoutube.controller;

import android.content.SharedPreferences;
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

import java.util.List;
import java.util.Set;

public class YoutubePlayListDisplayerActivity extends YouTubeBaseActivity {
    private YouTubePlayer mYouTubePlayer; // YouTube Player.
    private YouTubePlayerView mYouTubePlayerView; // View encapsulating YouTube Player.
    private String mVideoId;
    private Button mBackButton;
    private Button mAddPlaylistButton;
    private SharedPreferences mPreferences;
    private List<String> mVideoList ;
    private String mMode;


    //================================ LISTENER -> YouTubePlayer: Initialisation ================================
    private YouTubePlayer.OnInitializedListener mPlayerInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            mYouTubePlayer = youTubePlayer;
            mYouTubePlayerView.setVisibility(View.VISIBLE);
            mYouTubePlayer.loadVideo(mVideoId);
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
        mPreferences =getSharedPreferences(Constants.YOUTUBE_SHARED_PREFERENCES,MODE_PRIVATE);
        mVideoList = YoutubeHelper.setListFromSet(mPreferences.getStringSet(Constants.YOUTUBE_PLAYLIST_CURRENTLY,null));

        mBackButton = findViewById(R.id.Ytbe_Return_To_Research);
        mBackButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mAddPlaylistButton = findViewById(R.id.Ytbe_Add_To_Playlist);
        mAddPlaylistButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                YoutubeHelper.modifyPlaylistListOfVideo(YoutubePlayListDisplayerActivity.this, Constants.YOUTUBE_PLAYLIST_ID_IN_MODIFICATION, mVideoId);
                Toast.makeText(YoutubePlayListDisplayerActivity.this, "Video ajoutée à la playlist!", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });
        mMode = getIntent().getStringExtra(Constants.YOUTUBE_DISPLAYER_MODE);

         if (mMode.equals("VIEW")){
             ((ViewGroup) mAddPlaylistButton.getParent()).removeView(mAddPlaylistButton);

         }

    }




}
