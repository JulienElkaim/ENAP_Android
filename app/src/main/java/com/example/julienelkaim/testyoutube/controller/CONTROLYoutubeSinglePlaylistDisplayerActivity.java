package com.example.julienelkaim.testyoutube.controller;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.julienelkaim.testyoutube.adapter.YoutubeVideoListAdapter;
import com.example.julienelkaim.testyoutube.model.Playlist;
import com.example.julienelkaim.testyoutube.model.VideoDetails;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.example.julienelkaim.testyoutube.toolbox.YoutubeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CONTROLYoutubeSinglePlaylistDisplayerActivity extends AppCompatActivity {
    private Playlist mPlaylist;
    private ListView mListDisplayer;
    private YoutubeVideoListAdapter mYoutubeVideoListAdapter;
    private ArrayList<VideoDetails> mVideoDetailsArrayList;
    private String mMyAPIRequestForThisPlaylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlyoutube_single_playlist_displayer);

        mPlaylist = (Playlist) getIntent().getSerializableExtra(Constants.YOUTUBE_ACTUAL_MODIFIED_PLAYLIST);
        mMyAPIRequestForThisPlaylist = YoutubeHelper.setGoogleApiVideoListDataRetriever(mPlaylist.getVideoIdList()); // Get les infos

        TextView playlistTitle = findViewById(R.id.title_playlist);
        playlistTitle.setText(mPlaylist.getTitle());
        mListDisplayer = findViewById(R.id.listOfVideos);
        mVideoDetailsArrayList = new ArrayList<>();
        mYoutubeVideoListAdapter = new YoutubeVideoListAdapter(this, mVideoDetailsArrayList);
        launchVideosResearch();
    }


    // RECUPERED MAIS PAS ENCORE MODIFE !!!!! IL FAUT ADAPTER A CETTE ACTIVITY

    private void launchVideosResearch() {
        System.out.println(mMyAPIRequestForThisPlaylist);
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
                System.out.println("CONTROLYoutubeSinglePlaylistDisplayerActivity::TEST:: NOUS EN SOMME A ID : " + i);
                System.out.println("CONTROLYoutubeSinglePlaylistDisplayerActivity::TEST:: On a trouvé l'ID suivante : "+ jsonObject.getString("id"));
               YoutubeHelper.loadVideoDetailsInAList(jsonObject,mVideoDetailsArrayList, "ControlPlaylistDisplayer");
            }

            YoutubeHelper.displayAYoutubeVideoList(mListDisplayer, mYoutubeVideoListAdapter);
            System.out.println("CONTROLYoutubeSinglePlaylistDisplayerActivity::processRequestResponse::DEBUG::TryBlock::SUCCESS:: Fial Adapter");



        } catch (JSONException e) {
            System.out.println("CONTROLYoutubeSinglePlaylistDisplayerActivity::processRequestResponse::DEBUG::TryBlock::FAIL:: On est dans JSON error");
            e.printStackTrace();
        }

    }
}
