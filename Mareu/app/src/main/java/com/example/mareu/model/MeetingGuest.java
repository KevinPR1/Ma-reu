package com.example.mareu.model;

/**
 * Created by Kevin  - Openclassrooms on 14/11/2019
 */
public class MeetingGuest {
    private String mail;
    private String name;

    public MeetingGuest(String name,String mail) {
        this.name = name;
        this.mail = mail;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
