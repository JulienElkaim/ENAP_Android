package com.example.julienelkaim.testyoutube.model;

public class ChatImage {

    private String sender;
    private String receiver;
    private long imageId;
    // On utilise un 'long' à cause de FireBase : il semblerait que firebase convertisse les ressources int du code en long (pour la transmission sur les serveurs du int, la taille est trop grande donc
    // pour Firebase c'est un long alors que la memoire cache accordée a android studio est plus grande donc c'est un int pour android studio(/compilateur java)

    public ChatImage(String sender, String receiver, long imageId) {
        this.sender = sender;
        this.receiver = receiver;
        this.imageId = imageId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
