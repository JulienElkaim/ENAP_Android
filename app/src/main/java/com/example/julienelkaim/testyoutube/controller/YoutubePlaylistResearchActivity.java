package com.example.julienelkaim.testyoutube.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.julienelkaim.testyoutube.adapter.YoutubeVideoListAdapter;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.VideoDetails;
import com.example.julienelkaim.testyoutube.toolbox.Constants;
import com.example.julienelkaim.testyoutube.toolbox.YoutubeHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class YoutubePlaylistResearchActivity extends AppCompatActivity {


    EditText mInputArea;
    ListView mListView;
    ArrayList<VideoDetails> mVideoDetailsArrayList;
    YoutubeVideoListAdapter mYoutubeVideoListAdapter;
    private String mSearchUrl;
    private String mBufferedSearch="";
    public final int NB_RESULTS = 10;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Constants.windowAndSystemSettings(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Constants.windowAndSystemSettings(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);
        mListView = findViewById(R.id.listOfVideos);
        mInputArea = findViewById(R.id.my_area_to_input);
        setListViewAndListAsset();



        Button my_search_button = findViewById(R.id.my_search_button);
        my_search_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mBufferedSearch.equals(mInputArea.getText().toString())) { /*Si la requête n'a point changé*/
                    if (mListView.getChildCount() != 0) {
                        System.out.println("YoutubePlaylistResearchActivity::onCreate::DEBUG:: On réinitialise le listview et les détails de vidéo.");
                        //YoutubeHelper.displayAYoutubeVideoList(mListView,mYoutubeVideoListAdapter);
                        setListViewAndListAsset();
                    }

                    System.out.println("YoutubePlaylistResearchActivity::onCreate::DEBUG:: On se prépare pour faire la recherche.");
                    mBufferedSearch = mInputArea.getText().toString();
                    mSearchUrl= YoutubeHelper.setGoogleApiSearchUrl(mBufferedSearch, NB_RESULTS);
                    System.out.println("YoutubePlaylistResearchActivity::onCreate::DEBUG:: Clic du bouton.");
                    launchVideosResearch();
                }
            }
        });

        System.out.println("YoutubePlaylistResearchActivity::onCreate::DEBUG:: Activité créée avec succès.");
    }

    private void setListViewAndListAsset() {
        mVideoDetailsArrayList = new ArrayList<>();
        mYoutubeVideoListAdapter = new YoutubeVideoListAdapter(this, mVideoDetailsArrayList);

    }


    private void launchVideosResearch() {
        System.out.println(mSearchUrl);
        RequestQueue rqQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRqQueue = new StringRequest(
                Request.Method.GET,
                mSearchUrl,
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
                        System.out.println("YoutubePlaylistResearchActivity::processRequestResponse::DEBUG::TryBlock:: On a recup le jsonObject suivant: "+jsonObject);
                //charger les détails de la video
                if(jsonObject.getJSONObject("id").toString().contains("videoId")){ YoutubeHelper.loadVideoDetailsInAList(jsonObject,mVideoDetailsArrayList); }
            }

            YoutubeHelper.displayAYoutubeVideoList(mListView, mYoutubeVideoListAdapter);
                System.out.println("YoutubePlaylistResearchActivity::processRequestResponse::DEBUG::TryBlock::SUCCESS:: Fial Adapter");


        } catch (JSONException e) {
                System.out.println("YoutubePlaylistResearchActivity::processRequestResponse::DEBUG::TryBlock::FAIL:: On est dans JSON error");
            e.printStackTrace();
        }

    }




}
