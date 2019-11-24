package com.example.mareu.services;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingGuest;
import com.example.mareu.model.MeetingRoom;

import java.util.List;

/**
 * Created by Kevin  - Openclassrooms on 13/11/2019
 */
public interface MeetingApiService {


    /**
     * Get all meetings/rooms/guests
     * @return {@link List}
     */
    List<Meeting> getMeetings();
    List<MeetingRoom> getMeetingRooms();
    List<MeetingGuest> getMemberList();

    /**
     * add meeting to the meetings list
     */
    void addToMeetingList(Meeting meeting);
    /**
     * delete meeting to the meetings list
     */
    void deleteToMeetingList(Meeting meeting);
}
