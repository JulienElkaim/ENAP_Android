package com.example.julienelkaim.testyoutube.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.Playlist;
import com.example.julienelkaim.testyoutube.model.VideoHandler;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.example.julienelkaim.testyoutube.toolbox.YoutubeHelper;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import java.util.List;



public class YoutubeChildDisplayerActivity extends YouTubeBaseActivity {
    /*SHA1: "F7:E1:3F:C3:1E:91:1A:2D:BB:65:AA:B7:D2:69:8C:7A:14:14:2D:4F" AUTH key */
    //================================ Params ================================
    private YouTubePlayer mYouTubePlayer; // YouTube Player.
    private YouTubePlayerView mYouTubePlayerView; // View encapsulating YouTube Player.
    private VideoHandler mVideoHandler;
    private LinearLayout mLinearLayout;
    private ImageButton mPlayPauseButton;


    //================================ LISTENER -> YouTubePlayerView: On Click ================================
    private YouTubePlayerView.OnClickListener mYouTubePlayerViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            System.out.println("YoutubePlayerView::onClick");
        }
    };

    //================================ LISTENER -> YouTubePlayer: Initialisation ================================
    private YouTubePlayer.OnInitializedListener mPlayerInitializedListener = new YouTubePlayer.OnInitializedListener() {
        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            mYouTubePlayer = youTubePlayer;
            initializePlayerParams(); // Paramétrer le player
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            System.out.println("PlayerInitializer::onFailure");/*Si ca rate*/
        }
    };

    //================================ LISTENER -> YouTubePlayer: Modification d'état ================================
    private YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {
            mYouTubePlayerView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onLoaded(String s) {
            mYouTubePlayerView.setVisibility(View.VISIBLE);
            headerHandler(mLinearLayout, mVideoHandler);
        }

        @Override
        public void onAdStarted() {
            //mYouTubePlayer.loadVideo(mVideoHandler.getPlayingVideoId()); //ACTIVER SI PUB PRESENTE
        }

        @Override
        public void onVideoStarted() {
            System.out.println("PlayerState::onVideoStarted");
        }

        @Override
        public void onVideoEnded() {
            mYouTubePlayerView.setVisibility(View.INVISIBLE); //Contrer le problème des suggestions de fin
            mYouTubePlayer.loadVideo(mVideoHandler.nextVideo());
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
        }
    };


    /**
     * Set FullScreen mode when starting the activity.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);
    }


    /**
     * Create the activity and initialize every elements of the scene.
     *
     * @param savedInstanceState is the previous state saved if we had to destroy the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState); // Default create method for an activity.
        initializeLinksWithView();// Créer le lien avec les éléments graphique mutables.
        initializeButtonsOnView();// Initialise les comportements de click

        Playlist mPlaylist = YoutubeHelper.retrieveCurrentPlaylist(this);
        List<String> videoList = mPlaylist.getVideoIdList();


        // Autre action

        mVideoHandler = new VideoHandler(videoList);

        initializePlayer();
        headerHandler(mLinearLayout, mVideoHandler);
    }


    /**
     * Link Views with activity params to handle them.
     */
    private void initializeLinksWithView() {
        setContentView(R.layout.activity_ytbe);
        mYouTubePlayerView = findViewById(R.id.youtubePlayerView);
        mPlayPauseButton = findViewById(R.id.buttonPlay);
        mLinearLayout = findViewById(R.id.Ytbe_Header_Playlist);
    }


    /**
     * Set Click reaction for every Buttons on the view.
     */
    private void initializeButtonsOnView() {
        //=========== Boutton pour lancer Pause/Play ===========
        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPlayerControls("Play&Pause");
                //playerTest();
            }
        });
        //=========== Boutton pour retourner à vidéo précédente ===========
        ImageButton buttonGoBackward = findViewById(R.id.buttonGoBackward);
        buttonGoBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPlayerControls("Backward");
            }
        });
        //=========== Boutton pour retourner à vidéo d'avant ===========
        ImageButton buttonGoForward = findViewById(R.id.buttonGoForward);
        buttonGoForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myPlayerControls("Forward");
            }
        });
        //=========== Boutton pour retourner au menu ===========
        ImageButton buttonMenu = findViewById(R.id.buttonHome);
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myGame = new Intent(YoutubeChildDisplayerActivity.this, MainDispatcherActivity.class);
                startActivity(myGame);
            }
        });
    }


    /**
     * Initialize the player with API and Player Params by default.
     */
    public void initializePlayer() {
        mYouTubePlayerView.initialize(Constants.API_KEY,
                mPlayerInitializedListener); // Envoyer l'API key à Youtube, et réagir avec mPlayerInitializedListener si Success/Fail
    }


    /**
     * Initialize Params for the Youtube Player
     */
    private void initializePlayerParams() {
        mYouTubePlayerView.setVisibility(View.VISIBLE);
        mYouTubePlayerView.setOnClickListener(mYouTubePlayerViewOnClickListener);

        mYouTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);/*YouTubePlayer.PlayerStyle.MINIMAL*/
        mYouTubePlayer.setPlayerStateChangeListener(mPlayerStateChangeListener);
        mYouTubePlayer.loadVideo(mVideoHandler.nextVideo());
    }


    /**
     * Principal function to handle control buttons.
     *
     * @param order is the order description to execute.
     */
    private void myPlayerControls(String order) {
        switch (order) {
            case "Play&Pause":
                switcherPlayPause();
                break;
            case "Backward":
                goToPreviousVideo();
                break;
            case "Forward":
                goToNextVideo();
                break;
        }
    }


    /**
     * Control Button action when we click it
     */
    public void switcherPlayPause() {
        if (mYouTubePlayer.isPlaying()) {
            mYouTubePlayer.pause();
            mPlayPauseButton.setBackground(getResources().getDrawable(R.drawable.play_icon, getApplicationContext().getTheme()));

        } else {
            mYouTubePlayer.play();
            mPlayPauseButton.setBackground(getResources().getDrawable(R.drawable.pause_icon, getApplicationContext().getTheme()));
        }
    }


    /**
     * Load the previous video of the playlist.
     */
    private void goToPreviousVideo() {
        if (mVideoHandler.getPlayingVideoIndex() >= 1) {
            mYouTubePlayer.loadVideo(mVideoHandler.previousVideo());
        }
    }


    /**
     * Load the next video of the playlist.
     */
    private void goToNextVideo() {
        if (mVideoHandler.getPlayingVideoIndex() < mVideoHandler.getVideoList().size() - 1)
            mYouTubePlayer.loadVideo(mVideoHandler.nextVideo());
    }


    /**
     * Handle the header to fit with playlist datas.
     */
    public void headerHandler(LinearLayout lt, VideoHandler vdHandler) {
        lt.removeAllViews();
        int nbOfVideos = vdHandler.getVideoList().size();
        int actuallyPlaying = vdHandler.getPlayingVideoIndex();

        for (int i = 0; i < nbOfVideos; i++) {
            if (actuallyPlaying != -1) {
                if (i == actuallyPlaying) {
                    lt.addView(vdHandler.createPlaylistHeader("red", this));
                } else {
                    lt.addView(vdHandler.createPlaylistHeader("grey", this));
                }
            } else {
                lt.addView(vdHandler.createPlaylistHeader("grey", this));
            }
        }
    }
}
