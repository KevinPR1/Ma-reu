package com.example.mareu.controllers.events;

import com.example.mareu.model.Meeting;

/**
 * Created by Kevin  - Openclassrooms on 22/11/2019
 */
public class CreateMeetingEvent {
    public Meeting customMeeting;

    public CreateMeetingEvent(Meeting customMeeting) {
        this.customMeeting = customMeeting;
    }
}
