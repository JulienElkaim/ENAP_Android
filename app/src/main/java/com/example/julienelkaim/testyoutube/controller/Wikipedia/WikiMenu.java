package com.example.julienelkaim.testyoutube.controller.Wikipedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WikiMenu extends AppCompatActivity {
    private ListView listView;
    private WikiAdapter mAdapter;
    int page = 0;
    int imgnum = 0;
    EditText request;
    String results;
    ArrayList<String> resultsList = new ArrayList<>();



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_wiki);
        request = findViewById(R.id.request);

    }

    public void next(View view){
        Intent msgToSecondActivity = new Intent();
        msgToSecondActivity.putExtra("page", ++page);
        msgToSecondActivity.putExtra("imgnum", ++imgnum);
        msgToSecondActivity.setClass(mAdapter.getContext(), WikiAffichage.class);
        startActivity(msgToSecondActivity);
    }

    public void previous(View view){
        Intent msgToSecondActivity = new Intent();
        msgToSecondActivity.putExtra("page", --page);
        msgToSecondActivity.putExtra("imgnum", --imgnum);
        msgToSecondActivity.setClass(mAdapter.getContext(), WikiAffichage.class);
        startActivity(msgToSecondActivity);
    }


    public void display(View view){
        listView = findViewById(R.id.listview);

        final ArrayList<WikiArticle> wikiArticlesList = new ArrayList<>();

        for (String result : resultsList)
            wikiArticlesList.add(new WikiArticle(result));

        mAdapter = new WikiAdapter(this, wikiArticlesList);

        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WikiArticle item = (WikiArticle) listView.getItemAtPosition(position);
                mAdapter.notifyDataSetChanged();
                Intent msgToSecondActivity = new Intent();
                msgToSecondActivity.putExtra("articleTitle", item.getmName());
                msgToSecondActivity.setClass(mAdapter.getContext(), WikiAffichage.class);
                startActivity(msgToSecondActivity);
            }
        });
    }


    public void getArticle(View view){
        //Hide Keyboard
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(request.getWindowToken(), 0);

        //Hide Welcome Text
        TextView welcomeTitle = findViewById(R.id.welcomeTitle);
        TextView welcomeText = findViewById(R.id.welcomeText);
        welcomeText.setVisibility(View.GONE);
        welcomeTitle.setVisibility(View.GONE);

        //Perfom research
        String url = "https://fr.vikidia.org/w/api.php?action=opensearch&search=" + request.getText() + "&format=json";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        Response.Listener<String> responseListener = new WikiResponseListener();
        Response.ErrorListener errorListener = new WikiErrorResponseListener();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(stringRequest);
    }

    private class WikiResponseListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            List<String> retour;
            String tmp = response;
            tmp = tmp.replace("[", "#");
            tmp = tmp.replace("]", "");

            //Gestion des caractères spéciaux
            tmp = tmp.replace("\\u00e9", "\u00e9");
            tmp = tmp.replace("\\u00e8", "\u00e8");
            tmp = tmp.replace("\\u00c9", "\u00c9");
            tmp = tmp.replace("\\u00ea", "\u00ea");
            tmp = tmp.replace("\\u00e7", "\u00e7");
            tmp = tmp.replace("\\u00e0", "\u00e0");
            tmp = tmp.replace("\\u00f4", "\u00f4");
            tmp = tmp.replace("\\u00ef", "\u00ef");

            //Gestion des caractères spéciaux automatisée
            /*while (tmp.contains("\\")){
                String str = tmp.split(" ")[0];
                str = str.replace("\\","");
                String[] arr = str.split("u");
                String text = "";
                for(int i = 1; i < arr.length; i++){
                    int hexVal = Integer.parseInt(arr[i], 16);
                    text += (char)hexVal;
                }

            }*/

            String[] conversion = tmp.split("#");
            retour = Arrays.asList(conversion);

            //Stockage des résultats
            ArrayList<String> resultsListTemp = new ArrayList<>();
            results = retour.get(2);
            for (String result : results.split(","))
                resultsListTemp.add(result.replace("\"", ""));


            //Affichage des résultats
            listView = findViewById(R.id.listview);
            final ArrayList<WikiArticle> wikiArticlesList = new ArrayList<>();
            for (String result : resultsListTemp)
                wikiArticlesList.add(new WikiArticle(result));
            mAdapter = new WikiAdapter(getApplicationContext(), wikiArticlesList);
            listView.setAdapter(mAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    WikiArticle item = (WikiArticle) listView.getItemAtPosition(position);
                    mAdapter.notifyDataSetChanged();
                    Intent msgToSecondActivity = new Intent();
                    msgToSecondActivity.putExtra("articleTitle", item.getmName());
                    msgToSecondActivity.putExtra("page", page);
                    msgToSecondActivity.putExtra("imgnum", imgnum);
                    msgToSecondActivity.setClass(mAdapter.getContext(), WikiAffichage.class);
                    startActivity(msgToSecondActivity);
                }
            });
        }
    }

    private class WikiErrorResponseListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
}



