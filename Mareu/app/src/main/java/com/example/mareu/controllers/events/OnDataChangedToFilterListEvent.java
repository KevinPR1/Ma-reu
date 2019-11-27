package com.example.mareu.controllers.events;

import com.example.mareu.model.MeetingRoom;

/**
 * Created by Kevin  - Openclassrooms on 27/11/2019
 */
public class OnDataChangedToFilterListEvent {

    public MeetingRoom meetingRoom;

    public OnDataChangedToFilterListEvent(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }
}

