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


    private static String getSEARCHYTBAPIVideoId(JSONObject jsonObject, String switcher) throws JSONException {
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


    public static void loadVideoDetailsInAList(JSONObject jsonObject, ArrayList<VideoDetails> vdl, String switcher) throws JSONException {

        switch(switcher){
            case "ControlPlaylistDisplayer":
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
    private static String transformVideoIdListIntoVideoIdAPIListUrl( List<String> listOfVideoId){
        StringBuilder concatenator = new StringBuilder();
        for (int i =0 ;i < listOfVideoId.size();i++){
            if (concatenator.toString().equals("") ){
                concatenator = new StringBuilder(listOfVideoId.get(i));
            }else{
                concatenator.append(",").append(listOfVideoId.get(i));
            }

        }
        return  StringModifier.escapeMyUrl(concatenator.toString());
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

    public static void updateListOfPlaylist(Activity activity,Playlist playlist,  ArrayList<Playlist> mPlaylistArrayList){
        int idPl = playlist.getPlaylistId();

        for(int i = 0; i < mPlaylistArrayList.size(); i++){
            if (idPl == mPlaylistArrayList.get(i).getPlaylistId()){

                mPlaylistArrayList.set(i, playlist);
            }
        }

        saveListOfPlaylist(activity, mPlaylistArrayList);

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

    public static Playlist findPlaylistById(Activity activity, int id){
        Playlist playlist;

        ArrayList<Playlist> lOfPlaylist = retrieveListOfPlaylist(activity);
        for (int i = 0; i < lOfPlaylist.size(); i++) {
            playlist = lOfPlaylist.get(i);
            if (playlist.getPlaylistId() == id ){return playlist;}

        }
        return null;
    }

    public static void modifyPlaylistListOfVideo(Activity activity, int id, String newVideoId){
        ArrayList<Playlist> lOfPlaylist = retrieveListOfPlaylist(activity);
        Playlist playlist;
        for (int i = 0; i < lOfPlaylist.size(); i++){
            playlist = lOfPlaylist.get(i);
            if (id == playlist.getPlaylistId()){
                List<String> vidList = playlist.getVideoIdList();
                vidList.add(newVideoId);


                playlist.setVideoIdList(vidList);
                updateListOfPlaylist(activity,playlist,lOfPlaylist);
            }
        }


    }


    private static void defaultListOfPlaylists(ArrayList<Playlist> playlists) {
        playlists.add(new Playlist(0,"Jeux videos", "Permet aux enfants de découvrir l'univers des Jeux Videos", Arrays.asList("6ptRzgvBaAk", "SxLcKjfeaIw","6ptRzgvBaAk","6ptRzgvBaAk","Q3AilwYTvWM")));
        playlists.add(new Playlist(1,"Animaux", "Playlist sur le monde animal terreste, liste de vidéos courtes.", Arrays.asList("3EVJ0LOIdnY","_Ms-pnNQQ3k","zGR2W8tKXk0")));
        playlists.add(new Playlist(2,"Paysages du monde", "Une sélection de paysages pour découvrir les différents reliefs du monde.", Arrays.asList("xD_oxqq9omo","JK0NprMZ8iw","a5ryXI_6YwU","a5ryXI_6YwU")));
    }

    public static void destroyPlaylistById(Activity activity, int playlistId, ArrayList<Playlist> playlistArrayList) {
        for (int i =0; i< playlistArrayList.size(); i++){
            if (playlistArrayList.get(i).getPlaylistId() == playlistId) {
                playlistArrayList.remove(i);
                break;
            }
        }
        saveListOfPlaylist(activity, playlistArrayList);
    }

    public static int provideUniqueId(Activity activity) {

        ArrayList<Integer> lId = retrieveAllPlaylistId(activity);
        int i =0;
        while (lId.contains(i)){
            i++;
        }
        return i;
    }

    private static ArrayList<Integer> retrieveAllPlaylistId(Activity activity) {
        ArrayList<Playlist> lPlaylist = retrieveListOfPlaylist(activity);
        ArrayList <Integer> lId = new ArrayList<>();

        for (int i = 0; i< lPlaylist.size(); i++){
        lId.add(lPlaylist.get(i).getPlaylistId());
        }

        return lId;
    }
}
