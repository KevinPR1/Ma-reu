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
     *
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    List<MeetingRoom> getMeetingRooms();

    List<MeetingGuest> getMemberList();


    /**
     * Drop a list of Meeting with the same date
     *
     * @param dateToFilter to filter
     * @return {@link List}
     */
    List<Meeting> filterDate(String dateToFilter);

    /**
     * Drop a list of Meeting with the same room
     *
     * @param meetingRoom to filter
     * @return {@link List}
     */
    List<Meeting> filterMeetingRoom(MeetingRoom meetingRoom);

    /**
     * Drop a list of Meeting with the same room and date
     *
     * @param meetingRoom to filter
     * @return {@link List}
     */
    List<Meeting> filterWithPlaceAndDate(String date,MeetingRoom meetingRoom);


    /**
     * add meeting to the meetings list
     *
     * @param meeting added
     */
    void addToMeetingList(Meeting meeting);

    /**
     * delete meeting to the meetings list
     *
     * @param meeting removed
     */
    void deleteToMeetingList(Meeting meeting);
}
