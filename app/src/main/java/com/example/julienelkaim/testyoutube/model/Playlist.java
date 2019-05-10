package com.example.julienelkaim.testyoutube.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Playlist implements Serializable {
    public String title, description;
    public List<String> videoIdList;

    public Playlist(String title, String description, List<String> videoIdList) {
        this.title = title;
        this.description = description;

        this.videoIdList = videoIdList ;
    }

    public Playlist(){}

    public List<String> getVideoIdList() {
        return videoIdList;
    }

    public void setVideoIdList(List<String> videoIdList) {
        this.videoIdList = videoIdList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getNumberOfVideos(){
        return this.videoIdList.size();
    }
}
