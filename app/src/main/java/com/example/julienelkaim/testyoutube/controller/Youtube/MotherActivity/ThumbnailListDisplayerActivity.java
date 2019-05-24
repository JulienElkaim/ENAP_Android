package com.example.julienelkaim.testyoutube.controller.Youtube.MotherActivity;

import android.support.v7.app.AppCompatActivity;

public abstract class ThumbnailListDisplayerActivity extends AppCompatActivity {
    public boolean mIsListModifiable = false;
    public int countVideoDisplayed = 0;

    /**
     * @author Julien Elkaim
     *
     * Increment the number of videos displayed.
     */
    public void incrementCountVideoDisplayed(){ countVideoDisplayed+=1; }

    /**
     * @author Julien Elkaim
     *
     * @param videoId is the video to add at the list.
     */
    public void modifyYourList(String videoId){}
}
