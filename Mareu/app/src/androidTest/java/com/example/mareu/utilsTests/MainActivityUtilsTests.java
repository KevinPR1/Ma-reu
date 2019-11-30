package com.example.mareu.utilsTests;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;


import com.example.mareu.R;
import com.example.mareu.controllers.MainActivity;
import com.example.mareu.controllers.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingGuest;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.services.MeetingApiService;



import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;


import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import java.util.List;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Created by Kevin  - Openclassrooms on 30/11/2019
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityUtilsTests {

    private MainActivity mMainActivity;
    private MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    private static int ITEMS_COUNT;
    private List<Meeting> mMeetingList = mMeetingApiService.getMeetings();
    private List<MeetingGuest> mMeetingGuests = mMeetingApiService.getMemberList();
    private List<MeetingRoom> mMeetingRooms = mMeetingApiService.getMeetingRooms();


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() throws Exception {
        MainActivity mainActivity = mActivityTestRule.getActivity();
        assertThat(mainActivity, notNullValue());
        ITEMS_COUNT = mMeetingList.size();
    }

    @Test
    public void myMeetingList_addGuestAndDesableGuest_shouldRemoveGuestSelected() {
        // Given : add guest , then remove this guest from edit text
        onView(withId(R.id.MeetingsList_RecyclerView)).check(matches(isDisplayed()));
        //click on floating action button
        onView(withId(R.id.floating_button_add)).perform(click());
        //check if the custom dialog  is displayed
        onView(withId(R.id.container_dialog)).check(matches(isDisplayed()));

        //Now , set the dialog to add meeting into meeting list

        // set guests and check the current edit Text
        onView(withId(R.id.edit_text_guest_dialog)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Pierre"))).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.edit_text_guest_dialog)).check(matches(withText(mMeetingGuests.get(0).getMail())));
        //remove this guest
        onView(withId(R.id.edit_text_guest_dialog)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Pierre"))).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.edit_text_guest_dialog)).check(matches(withText("")));
    }

    @Test
    public void myMeetingList_clearAllAction_shouldClearGuestSelected() {
        // Given : add guest , then remove this guest from edit text
        onView(withId(R.id.MeetingsList_RecyclerView)).check(matches(isDisplayed()));
        //click on floating action button
        onView(withId(R.id.floating_button_add)).perform(click());
        //check if the custom dialog  is displayed
        onView(withId(R.id.container_dialog)).check(matches(isDisplayed()));

        //Now , set the dialog to add meeting into meeting list

        // set guests and check the current edit Text
        onView(withId(R.id.edit_text_guest_dialog)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Pierre"))).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.edit_text_guest_dialog)).check(matches(withText(mMeetingGuests.get(0).getMail())));

        //Clear all
        onView(withId(R.id.edit_text_guest_dialog)).perform(click());
        onView(withId(android.R.id.button3)).perform(click());
        onView(withId(R.id.edit_text_guest_dialog)).check(matches(withText("")));
    }
}