package com.example.mareu.events;


import com.example.mareu.model.Meeting;

/**
 * Created by Kevin  - Openclassrooms on 14/11/2019
 */
public class DeleteMeetingEvent {

public Meeting mMeeting;

    public DeleteMeetingEvent(Meeting meeting) {
        mMeeting = meeting;
    }
}
