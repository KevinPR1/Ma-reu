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
    public void setUp()  {
        mMeetingApiService = DI.getMeetingApiService();
        meeting = new Meeting("","","","","",0);
    }

    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = mMeetingApiService.getMeetings();
        List<Meeting> container = ImpConfigMeetingGenerator.MEETING_LIST;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(container.toArray())));
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
        String date = "30/11/2019" ;
        Meeting meetingToFilter = new Meeting("","","","",date,0);
        mMeetingApiService.getMeetings().add(meetingToFilter);
        List<Meeting> filterListWithDate = mMeetingApiService.filterDate(date);
        assertEquals(filterListWithDate.size(),1);
    }

    @Test
    public void filterMeetingRoomWithSuccess() {
        MeetingRoom meetingRoom = mMeetingApiService.getMeetingRooms().get(9);
        Meeting meetingToFilter = new Meeting("",meetingRoom.getName(),"","","",0);
        mMeetingApiService.getMeetings().add(meetingToFilter);
        List<Meeting> filterListWithMeetingRoom = mMeetingApiService.filterMeetingRoom(meetingRoom);
        assertEquals(filterListWithMeetingRoom.size(),1);
    }
}