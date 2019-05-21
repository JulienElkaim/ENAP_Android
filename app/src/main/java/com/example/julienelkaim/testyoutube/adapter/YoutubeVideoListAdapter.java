package com.example.julienelkaim.testyoutube.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.controller.MotherActivity.YoutubeThumbnailListDisplayerActivity;
import com.example.julienelkaim.testyoutube.controller.YoutubePlayListDisplayerActivity;
import com.example.julienelkaim.testyoutube.model.VideoDetails;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YoutubeVideoListAdapter extends BaseAdapter {

    YoutubeThumbnailListDisplayerActivity mActivity;
    ArrayList<VideoDetails> mVideoDetailsArrayList;
    LayoutInflater mLayoutInflater;
    public YoutubeVideoListAdapter(YoutubeThumbnailListDisplayerActivity activity, ArrayList<VideoDetails> videoDetailsArrayList){

        mActivity = activity;
        mVideoDetailsArrayList = videoDetailsArrayList;

    }

    public LayoutInflater getLayoutInflater(){return mLayoutInflater;}

    @Override
    public int getCount() {
        return mVideoDetailsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mVideoDetailsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        if(mLayoutInflater==null){
            mLayoutInflater = mActivity.getLayoutInflater();
        }

        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.custom_video_item,null);
        }

        final VideoDetails videoDetails = mVideoDetailsArrayList.get(position);

        ImageView imageView = convertView.findViewById(R.id.thumbnailsImageView);
        Picasso.get().load(videoDetails.getUrl()).into(imageView);

        TextView textView = convertView.findViewById(R.id.video_title);
        textView.setText(videoDetails.getTitle());

        TextView secondTextView = convertView.findViewById(R.id.video_description);
        secondTextView.setText(videoDetails.getDescription());

        LinearLayout linearLayout = convertView.findViewById(R.id.root);

        if(mActivity.mIsListModifiable){
            //Single playlist display part    =================== DIFFERENT INTENT QUE DANS LE ELSE !!
            addASupprButton(mActivity,convertView, videoDetails);
            linearLayout.setOnClickListener(new LinearLayout.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mActivity, YoutubePlayListDisplayerActivity.class);
                    i.putExtra(Constants.YOUTUBE_VIDEO_ID_FROM_RESEARCH,videoDetails.getVideoId());
                    i.putExtra(Constants.YOUTUBE_DISPLAYER_MODE, "VIEW");
                    mActivity.startActivity(i);
                }
            });

        }else{

            linearLayout.setOnClickListener(new LinearLayout.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mActivity, YoutubePlayListDisplayerActivity.class);
                    i.putExtra(Constants.YOUTUBE_VIDEO_ID_FROM_RESEARCH,videoDetails.getVideoId());
                    i.putExtra(Constants.YOUTUBE_DISPLAYER_MODE, "ADD");
                    mActivity.startActivity(i);
                }
            });

        }

        mActivity.incrementCountVideoDisplayed();
        return convertView;
    }

    private void addASupprButton(final YoutubeThumbnailListDisplayerActivity activity, View convertView, final VideoDetails videoDetails) {


        ImageButton supprBtn = new ImageButton(activity);
        supprBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.modifyYourList(videoDetails.getVideoId());
                activity.finish();
                activity.startActivity(activity.getIntent()); //Relancer l'activité elle même


            }
        });

        supprBtn.setLayoutParams(new ViewGroup.LayoutParams(50,50));
        supprBtn.setBackgroundResource(R.drawable.suppr_button);

        LinearLayout ll = convertView.findViewById(R.id.desc_and_button);
        if(ll.getChildCount() == 1 ){
            ll.addView(supprBtn);
        }else{
            ll.removeViewAt(1);
            ll.addView(supprBtn);
        }
    }

}


/*

<ImageButton
                    android:id="@+id/suppr_video_from_playlist"
                    android:layout_width="30dp"
                    android:layout_height="30dp"
                    android:background="@drawable/suppr_button"

                    />

*/