package com.example.julienelkaim.testyoutube.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;



public class Playlist implements Serializable {
    private String title, description;
    private List<String> videoIdList;
    private int playlistId;


    public Playlist(int id, String title, String description, List<String> videoIdList) {
        this.assignCommonFeatures(id, title,  description);
        this.videoIdList = videoIdList ;

    }

    public Playlist(int id, String title, String description){
        this.assignCommonFeatures(id, title,  description);
        this.videoIdList = Collections.emptyList();

    }
    private void assignCommonFeatures(int id, String title, String description){
        this.title = title;
        this.description = description;
        this.playlistId = id;
    }

    public List<String> getVideoIdList() {
        return videoIdList;
    }

    public void setVideoIdList(List<String> videoIdList) {
        this.videoIdList = videoIdList;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPlaylistId() {
        return playlistId;
    }


    public int getNumberOfVideos(){
        return this.videoIdList.size();
    }


}
