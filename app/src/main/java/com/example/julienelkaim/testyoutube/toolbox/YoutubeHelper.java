package com.example.julienelkaim.testyoutube.toolbox;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.ListView;

import com.example.julienelkaim.testyoutube.model.Playlist;
import com.example.julienelkaim.testyoutube.model.VideoDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

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

    public static void sendPlaylistToYourChild(Activity activity, Playlist mPlaylist) {
        SharedPreferences mPrefs = activity.getSharedPreferences(Constants.YOUTUBE_SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mPlaylist);
        prefsEditor.putString(Constants.YOUTUBE_PLAYLIST_CURRENTLY, json);
        prefsEditor.commit();
    }

    public static Playlist retrieveCurrentPlaylist(Activity activity) {

        SharedPreferences  mPrefs = activity.getSharedPreferences(Constants.YOUTUBE_SHARED_PREFERENCES,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.YOUTUBE_PLAYLIST_CURRENTLY, "");


        return gson.fromJson(json, Playlist.class);
    }

    public static void saveListOfPlaylist(Activity activity, ArrayList<Playlist> mPlaylistArrayList) {


        SharedPreferences mPrefs = activity.getSharedPreferences(Constants.YOUTUBE_SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mPlaylistArrayList);
        prefsEditor.putString(Constants.YOUTUBE_LIST_OF_PLAYLIST_SAVED, json);
        prefsEditor.commit();
    }

    public static ArrayList<Playlist> retrieveListOfPlaylist(Activity activity) {

        ArrayList<Playlist> playlists;
        SharedPreferences  mPrefs = activity.getSharedPreferences(Constants.YOUTUBE_SHARED_PREFERENCES , MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(Constants.YOUTUBE_LIST_OF_PLAYLIST_SAVED, "");

        if (json.equals("") ){
            playlists = new ArrayList<>();

            YoutubeHelper.defaultListOfPlaylists(playlists);
        }else {

            Type type = new TypeToken<ArrayList<Playlist>>() {}.getType();
            playlists = gson.fromJson(json, type);

        }

        return playlists;
    }

    private static void defaultListOfPlaylists(ArrayList<Playlist> playlists) {
        playlists.add(new Playlist("Jeux videos", "Permet aux enfants de découvrir l'univers des Jeux Videos", Arrays.asList("3-lmI6n4oy8", "SxLcKjfeaIw","6ptRzgvBaAk","livh7byDX1w&t=236s","Q3AilwYTvWM")));
        playlists.add(new Playlist("Animaux", "Playlist sur le monde animal terreste, liste de vidéos courtes.", Arrays.asList("3EVJ0LOIdnY","_Ms-pnNQQ3k","zGR2W8tKXk0")));
        playlists.add(new Playlist("Paysages du monde", "Une sélection de paysages pour découvrir les différents reliefs du monde.", Arrays.asList("xD_oxqq9omo","JK0NprMZ8iw","a5ryXI_6YwU","MK8bXj5oGGM")));
    }
}
