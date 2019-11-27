package com.example.mareu.model;

import java.util.Objects;

/**
 * Created by Kevin  - Openclassrooms on 14/11/2019
 */
public class MeetingGuest {
    private String mMail;
    private String mName;

    public MeetingGuest(String name, String mail) {
        mName = name;
        mMail = mail;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getMail() {
        return mMail;
    }

    public void setMail(String mail) {
        this.mMail = mail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingGuest that = (MeetingGuest) o;
        return mMail.equals(that.mMail) &&
                mName.equals(that.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mMail, mName);
    }
}
