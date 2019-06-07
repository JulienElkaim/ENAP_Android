package com.example.julienelkaim.testyoutube.controller.Youtube.Aidant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.julienelkaim.testyoutube.R;
import com.example.julienelkaim.testyoutube.model.Youtube.Playlist;
import com.example.julienelkaim.testyoutube.toolbox.Youtube.YoutubeBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PlaylistCreatorGetterActivity extends AppCompatActivity {
    EditText mPlaylistId;
    EditText mPlaylistTitle;
    EditText mPlaylistDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_creator_getter);

        mPlaylistId = findViewById(R.id.playlist_create_id);
        mPlaylistTitle = findViewById(R.id.playlist_create_title);
        mPlaylistDescription = findViewById(R.id.playlist_create_description);

        Button mLaunch = findViewById(R.id.playlist_create_by_id_btn);
        mLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playlistId = mPlaylistId.getText().toString();
                launchVideosResearch(
                        YoutubeBox.setGoogleApiPlaylistitemUrl(playlistId,25)
                );


            }
        });
    }


    /**
     * @author Julien Elkaim
     *
     * Initiate API request with the user's entry.
     */
    private void launchVideosResearch(String mSearchUrl) {

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
     * @param response is the request' answer of the Youtube Data API.
     */
    private void processRequestResponse(String response) {

        List<String> als = new ArrayList<>();
        try {

            JSONArray jArray = new JSONObject(response).getJSONArray("items");

            for (int i=0; i< jArray.length();i++) {

                JSONObject jsonObject = jArray.getJSONObject(i); // un snippet

                //charger l'id de la video
                String tmp = jsonObject.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                als.add(tmp);
            }

            ArrayList<Playlist> mesPlaylists = YoutubeBox.retrieveListOfPlaylist(PlaylistCreatorGetterActivity.this);
            mesPlaylists.add(
                    new Playlist(
                            YoutubeBox.provideUniqueId(PlaylistCreatorGetterActivity.this),
                            mPlaylistTitle.getText().toString(),
                            mPlaylistDescription.getText().toString(),
                            als
                    )
            );
            YoutubeBox.saveListOfPlaylist(PlaylistCreatorGetterActivity.this ,mesPlaylists);
            onBackPressed();



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
