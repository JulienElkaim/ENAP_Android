package com.example.julienelkaim.testyoutube.controller.Youtube.MotherActivity;

import android.support.v7.app.AppCompatActivity;

public abstract class ThumbnailListDisplayerActivity extends AppCompatActivity {
    public boolean mIsListModifiable = false;
    public int countVideoDisplayed = 0;

    public void incrementCountVideoDisplayed(){ countVideoDisplayed+=1; }

    public void modifyYourList(String videoId){}
}
