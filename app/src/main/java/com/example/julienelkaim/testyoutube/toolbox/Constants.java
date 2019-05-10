package com.example.julienelkaim.testyoutube.toolbox;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import java.util.Objects;

public final class Constants {



    private Constants() {
        // restrict instantiation
    }
    public static void windowAndSystemSettings(Activity myActivity){

        try {
            AppCompatActivity tmpAct = (AppCompatActivity)myActivity;
            Objects.requireNonNull(tmpAct.getSupportActionBar()).hide(); // hide the title bar
        }catch (Exception e){
            //
        }
        myActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        myActivity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

    }
    public static final String API_KEY = "AIzaSyAgCH9R0s_c6OH2bW2DZ47Nv7VqVMV9Qdk";
    public static final String YOUTUBE_VIDEO_ID_FROM_RESEARCH = "recuperer id de la video clicked dans activity de recherche";
    public static final String YOUTUBE_PLAYLIST_CURRENTLY = "Ma playlist actuellement saved dans lapplication";
    public static final String YOUTUBE_SHARED_PREFERENCES = "YOUTUBE";
    public static final String YOUTUBE_ACTUAL_MODIFIED_PLAYLIST = "Playlist currently displayed in control. In modification process.";

}
