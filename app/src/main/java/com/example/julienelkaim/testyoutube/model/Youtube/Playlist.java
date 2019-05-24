package com.example.julienelkaim.testyoutube.model.Youtube;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;



public class Playlist implements Serializable {
    private String title, description;
    private List<String> videoIdList;
    private int playlistId;


    /**
     * @author Julien Elkaim
     *
     * @param id is the playlist ID to construct. Provided by "provideUniqueId" in YoutubeBox.
     * @param title is the playlist title
     * @param description is the description of this playlist.
     * @param videoIdList is the list of video ID in this playlist.
     */
    public Playlist(int id, String title, String description, List<String> videoIdList) {
        this.assignCommonFeatures(id, title,  description);
        this.videoIdList = videoIdList ;

    }

    /**
     * @author Julien Elkaim
     *
     * @see this.Playlist up method
     * @param id is the playlist ID to construct.
     * @param title is the playlist title.
     * @param description is the description of this playlist.
     * Video List is settled empty.
     */
    public Playlist(int id, String title, String description){
        this.assignCommonFeatures(id, title,  description);
        this.videoIdList = Collections.emptyList();

    }

    /**
     * @author Julien Elkaim
     *
     * Provide assignation service to both constructor methods.
     *
     * @param id is the playlist ID.
     * @param title is the title of the playlist.
     * @param description is the description of the playlist.
     *
     */
    private void assignCommonFeatures(int id, String title, String description){
        this.title = title;
        this.description = description;
        this.playlistId = id;
    }


    /**
     * @author Julien Elkaim
     *
     * @return list of videos, represented by their id.
     */
    public List<String> getVideoIdList() {
        return videoIdList;
    }

    /**
     * @author Julien Elkaim
     *
     * @param videoIdList is the list of video to provide to change the video list of this playlist.
     */
    public void setVideoIdList(List<String> videoIdList) {
        this.videoIdList = videoIdList;
    }

    /**
     * @author Julien Elkaim
     *
     * @return playlist's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @author Julien Elkaim
     *
     * @return playlist's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @author Julien Elkaim
     *
     * @return playlist's ID.
     */
    public int getPlaylistId() {
        return playlistId;
    }


    /**
     * @author Julien Elkaim
     *
     * @return number of videos of the playlist.
     */
    public int getNumberOfVideos(){
        return this.videoIdList.size();
    }


}
