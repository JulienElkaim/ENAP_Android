package com.example.julienelkaim.testyoutube.adapter.Youtube;


import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.controller.Youtube.Aidant.SinglePlaylistDisplayerActivity;
import com.example.julienelkaim.testyoutube.controller.Youtube.MotherActivity.PlaylistListDisplayerActivity;
import com.example.julienelkaim.testyoutube.model.Youtube.Playlist;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.example.julienelkaim.testyoutube.toolbox.Youtube.YoutubeBox;
import java.util.ArrayList;


public class PlaylistListAdapter extends BaseAdapter {

    private PlaylistListDisplayerActivity mActivity;
    private ArrayList<Playlist> mPlaylistArrayList;
    private LayoutInflater mLayoutInflater;


    /**
     * @author Julien Elkaim
     *
     * @param activity is the context of the list view using this adapter.
     * @param PlaylistArrayList is the list of playlist to adapte at the list view.
     */
    public PlaylistListAdapter(PlaylistListDisplayerActivity activity, ArrayList<Playlist> PlaylistArrayList){
        mActivity = activity;
        mPlaylistArrayList = PlaylistArrayList;
    }

    /**
     *
     * @return the number of playlist to add at the list view
     */
    @Override
    public int getCount() {
        return mPlaylistArrayList.size();
    }

    /**
     *
     * @param position is the index of the playlist in the list.
     * @return the playlist at the position provided.
     */
    @Override
    public Object getItem(int position) {
        return mPlaylistArrayList.get(position);
    }

    /**
     *
     * @param position  is the index of the playlist in the list.
     * @return the id number of the playlist in the total list displayed.
     */
    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    /**
     * @author Julien Elkaim
     *
     * @param position is the index of the playlist in the list.
     * @param convertView is the view object that will display our element.
     * @param parent is the list view that will display our playlists.
     * @return the view object after modification to display.
     */
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(mLayoutInflater==null){
            mLayoutInflater = mActivity.getLayoutInflater();
        }

        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.custom_playlist_item, null);
        }

        final Playlist mPlaylist = mPlaylistArrayList.get(position);

        TextView textView = convertView.findViewById(R.id.video_title);
        textView.setText(mPlaylist.getTitle());

        TextView secondTextView = convertView.findViewById(R.id.video_description);
        secondTextView.setText((mPlaylist.getDescription().equals(""))? secondTextView.getText(): mPlaylist.getDescription());

        LinearLayout linearLayout = convertView.findViewById(R.id.root);
        linearLayout.setOnClickListener(new LinearLayout.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {


                Intent displayThisPlaylist = new Intent(mActivity, SinglePlaylistDisplayerActivity.class) ;
                displayThisPlaylist.putExtra(GlobalBox.YOUTUBE_ACTUAL_MODIFIED_PLAYLIST , mPlaylist);
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
                YoutubeBox.sendPlaylistToYourChild(mActivity,mPlaylist);
                Toast.makeText(mActivity, "Playlist envoyée à l'enfant!", Toast.LENGTH_SHORT).show();

            }
        });

        ImageButton supprButton = convertView.findViewById(R.id.suprr_button_playlist);
        supprButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save my playlist in preferences as the current playlist, and update listview
                YoutubeBox.destroyPlaylistById(mActivity, mPlaylist.getPlaylistId(), mPlaylistArrayList);
                mActivity.finish();
                mActivity.startActivity(mActivity.getIntent());

            }
        });

        return convertView;
    }

}

