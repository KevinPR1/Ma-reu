package com.example.mareu.controllers.events;


import com.example.mareu.model.Meeting;

/**
 * Created by Kevin  - Openclassrooms on 14/11/2019
 */
public class DeleteMeetingEvent {

    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting) {
        this.meeting = meeting;
    }
}
