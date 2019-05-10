package com.example.julienelkaim.testyoutube.toolbox;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.widget.ListView;

import com.example.julienelkaim.testyoutube.model.VideoDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class YoutubeHelper {

    private YoutubeHelper(){}

    public static void displayAYoutubeVideoList(ListView lv, android.widget.BaseAdapter adpt) {
        lv.setAdapter(adpt);
        adpt.notifyDataSetChanged();
    }

    public static String getYTBAPIVideoId(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("id").getString("videoId"); }
    public static String getYTBAPIVideoTitle(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("snippet").getString("title"); }
    public static String getYTBAPIVideoDescription(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("snippet").getString("description"); }
    public static String getYTBAPIVideoThumbnails(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url"); }

    public static void loadVideoDetailsInAList(JSONObject jsonObject, ArrayList<VideoDetails> vdl) throws JSONException {
        vdl.add(new VideoDetails(
                YoutubeHelper.getYTBAPIVideoId(jsonObject),
                YoutubeHelper.getYTBAPIVideoTitle(jsonObject),
                YoutubeHelper.getYTBAPIVideoDescription(jsonObject),
                YoutubeHelper.getYTBAPIVideoThumbnails(jsonObject)));
    }

    /**
     *
     * @param requested string is the url video
     */
    public static String setGoogleApiSearchUrl(String requested, int nbResults) {
        return "https://www.googleapis.com/youtube/v3/search?" +
                "part="+"snippet"+
                "&maxResults="+ nbResults +
                "&q="+ StringModifier.escapeMyUrl(requested) +
                "&key=" + Constants.API_KEY;
    }

    public static <T> List<T> setListFromSet(Set<T> set)
    {

        if (set == null){   return new ArrayList<T>();}
        else{               return new ArrayList<>(set);}

    }


    @SuppressLint("ApplySharedPref")
    public static HashSet<String> setSetFromList(List<String> videoList) {
        if (videoList == null){     return new HashSet<>();}
        else{                       return new HashSet<>(videoList);}

    }
}
