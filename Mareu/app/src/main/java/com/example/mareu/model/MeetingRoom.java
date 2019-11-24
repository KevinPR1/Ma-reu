package com.example.mareu.model;


import android.support.annotation.NonNull;

import java.util.Objects;

/**
 * Created by Kevin  - Openclassrooms on 14/11/2019
 */
public class MeetingRoom {

    private String mName;
    private int mImage;

    public MeetingRoom(String name, int image) {
        mName = name;
        mImage = image;
    }


    public String getName() {
        return mName;
    }

    public int getImage() {
        return mImage;
    }


    @NonNull
    @Override
    public String toString() {
        return mName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingRoom that = (MeetingRoom) o;
        return mImage == that.mImage &&
                mName.equals(that.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mName, mImage);
    }
}
