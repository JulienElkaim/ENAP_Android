package com.example.julienelkaim.testyoutube.toolbox;
import android.annotation.SuppressLint;
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

    /*JSON Parse object when looking by keyword*/
    public static String getSEARCHYTBAPIVideoId(JSONObject jsonObject, String switcher) throws JSONException {
        String returnedId = "";
        switch(switcher){
            case "playlist":
                returnedId = jsonObject.getString("id");
                break;
            case "search":
                returnedId = jsonObject.getJSONObject("id").getString("videoId");
                break;
        }
        return returnedId;
    }
    public static String getSEARCHYTBAPIVideoTitle(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("snippet").getString("title"); }
    public static String getSEARCHYTBAPIVideoDescription(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("snippet").getString("description"); }
    public static String getSEARCHYTBAPIVideoThumbnails(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url"); }

    /*JSON Parse object when looking by video ID
    public static String getPLAYLISTYTBAPIVideoId(JSONObject jsonObject) throws JSONException {return jsonObject.getString("id"); }
    public static String getPLAYLISTYTBAPIVideoTitle(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("snippet").getString("title"); }
    public static String getPLAYLISTYTBAPIVideoDescription(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("snippet").getString("description"); }
    public static String getPLAYLISTYTBAPIVideoThumbnails(JSONObject jsonObject) throws JSONException {return jsonObject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url"); }
    */
    public static void loadVideoDetailsInAList(JSONObject jsonObject, ArrayList<VideoDetails> vdl, String switcher) throws JSONException {

        switch(switcher){
            case "ControlPlaylistDisplayer":
                System.out.println("CONTROLYoutubeSinglePlaylistDisplayerActivity:: HELPER, controlplaylist");
                vdl.add(new VideoDetails(
                        YoutubeHelper.getSEARCHYTBAPIVideoId(jsonObject,"playlist"),
                        YoutubeHelper.getSEARCHYTBAPIVideoTitle(jsonObject),
                        YoutubeHelper.getSEARCHYTBAPIVideoDescription(jsonObject),
                        YoutubeHelper.getSEARCHYTBAPIVideoThumbnails(jsonObject)));
                break;
            case "ControlSearchVideo":
                vdl.add(new VideoDetails(
                        YoutubeHelper.getSEARCHYTBAPIVideoId(jsonObject,"search"),
                        YoutubeHelper.getSEARCHYTBAPIVideoTitle(jsonObject),
                        YoutubeHelper.getSEARCHYTBAPIVideoDescription(jsonObject),
                        YoutubeHelper.getSEARCHYTBAPIVideoThumbnails(jsonObject)));
                break;

        }

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

    public static String setGoogleApiVideoListDataRetriever (List<String> listOfVideoid){
        return "https://www.googleapis.com/youtube/v3/videos?" +
                "part=snippet%2CcontentDetails%2Cstatistics" +
                "&id="+ transformVideoIdListIntoVideoIdAPIListUrl(listOfVideoid) +
                "&key=" +Constants.API_KEY;
    }
    public static String transformVideoIdListIntoVideoIdAPIListUrl( List<String> listOfVideoId){
        String concatenator = "";
        for (int i =0 ;i < listOfVideoId.size();i++){
            if (concatenator.equals("") ){
                concatenator = listOfVideoId.get(i);
            }else{
                concatenator+= "," + listOfVideoId.get(i);
            }

        }
        return  StringModifier.escapeMyUrl( concatenator );
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
