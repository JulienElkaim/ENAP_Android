package com.example.julienelkaim.testyoutube.controller.Youtube.Aidant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.adapter.Youtube.VideoListAdapter;
import com.example.julienelkaim.testyoutube.controller.Youtube.MotherActivity.ThumbnailListDisplayerActivity;
import com.example.julienelkaim.testyoutube.model.Youtube.Playlist;
import com.example.julienelkaim.testyoutube.model.Youtube.Video;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.example.julienelkaim.testyoutube.toolbox.Youtube.YoutubeBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class SinglePlaylistDisplayerActivity extends ThumbnailListDisplayerActivity {

    private Playlist mPlaylist;
    private ListView mListDisplayer;
    private VideoListAdapter mVideoListAdapter;
    private ArrayList<Video> mVideoArrayList;
    private String mMyAPIRequestForThisPlaylist;


    protected void onStart() {
        super.onStart();
        GlobalBox.windowAndSystemSettings(this);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        finish();
        startActivity(getIntent());
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIsListModifiable = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlyoutube_single_playlist_displayer);
        mPlaylist = (Playlist) getIntent().getSerializableExtra(GlobalBox.YOUTUBE_ACTUAL_MODIFIED_PLAYLIST);
        mPlaylist = YoutubeBox.findPlaylistById(this, mPlaylist.getPlaylistId());
        initializeViews();


    }

    private void initializeViews(){
        mMyAPIRequestForThisPlaylist = YoutubeBox.setGoogleApiVideoListDataRetriever(mPlaylist.getVideoIdList()); // Get les infos

        TextView playlistTitle = findViewById(R.id.title_playlist);
        playlistTitle.setText(mPlaylist.getTitle());
        mListDisplayer = findViewById(R.id.listOfVideos);
        mVideoArrayList = new ArrayList<>();
        mVideoListAdapter = new VideoListAdapter(this, mVideoArrayList);
        launchVideosResearch();

        TextView emptyText = findViewById(android.R.id.empty);
        if (emptyText != null) {
            mListDisplayer.setEmptyView(emptyText);
            if (mPlaylist.getVideoIdList().size() != 0) { ((ViewGroup) emptyText.getParent()).removeView(emptyText);}
        }

        ImageButton addButton = findViewById(R.id.add_button_video);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalBox.YOUTUBE_PLAYLIST_ID_IN_MODIFICATION = mPlaylist.getPlaylistId();
                Intent i = new Intent( SinglePlaylistDisplayerActivity.this , PlaylistResearchActivity.class);
                startActivity(i);
            }
        });
    }


    @Override
    public void modifyYourList(String videoId) {

        ArrayList<String> vList =  new ArrayList<>(mPlaylist.getVideoIdList());
        vList.remove(videoId);
        mPlaylist.setVideoIdList(vList);
        initializeViews();
        YoutubeBox.updateListOfPlaylist(this, mPlaylist, YoutubeBox.retrieveListOfPlaylist(this));

    }

    private void launchVideosResearch() {
        RequestQueue rqQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRqQueue = new StringRequest(
                Request.Method.GET,
                mMyAPIRequestForThisPlaylist,
                new Response.Listener<String>() {@Override public void onResponse(String response) { processRequestResponse(response) ; } },
                new Response.ErrorListener() {@Override public void onErrorResponse(VolleyError error) { Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show(); } }
        );
        rqQueue.add(stringRqQueue);
    }

    private void processRequestResponse(String response) {
        try {
            JSONArray jArray = new JSONObject(response).getJSONArray("items");
            for (int i=0; i< jArray.length();i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);

                //charger les détails de la video
               YoutubeBox.loadVideoDetailsInAList(jsonObject, mVideoArrayList, "ControlPlaylistDisplayer");
            }

            YoutubeBox.displayAYoutubeVideoList(mListDisplayer, mVideoListAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
