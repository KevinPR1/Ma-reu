package com.example.mareu.services;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingGuest;
import com.example.mareu.model.MeetingRoom;

import java.util.List;

/**
 * Created by Kevin  - Openclassrooms on 13/11/2019
 */
public class ImpMeetingApiService implements MeetingApiService{

    private List<Meeting> meetingsList = ImpConfigMeetingGenerator.getMeetingList();
    private List<MeetingRoom> meetingRoomsList =ImpConfigMeetingGenerator.getMeetingRoomsList();
    private List<MeetingGuest> memberList =ImpConfigMeetingGenerator.getMemberList();

    /**
     * Meeting
     * @return meeting list.
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetingsList;
    }

    @Override
    public void addToMeetingList(Meeting meeting) {
        meetingsList.add(meeting);
    }
    @Override
    public void deleteToMeetingList(Meeting meeting) {
        meetingsList.remove(meeting);
    }

    /**
     * Meeting rooms
     * @return meeting rooms list
     */
    @Override
    public List<MeetingRoom> getMeetingRooms() {
        return meetingRoomsList;
    }

    @Override
    public void addToMeetingRoomsList(MeetingRoom meetingRoom) {
        meetingRoomsList.add(meetingRoom);
    }

    @Override
    public void deleteToMeetingRoomsList(MeetingRoom meetingRoom) {
        meetingRoomsList.remove(meetingRoom);
    }

    @Override
    public List<MeetingGuest> getMemberList() {
        return memberList;
    }

    @Override
    public void addToTheGuestList(MeetingGuest guest) {
        memberList.add(guest);
    }

    @Override
    public void deleteToMeetingRoomsList(MeetingGuest guest) {
        memberList.remove(guest);
    }


}
