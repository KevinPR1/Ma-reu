package com.example.mareu.model;

/**
 * Created by Kevin  - Openclassrooms on 09/11/2019
 */
public class Meeting {
    private String hour;
    private String place ;
    private String subject;
    private String participants;
    private int image;



    public Meeting(String hour, String place, String subject, String participants,int image) {
        this.hour = hour;
        this.place = place;
        this.subject = subject;
        this.participants = participants;
        this.image = image;
    }


    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
