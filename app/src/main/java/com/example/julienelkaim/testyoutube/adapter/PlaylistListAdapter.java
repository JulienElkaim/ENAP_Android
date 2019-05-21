package com.example.julienelkaim.testyoutube.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
import com.example.julienelkaim.testyoutube.controller.CONTROLYoutubePlaylistEnumeratorActivity;
import com.example.julienelkaim.testyoutube.controller.CONTROLYoutubeSinglePlaylistDisplayerActivity;
import com.example.julienelkaim.testyoutube.controller.MotherActivity.YoutubePlaylistListDisplayerActivity;
import com.example.julienelkaim.testyoutube.model.Playlist;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.example.julienelkaim.testyoutube.toolbox.YoutubeHelper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class PlaylistListAdapter extends BaseAdapter {

    YoutubePlaylistListDisplayerActivity mActivity;
    ArrayList<Playlist> mPlaylistArrayList;
    LayoutInflater mLayoutInflater;
    public PlaylistListAdapter(YoutubePlaylistListDisplayerActivity activity, ArrayList<Playlist> PlaylistArrayList){

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


                Intent displayThisPlaylist = new Intent(mActivity, CONTROLYoutubeSinglePlaylistDisplayerActivity.class) ;
                displayThisPlaylist.putExtra(Constants.YOUTUBE_ACTUAL_MODIFIED_PLAYLIST , mPlaylist);
                mActivity.startActivity(displayThisPlaylist);

            }
        });

        final TextView nbVideo = convertView.findViewById(R.id.nb_video_in_playlist);
        nbVideo.setText( String.valueOf(mPlaylist.getNumberOfVideos()) );

        ImageButton sendButton = convertView.findViewById(R.id.send_button_playlist);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save my playlist in preferences as the current playlist
                YoutubeHelper.sendPlaylistToYourChild(mActivity,mPlaylist);
                Toast.makeText(mActivity, "Playlist envoyée à l'enfant!", Toast.LENGTH_SHORT).show();

            }
        });

        ImageButton supprButton = convertView.findViewById(R.id.suprr_button_playlist);
        supprButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save my playlist in preferences as the current playlist, and update listview
                YoutubeHelper.destroyPlaylistById(mActivity, mPlaylist.getPlaylistId(), mPlaylistArrayList);
                mActivity.setMyPlaylistList();


            }
        });

        return convertView;
    }

}

