package com.example.julienelkaim.testyoutube.model;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static android.content.Context.MODE_PRIVATE;

public class Playlist implements Serializable {
    public String title, description;
    public List<String> videoIdList;
    private static final AtomicInteger count = new AtomicInteger(0);
    private final int PlaylistId = count.incrementAndGet();

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

    public int getPlaylistId() {
        return PlaylistId;
    }


    public int getNumberOfVideos(){
        return this.videoIdList.size();
    }


}
