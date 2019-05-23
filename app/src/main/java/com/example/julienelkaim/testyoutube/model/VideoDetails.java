package com.example.julienelkaim.testyoutube.model;

public class VideoDetails {

    private String videoId, title, description, url;

    public VideoDetails(String videoId, String title, String description, String url) {
        this.videoId = videoId;
        this.title = title;
        this.description = description;
        this.url = url;
    }



    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }


    public String getUrl() {
        return url;
    }

}
