package com.example.mareu.services;

import com.example.mareu.controllers.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingGuest;
import com.example.mareu.model.MeetingRoom;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;


import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Created by Kevin  - Openclassrooms on 28/11/2019
 */
public class ImpMeetingApiServiceTest {

    private MeetingApiService mMeetingApiService;
    private Meeting meeting;

    @Before
    public void setUp() {
        mMeetingApiService = DI.getMeetingApiService();
        meeting = new Meeting("17h00", "Daisy", "test", "kevin@lamzone.com", "03/12/2019", 0);
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = mMeetingApiService.getMeetings();
        List<Meeting> container = ImpConfigMeetingGenerator.MEETING_LIST;
        assertTrue(meetings.containsAll(container));
    }

    @Test
    public void getMeetingRoomsWithSuccess() {
        List<MeetingRoom> meetingRooms = mMeetingApiService.getMeetingRooms();
        List<MeetingRoom> container = ImpConfigMeetingGenerator.MEETING_ROOMS_LIST;
        assertThat(meetingRooms, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(container.toArray())));
    }

    @Test
    public void getMemberListWithSuccess() {
        List<MeetingGuest> memberList = mMeetingApiService.getMemberList();
        List<MeetingGuest> container = ImpConfigMeetingGenerator.MEMBER_LIST;
        assertThat(memberList, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(container.toArray())));
    }

    @Test
    public void addToMeetingListWithSuccess() {
        mMeetingApiService.getMeetings().add(meeting);
        assertTrue(mMeetingApiService.getMeetings().contains(meeting));
    }

    @Test
    public void deleteToMeetingListWithSuccess() {
        mMeetingApiService.getMeetings().add(meeting);
        assertTrue(mMeetingApiService.getMeetings().contains(meeting));
        mMeetingApiService.getMeetings().remove(meeting);
        assertFalse(mMeetingApiService.getMeetings().contains(meeting));
    }

    @Test
    public void filterDateWithSuccess() {
        String date = "11/12/2019";
        MeetingRoom meetingRoom = mMeetingApiService.getMeetingRooms().get(9);
        MeetingGuest meetingGuest = mMeetingApiService.getMemberList().get(5);
        Meeting meetingToFilter = new Meeting("17h00", meetingRoom.getName(), "test", meetingGuest.getMail(), date, meetingRoom.getImage());
        mMeetingApiService.getMeetings().add(meetingToFilter);
        List<Meeting> filterListWithDate = mMeetingApiService.filterDate(date);
        assertEquals(filterListWithDate.size(), 1);
    }

    @Test
    public void filterMeetingRoomWithSuccess() {
        MeetingRoom meetingRoom = mMeetingApiService.getMeetingRooms().get(6);
        MeetingGuest meetingGuest = mMeetingApiService.getMemberList().get(8);
        Meeting meetingToFilter = new Meeting("17h00", meetingRoom.getName(), "test", meetingGuest.getMail(), "01/12/2019", meetingRoom.getImage());
        mMeetingApiService.getMeetings().add(meetingToFilter);
        List<Meeting> filterListWithMeetingRoom = mMeetingApiService.filterMeetingRoom(meetingRoom);
        assertEquals(filterListWithMeetingRoom.size(), 1);
    }

    @Test
    public void filterWithPlaceAndDate() {
        MeetingRoom meetingRoom = mMeetingApiService.getMeetingRooms().get(7);
        MeetingGuest meetingGuest = mMeetingApiService.getMemberList().get(7);
        String date = "11/12/2019";
        Meeting meetingToFilter = new Meeting("16h00", meetingRoom.getName(), "test", meetingGuest.getMail(), date, meetingRoom.getImage());
        mMeetingApiService.getMeetings().add(meetingToFilter);
        List<Meeting> filterListWithPlaceAndDate = mMeetingApiService.filterWithPlaceAndDate(date,meetingRoom);
        assertEquals(filterListWithPlaceAndDate.size(), 1);
    }
}