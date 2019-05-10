package com.example.julienelkaim.testyoutube.model;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.controller.YoutubeChildDisplayerActivity;

import java.util.List;

public class VideoHandler {

    private List<String> mVideoList;
    private int mPlayedVideoNumber;

    public VideoHandler(List<String> myVideoList) {
        mVideoList = myVideoList;
        mPlayedVideoNumber = -1; //Position debut

        //List<String> supplierNames1 = new ArrayList<String>()
    }



    /**
     *
     * @return the next video to play, null if no video to play
     */
    public String nextVideo(){

        return (mPlayedVideoNumber == (mVideoList.size()-1) )? null: mVideoList.get(++mPlayedVideoNumber);
    }

    public String getPlayingVideoId(){
        return mVideoList.get(mPlayedVideoNumber);
    }

    public int getPlayingVideoIndex(){
        return mPlayedVideoNumber;
    }

    public List<String> getVideoList() {
        return mVideoList;
    }

    public String previousVideo() {
        return (mPlayedVideoNumber <= 0)? null:mVideoList.get(--mPlayedVideoNumber);
    }

    public ImageView createPlaylistHeader(String color, YoutubeChildDisplayerActivity context) {
        ImageView addedChild = new ImageView(context);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        lp.setMargins(1,0,1,0);
        addedChild.setLayoutParams(lp);

        if(color.equals("grey")) {
            addedChild.setImageDrawable(context.getResources().getDrawable(R.drawable.playlist_grey, context.getApplicationContext().getTheme()));
        }
        if(color.equals("red")){
            addedChild.setImageDrawable(context.getResources().getDrawable(R.drawable.playlist_red, context.getApplicationContext().getTheme()));
        }

        return addedChild;
    }


}