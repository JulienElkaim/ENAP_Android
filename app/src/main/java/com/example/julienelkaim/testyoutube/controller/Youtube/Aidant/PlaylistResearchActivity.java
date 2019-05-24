package com.example.julienelkaim.testyoutube.controller.Youtube.Aidant;


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
import com.example.julienelkaim.testyoutube.adapter.Youtube.VideoListAdapter;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.controller.Youtube.MotherActivity.ThumbnailListDisplayerActivity;
import com.example.julienelkaim.testyoutube.model.Youtube.Video;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.example.julienelkaim.testyoutube.toolbox.Youtube.YoutubeBox;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;



public class PlaylistResearchActivity extends ThumbnailListDisplayerActivity {


    EditText mInputArea;
    ListView mListView;
    ArrayList<Video> mVideoArrayList;
    VideoListAdapter mVideoListAdapter;
    private String mSearchUrl;
    private String mBufferedSearch="";
    public final int NB_RESULTS = 20;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        GlobalBox.windowAndSystemSettings(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        GlobalBox.windowAndSystemSettings(this);

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

                        setListViewAndListAsset();
                    }

                    mBufferedSearch = mInputArea.getText().toString();
                    mSearchUrl= YoutubeBox.setGoogleApiSearchUrl(mBufferedSearch, NB_RESULTS);
                    launchVideosResearch();
                }
            }
        });
    }

    /**
     * @author Julien Elkaim
     *
     * Initialize list of video (empty) and the adapter of the list view.
     */
    private void setListViewAndListAsset() {
        mVideoArrayList = new ArrayList<>();
        mVideoListAdapter = new VideoListAdapter(this, mVideoArrayList);

    }


    /**
     * @author Julien Elkaim
     *
     * Initiate API request with the user's entry.
     */
    private void launchVideosResearch() {
        RequestQueue rqQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRqQueue = new StringRequest(
                Request.Method.GET,
                mSearchUrl,
                new Response.Listener<String>() {@Override public void onResponse(String response) { processRequestResponse(response) ; } },
                new Response.ErrorListener() {@Override public void onErrorResponse(VolleyError error) { Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_LONG).show(); } }
        );
        rqQueue.add(stringRqQueue);
    }

    /**
     * @author Julien Elkaim
     *
     * Handle API's answers to display videos matching to our requests.
     *
     * @param response is the request' answer of the Youtube Data API.
     */
    private void processRequestResponse(String response) {
        try {
            JSONArray jArray = new JSONObject(response).getJSONArray("items");

            for (int i=0; i< jArray.length();i++) {
                JSONObject jsonObject = jArray.getJSONObject(i);

                //charger les détails de la video
                if(jsonObject.getJSONObject("id").toString().contains("videoId")){ YoutubeBox.loadVideoDetailsInAList(jsonObject, mVideoArrayList, "ControlSearchVideo"); }
            }

            YoutubeBox.displayAYoutubeVideoList(mListView, mVideoListAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}
