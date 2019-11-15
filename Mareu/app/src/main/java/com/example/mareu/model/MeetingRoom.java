package com.example.mareu.model;



/**
 * Created by Kevin  - Openclassrooms on 14/11/2019
 */
public class MeetingRoom {

    private String name;
    private int image ;

    public MeetingRoom(String name, int image) {
        this.name = name;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public int getImage() {
        return image;
    }


}
