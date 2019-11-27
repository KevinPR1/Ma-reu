package com.example.mareu.model;

import java.util.Objects;

/**
 * Created by Kevin  - Openclassrooms on 09/11/2019
 */
public class Meeting {
    private String mHour, mParticipants, mSubject, mPlace, mDate;
    private int mImage;
    private Integer mDuration;


    public Meeting(String hour, String place, String subject, String participants, String date, int image) {
        mHour = hour;
        mPlace = place;
        mSubject = subject;
        mParticipants = participants;
        mImage = image;
        mDuration = 45;
        mDate = date;
    }


    public String getHour() {
        return mHour;
    }

    public void setHour(String hour) {
        this.mHour = hour;
    }

    public String getPlace() {
        return mPlace;
    }

    public void setPlace(String place) {
        this.mPlace = place;
    }

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        this.mSubject = subject;
    }

    public String getParticipants() {
        return mParticipants;
    }

    public void setParticipants(String participants) {
        this.mParticipants = participants;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        this.mImage = image;
    }

    public Integer getDuration() {
        return mDuration;
    }

    public void setDuration(Integer duration) {
        this.mDuration = duration;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return mHour.equals(meeting.mHour) &&
                mPlace.equals(meeting.mPlace) &&
                mDate.equals(meeting.mDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mHour, mPlace, mDate);
    }
}
