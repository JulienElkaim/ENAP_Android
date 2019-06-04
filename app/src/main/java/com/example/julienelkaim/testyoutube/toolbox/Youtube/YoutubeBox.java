package com.example.julienelkaim.testyoutube.toolbox.Youtube;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.ListView;
import com.example.julienelkaim.testyoutube.model.Youtube.Playlist;
import com.example.julienelkaim.testyoutube.model.Youtube.Video;
import com.example.julienelkaim.testyoutube.toolbox.GlobalBox;
import com.example.julienelkaim.testyoutube.toolbox.StringBox;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static android.content.Context.MODE_PRIVATE;

public final class YoutubeBox {

    private YoutubeBox(){}

    /**
     * @author Julien Elkaim
     *
     * Set adapter in the list view.
     *
     * @param lv is the list view to adapte
     * @param adpt is the adapter for the list view
     */
    public static void displayAYoutubeVideoList(ListView lv, android.widget.BaseAdapter adpt) {
        lv.setAdapter(adpt);
        adpt.notifyDataSetChanged();
    }

    /**
     * @author Julien Elkaim
     *
     * @param jsonObject json to parse.
     * @param switcher mode of parsing.
     * @return the video ID.
     * @throws JSONException is raised if JSON is broken or if request are unvalidated.
     */
    private static String getSearchYoutubeAPIVideoId(JSONObject jsonObject, String switcher) throws JSONException {
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

    /**
     * @author Julien Elkaim
     *
     * @param jsonObject json to parse. Represent a single video.
     * @return the video title.
     * @throws JSONException is raised if JSON is broken or if request are unvalidated.
     */
    private static String getSEARCHYTBAPIVideoTitle(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("snippet").getString("title");
    }

    /**
     * @author Julien Elkaim
     *
     * @param jsonObject json to parse. Represent a single video.
     * @return the video description.
     * @throws JSONException is raised if JSON is broken or if request are unvalidated.
     */
    private static String getSEARCHYTBAPIVideoDescription(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("snippet").getString("description");
    }

    /**
     * @author Julien Elkaim
     *
     * @param jsonObject json to parse. Represent a single video.
     * @return the video thumbnail's url.
     * @throws JSONException is raised if JSON is broken or if request are unvalidated.
     */
    private static String getSEARCHYTBAPIVideoThumbnails(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("medium").getString("url");
    }


    /**
     * @author Julien Elkaim
     *
     * @param jsonObject json to parse. Represent a single video.
     * @param vdl is the list of video to load.
     * @param switcher is the loading mode.
     * @throws JSONException is raised if JSON is broken or if request are unvalidated.
     */
    public static void loadVideoDetailsInAList(JSONObject jsonObject, ArrayList<Video> vdl, String switcher) throws JSONException {

        switch(switcher){
            case "ControlPlaylistDisplayer":
                vdl.add(new Video(
                        YoutubeBox.getSearchYoutubeAPIVideoId(jsonObject,"playlist"),
                        YoutubeBox.getSEARCHYTBAPIVideoTitle(jsonObject),
                        YoutubeBox.getSEARCHYTBAPIVideoDescription(jsonObject),
                        YoutubeBox.getSEARCHYTBAPIVideoThumbnails(jsonObject)));
                break;
            case "ControlSearchVideo":
                vdl.add(new Video(
                        YoutubeBox.getSearchYoutubeAPIVideoId(jsonObject,"search"),
                        YoutubeBox.getSEARCHYTBAPIVideoTitle(jsonObject),
                        YoutubeBox.getSEARCHYTBAPIVideoDescription(jsonObject),
                        YoutubeBox.getSEARCHYTBAPIVideoThumbnails(jsonObject)));
                break;

        }

    }


    /**
     * @author Julien Elkaim
     *
     * @param requested string is the url video
     */
    public static String setGoogleApiSearchUrl(String requested, int nbResults) {
        return "https://www.googleapis.com/youtube/v3/search?" +
                "part="+"snippet"+
                "&maxResults="+ nbResults +
                "&&playlistId="+ StringBox.escapeMyUrl(requested) +
                "&key=" + GlobalBox.API_KEY;
    }
    /**
     * @author Julien Elkaim
     *
     * @param requested string is the url video
     */
    public static String setGoogleApiPlaylistitemUrl(String requested, int nbResults) {
        return "https://www.googleapis.com/youtube/v3/playlistItems?" +
                "part=" +
                "snippet" +
                "&fields=" +
                "items%2Fsnippet(resourceId(videoId))" +
                "&maxResults="+
                nbResults +
                "&playlistId=" +
                requested+
                "&key=" +
                GlobalBox.API_KEY;
    }

    /**
     * @author Julien Elkaim
     *
     * @param listOfVideoid is a url-type string to represent list of video to send to google's API.
     * @return url to contact google's API.
     */
    public static String setGoogleApiVideoListDataRetriever (List<String> listOfVideoid){
        return "https://www.googleapis.com/youtube/v3/videos?" +
                "part=snippet%2CcontentDetails%2Cstatistics" +
                "&id="+ transformVideoIdListIntoVideoIdAPIListUrl(listOfVideoid) +
                "&key=" + GlobalBox.API_KEY;
    }

    /**
     * @author Julien Elkaim
     *
     * @param listOfVideoId is list of video to transform for API purpose.
     * @return url-escaped-style list of video for google API.
     */
    private static String transformVideoIdListIntoVideoIdAPIListUrl( List<String> listOfVideoId){
        StringBuilder concatenator = new StringBuilder();
        for (int i =0 ;i < listOfVideoId.size();i++){
            if (concatenator.toString().equals("") ){
                concatenator = new StringBuilder(listOfVideoId.get(i));
            }else{
                concatenator.append(",").append(listOfVideoId.get(i));
            }

        }
        return StringBox.escapeMyUrl(concatenator.toString());
    }


    /**
     * @author Julien Elkaim
     *
     * @param activity activity to display the playlist.
     * @param mPlaylist playlist to display.
     */
    public static void sendPlaylistToYourChild(Activity activity, Playlist mPlaylist) {
        SharedPreferences mPrefs = activity.getSharedPreferences(GlobalBox.YOUTUBE_SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mPlaylist);
        prefsEditor.putString(GlobalBox.YOUTUBE_PLAYLIST_CURRENTLY, json);
        prefsEditor.apply();
    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is here for preferences purpose only.
     * @return the current playlist to display
     */
    public static Playlist retrieveCurrentPlaylist(Activity activity) {

        SharedPreferences  mPrefs = activity.getSharedPreferences(GlobalBox.YOUTUBE_SHARED_PREFERENCES,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(GlobalBox.YOUTUBE_PLAYLIST_CURRENTLY, "");


        return gson.fromJson(json, Playlist.class);
    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is here for preferences purpose only.
     * @param playlist is the playlist updated to save.
     * @param mPlaylistArrayList the list of playlist known, to update with updated playlist.
     */
    public static void updateListOfPlaylist(Activity activity,Playlist playlist,  ArrayList<Playlist> mPlaylistArrayList){
        int idPl = playlist.getPlaylistId();

        for(int i = 0; i < mPlaylistArrayList.size(); i++){
            if (idPl == mPlaylistArrayList.get(i).getPlaylistId()){

                mPlaylistArrayList.set(i, playlist);
            }
        }

        saveListOfPlaylist(activity, mPlaylistArrayList);

    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is here for preferences purpose only.
     * @param mPlaylistArrayList is the new list of playlist to keep in memory.
     */
    public static void saveListOfPlaylist(Activity activity, ArrayList<Playlist> mPlaylistArrayList) {

        SharedPreferences mPrefs = activity.getSharedPreferences(GlobalBox.YOUTUBE_SHARED_PREFERENCES,MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mPlaylistArrayList);
        prefsEditor.putString(GlobalBox.YOUTUBE_LIST_OF_PLAYLIST_SAVED, json);
        prefsEditor.apply();

    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is here for preferences purpose only.
     * @return list of playlist kept in memory.
     */
    public static ArrayList<Playlist> retrieveListOfPlaylist(Activity activity) {

        ArrayList<Playlist> playlists;
        SharedPreferences  mPrefs = activity.getSharedPreferences(GlobalBox.YOUTUBE_SHARED_PREFERENCES , MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString(GlobalBox.YOUTUBE_LIST_OF_PLAYLIST_SAVED, "");

        assert json != null;
        if (json.equals("") ){
            playlists = new ArrayList<>();
            YoutubeBox.defaultListOfPlaylists(playlists);
        }else {

            Type type = new TypeToken<ArrayList<Playlist>>() {}.getType();
            playlists = gson.fromJson(json, type);

        }

        return playlists;
    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is here for preferences purpose only.
     * @param id is the id of the playlist to retrieve.
     * @return null if none is matching or the corresponding playlist.
     */
    public static Playlist findPlaylistById(Activity activity, int id){
        Playlist playlist;

        ArrayList<Playlist> lOfPlaylist = retrieveListOfPlaylist(activity);
        for (int i = 0; i < lOfPlaylist.size(); i++) {
            playlist = lOfPlaylist.get(i);
            if (playlist.getPlaylistId() == id ){return playlist;}

        }
        return null;
    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is here for preferences purpose only.
     * @param id is the playlist id in memory.
     * @param newVideoId is the video ID to add in the playlist.
     */
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

    /**
     * @author Julien Elkaim
     *
     * Provide a default list of playlist.
     *
     * @param playlists is the list of playlist to settle
     */
    private static void defaultListOfPlaylists(ArrayList<Playlist> playlists) {
        playlists.add(new Playlist(0,"Jeux videos", "Permet aux enfants de découvrir l'univers des Jeux Videos", Arrays.asList("6ptRzgvBaAk", "SxLcKjfeaIw","6ptRzgvBaAk","6ptRzgvBaAk","Q3AilwYTvWM")));
        playlists.add(new Playlist(1,"Animaux", "Playlist sur le monde animal terreste, liste de vidéos courtes.", Arrays.asList("3EVJ0LOIdnY","_Ms-pnNQQ3k","zGR2W8tKXk0")));
        playlists.add(new Playlist(2,"Paysages du monde", "Une sélection de paysages pour découvrir les différents reliefs du monde.", Arrays.asList("xD_oxqq9omo","JK0NprMZ8iw","a5ryXI_6YwU","a5ryXI_6YwU")));
    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is here for preferences purpose only.
     * @param playlistId is the id of video to destroy.
     * @param playlistArrayList is the list of playlist stored.
     */
    public static void destroyPlaylistById(Activity activity, int playlistId, ArrayList<Playlist> playlistArrayList) {
        for (int i =0; i< playlistArrayList.size(); i++){
            if (playlistArrayList.get(i).getPlaylistId() == playlistId) {
                playlistArrayList.remove(i);
                break;
            }
        }
        saveListOfPlaylist(activity, playlistArrayList);
    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is here for preferences purpose only.
     * @return an ID ensured to be unique in the system.
     */
    public static int provideUniqueId(Activity activity) {

        ArrayList<Integer> lId = retrieveAllPlaylistId(activity);
        int i =0;
        while (lId.contains(i)){
            i++;
        }
        return i;
    }

    /**
     * @author Julien Elkaim
     *
     * @param activity is here for preferences purpose only.
     * @return list of Playlists' ID present in memory.
     */
    private static ArrayList<Integer> retrieveAllPlaylistId(Activity activity) {
        ArrayList<Playlist> lPlaylist = retrieveListOfPlaylist(activity);
        ArrayList <Integer> lId = new ArrayList<>();

        for (int i = 0; i< lPlaylist.size(); i++){
        lId.add(lPlaylist.get(i).getPlaylistId());
        }

        return lId;
    }
}
