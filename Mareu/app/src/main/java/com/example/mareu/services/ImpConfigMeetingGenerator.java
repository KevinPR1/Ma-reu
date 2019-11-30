package com.example.mareu.services;

import com.example.mareu.R;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingGuest;
import com.example.mareu.model.MeetingRoom;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Kevin  - Openclassrooms on 13/11/2019
 */

public class ImpConfigMeetingGenerator {


    public static List<MeetingRoom> MEETING_ROOMS_LIST = Arrays.asList(
            new MeetingRoom("Peach", R.drawable.img_peach),
            new MeetingRoom("Mario", R.drawable.img_mario),
            new MeetingRoom("Luigi", R.drawable.img_luigi),
            new MeetingRoom("Yoshi", R.drawable.img_yoshi),
            new MeetingRoom("Daisy", R.drawable.img_daisy),
            new MeetingRoom("Toad", R.drawable.img_toad),
            new MeetingRoom("Bowser", R.drawable.img_bowser),
            new MeetingRoom("Wario", R.drawable.img_wario),
            new MeetingRoom("Waluigi", R.drawable.img_waluigi),
            new MeetingRoom("Boo", R.drawable.img_boo)

    );


    public static List<MeetingGuest> MEMBER_LIST = Arrays.asList(
            new MeetingGuest("Pierre", "pierre@lamzone.com"),
            new MeetingGuest("Alice", "alice@lamzone.com"),
            new MeetingGuest("Damien", "damien@lamzone.com"),
            new MeetingGuest("Baptiste", "baptiste@lamzone.com"),
            new MeetingGuest("Luc", "Luc@lamzone.com"),
            new MeetingGuest("Sofia", "sofia@lamzone.com"),
            new MeetingGuest("Louis", "louis@lamzone.com"),
            new MeetingGuest("Bastien", "bastien@lamzone.com"),
            new MeetingGuest("Gabriel", "gabriel@lamzone.com"),
            new MeetingGuest("Paul", "paul@lamzone.com")
    );


    public static List<Meeting> MEETING_LIST = Arrays.asList(
            new Meeting("16h00", MEETING_ROOMS_LIST.get(0).getName(), "Réunion A", MEMBER_LIST.get(2).getMail() +", "+ MEMBER_LIST.get(1).getMail() +", "+ MEMBER_LIST.get(8).getMail(), "27/12/2019", MEETING_ROOMS_LIST.get(0).getImage()),
            new Meeting("15h00", MEETING_ROOMS_LIST.get(1).getName(), "Réunion B", MEMBER_LIST.get(6).getMail() +", "+ MEMBER_LIST.get(7).getMail() +", "+ MEMBER_LIST.get(9).getMail(), "27/12/2019", MEETING_ROOMS_LIST.get(1).getImage()),
            new Meeting("15h00", MEETING_ROOMS_LIST.get(2).getName(), "Réunion C", MEMBER_LIST.get(5).getMail() + ", "+MEMBER_LIST.get(4).getMail() + ", "+MEMBER_LIST.get(3).getMail(), "28/12/2019", MEETING_ROOMS_LIST.get(2).getImage()),
            new Meeting("15h00", MEETING_ROOMS_LIST.get(8).getName(), "Réunion C", MEMBER_LIST.get(8).getMail() + ", "+MEMBER_LIST.get(9).getMail() + ", "+MEMBER_LIST.get(6).getMail(), "18/12/2019", MEETING_ROOMS_LIST.get(8).getImage())
    );


    static List<Meeting> getMeetingList() {
        return new ArrayList<>(MEETING_LIST);
    }

    static List<MeetingRoom> getMeetingRoomsList() {
        return new ArrayList<>(MEETING_ROOMS_LIST);
    }

    static List<MeetingGuest> getMemberList() {
        return new ArrayList<>(MEMBER_LIST);
    }

}
