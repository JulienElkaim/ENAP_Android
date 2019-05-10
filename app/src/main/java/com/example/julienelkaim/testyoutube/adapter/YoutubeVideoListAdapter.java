package com.example.julienelkaim.testyoutube.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.controller.YoutubePlayListDisplayerActivity;
import com.example.julienelkaim.testyoutube.model.VideoDetails;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class YoutubeVideoListAdapter extends BaseAdapter {

    Activity mActivity;
    ArrayList<VideoDetails> mVideoDetailsArrayList;
    LayoutInflater mLayoutInflater;
    public YoutubeVideoListAdapter(Activity activity, ArrayList<VideoDetails> videoDetailsArrayList){

        mActivity = activity;
        mVideoDetailsArrayList = videoDetailsArrayList;

    }
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
        linearLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, YoutubePlayListDisplayerActivity.class);
                i.putExtra(Constants.YOUTUBE_VIDEO_ID_FROM_RESEARCH,videoDetails.getVideoId());
                mActivity.startActivity(i);
            }
        });

        return convertView;
    }

}
