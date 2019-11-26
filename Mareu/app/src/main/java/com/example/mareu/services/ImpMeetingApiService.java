package com.example.mareu.services;

import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingGuest;
import com.example.mareu.model.MeetingRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin  - Openclassrooms on 13/11/2019
 */
public class ImpMeetingApiService implements MeetingApiService {

    private List<Meeting> mMeetingList = ImpConfigMeetingGenerator.getMeetingList();
    private List<MeetingRoom> mMeetingRoomsList = ImpConfigMeetingGenerator.getMeetingRoomsList();
    private List<MeetingGuest> mMemberList = ImpConfigMeetingGenerator.getMemberList();

    /**
     * Meeting
     *
     * @return meeting list.
     */
    @Override
    public List<Meeting> getMeetings() {
        return mMeetingList;
    }

    /**
     * Meeting rooms
     *
     * @return meeting rooms list
     */
    @Override
    public List<MeetingRoom> getMeetingRooms() {
        return mMeetingRoomsList;
    }


    /**
     * Meeting Guest
     *
     * @return member list
     */
    @Override
    public List<MeetingGuest> getMemberList() {
        return mMemberList;
    }

    @Override
    public List<Meeting> filterDate(String dateTofiltrer) {
        return null;
    }

    @Override
    public List<Meeting> filterMeetingRoom(MeetingRoom meetingRoom) {
        return null;
    }


    /**
     * MeetingList - methods
     */
    @Override
    public void addToMeetingList(Meeting meeting) {
        mMeetingList.add(meeting);
    }

    @Override
    public void deleteToMeetingList(Meeting meeting) {
        mMeetingList.remove(meeting);
    }
}
