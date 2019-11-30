package com.example.mareu.controllers.events;

import com.example.mareu.model.MeetingRoom;

/**
 * Created by Kevin  - Openclassrooms on 30/11/2019
 */
public class OnItemFilterPlaceAndDateEvent {
    public String customDate;
    public MeetingRoom customMeetingRoom;

    public OnItemFilterPlaceAndDateEvent(String date, MeetingRoom meetingRoom) {
        customDate = date;
        customMeetingRoom = meetingRoom;
    }
}
