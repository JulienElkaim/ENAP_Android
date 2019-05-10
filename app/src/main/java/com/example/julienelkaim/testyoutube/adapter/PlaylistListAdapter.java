package com.example.julienelkaim.testyoutube.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/*
import com.example.julienelkaim.test_playlist.CONTROLYoutubePlaylistEnumeratorActivity;
import com.example.julienelkaim.test_playlist.R;
import com.example.julienelkaim.test_playlist.Tools.Constants;
import com.example.julienelkaim.test_playlist.Tools.Playlist;
*/
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.Playlist;

import java.util.ArrayList;
import java.util.Arrays;

public class PlaylistListAdapter extends BaseAdapter {

    Activity mActivity;
    ArrayList<Playlist> mPlaylistArrayList;
    LayoutInflater mLayoutInflater;
    public PlaylistListAdapter(Activity activity, ArrayList<Playlist> PlaylistArrayList){

        mActivity = activity;
        mPlaylistArrayList = PlaylistArrayList;

    }

    @Override
    public int getCount() {
        return mPlaylistArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPlaylistArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(mLayoutInflater==null){
            mLayoutInflater = mActivity.getLayoutInflater();
        }

        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.custom_playlist_item,null);
        }

        final Playlist mPlaylist = mPlaylistArrayList.get(position);

        // INSERT HERE SOME FEATURES

        /*ImageView imageView = convertView.findViewById(R.id.thumbnailsImageView);
        Picasso.get().load(mPlaylist.getVideoId()).into(imageView);*/

        TextView textView = convertView.findViewById(R.id.video_title);
        textView.setText(mPlaylist.getTitle());

        TextView secondTextView = convertView.findViewById(R.id.video_description);
        secondTextView.setText((mPlaylist.getDescription() =="")? secondTextView.getText(): mPlaylist.getDescription());

        LinearLayout linearLayout = convertView.findViewById(R.id.root);
        linearLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String concatenator = "";
                for (int i = 0; i < mPlaylist.getVideoIdList().size(); i++) {
                    concatenator += mPlaylist.getVideoIdList().get(i);

                }
                Toast.makeText(mActivity, concatenator, Toast.LENGTH_SHORT).show();

                /*Intent i = new Intent(mActivity, CONTROLYoutubePlaylistEnumeratorActivity.class);
                i.putExtra(Constants.YOUTUBE_VIDEO_ID_FROM_RESEARCH, String.join(";",mPlaylist.getVideoIdList()) );
                mActivity.startActivity(i);*/
            }
        });

        TextView nbVideo = convertView.findViewById(R.id.nb_video_in_playlist);
        nbVideo.setText( String.valueOf(mPlaylist.getNumberOfVideos()) );

        return convertView;
    }

}

