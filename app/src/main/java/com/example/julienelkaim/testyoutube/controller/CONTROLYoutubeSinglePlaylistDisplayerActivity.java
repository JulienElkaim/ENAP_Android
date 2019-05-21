package com.example.julienelkaim.testyoutube.controller;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
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
import com.example.julienelkaim.testyoutube.adapter.YoutubeVideoListAdapter;
import com.example.julienelkaim.testyoutube.controller.MotherActivity.YoutubeThumbnailListDisplayerActivity;
import com.example.julienelkaim.testyoutube.model.Playlist;
import com.example.julienelkaim.testyoutube.model.VideoDetails;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.example.julienelkaim.testyoutube.toolbox.YoutubeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CONTROLYoutubeSinglePlaylistDisplayerActivity extends YoutubeThumbnailListDisplayerActivity {

    private Playlist mPlaylist;
    private ListView mListDisplayer;
    private YoutubeVideoListAdapter mYoutubeVideoListAdapter;
    private ArrayList<VideoDetails> mVideoDetailsArrayList;
    private String mMyAPIRequestForThisPlaylist;
    private ImageButton mAddButton;



    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        System.out.println("RELOAD::: ALLER ON RELOAD");
        finish();
        startActivity(getIntent());
    }


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        mIsListModifiable = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controlyoutube_single_playlist_displayer);
        mPlaylist = (Playlist) getIntent().getSerializableExtra(Constants.YOUTUBE_ACTUAL_MODIFIED_PLAYLIST);
        mPlaylist = YoutubeHelper.findPlaylistById(this, mPlaylist.getPlaylistId());
        initializeViews();


    }

    private void initializeViews(){
        mMyAPIRequestForThisPlaylist = YoutubeHelper.setGoogleApiVideoListDataRetriever(mPlaylist.getVideoIdList()); // Get les infos

        TextView playlistTitle = findViewById(R.id.title_playlist);
        playlistTitle.setText(mPlaylist.getTitle());
        mListDisplayer = findViewById(R.id.listOfVideos);
        mVideoDetailsArrayList = new ArrayList<>();
        mYoutubeVideoListAdapter = new YoutubeVideoListAdapter(this, mVideoDetailsArrayList);
        launchVideosResearch();

        TextView emptyText = findViewById(android.R.id.empty);
        System.out.println("DEBUG::: On y est mec! IN IT");
        if (emptyText != null) {
            mListDisplayer.setEmptyView(emptyText);
            System.out.println("DEBUG::: On y est mec! GRAVE IN IT");
            if (mPlaylist.getVideoIdList().size() != 0) { ((ViewGroup) emptyText.getParent()).removeView(emptyText);}
        }

        mAddButton = findViewById(R.id.add_button_video);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mettre la playlist d'ici en a changer global
                // Lancer activité de recherche
                System.out.println("DEBBY:::  On va noter la playlsit en modif");
                Constants.YOUTUBE_PLAYLIST_ID_IN_MODIFICATION = mPlaylist.getPlaylistId();
                System.out.println("DEBBY:::  On A DEJA noter la playlsit en modif");
                Intent i = new Intent( CONTROLYoutubeSinglePlaylistDisplayerActivity.this , YoutubePlaylistResearchActivity.class);
                //i.putExtra(Constants.YOUTUBE_DISPLAYER_MODE,"ADD");
                startActivity(i);
                System.out.println("DEBBY:::  On A Envoyé pour la playlist "+ Constants.YOUTUBE_PLAYLIST_ID_IN_MODIFICATION);

            }
        });
    }


    @Override
    public void modifyYourList(String videoId) {
        System.out.println("DEBUG::: " + videoId);
        System.out.println("DEBUG::: On y est mec! now, on modif la list");
        ArrayList<String> vList =  new ArrayList<>(mPlaylist.getVideoIdList());
        vList.remove(videoId);
        mPlaylist.setVideoIdList(vList);
        System.out.println("DEBUG::: On y est mec! FRONT");
        initializeViews();
        System.out.println("DEBUG::: On y est mec! BACK");
        YoutubeHelper.updateListOfPlaylist(this, mPlaylist, YoutubeHelper.retrieveListOfPlaylist(this));

    }

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
