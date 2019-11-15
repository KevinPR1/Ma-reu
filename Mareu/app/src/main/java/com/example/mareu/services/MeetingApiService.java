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
     * add meeting room to the meeting rooms list
     */
    void addToMeetingRoomsList(MeetingRoom meetingRoom);
    /**
     * delete meeting room to the meeting roomslist
     */
    void deleteToMeetingRoomsList(MeetingRoom meetingRoom);


    /**
     * add meeting to the meetings list
     */
    void addToMeetingList(Meeting meeting);
    /**
     * delete meeting to the meetings list
     */
    void deleteToMeetingList(Meeting meeting);



    /**
     * add guest  to the guest list
     */
    void addToTheGuestList(MeetingGuest guest);

    /**
     * delete guest  to the guest list
     */
    void deleteToMeetingRoomsList(MeetingGuest guest);
}
