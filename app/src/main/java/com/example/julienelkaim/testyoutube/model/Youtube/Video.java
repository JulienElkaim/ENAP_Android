package com.example.julienelkaim.testyoutube.model.Youtube;

public class Video {

    private String videoId, title, description, url;

    public Video(String videoId, String title, String description, String url) {
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
