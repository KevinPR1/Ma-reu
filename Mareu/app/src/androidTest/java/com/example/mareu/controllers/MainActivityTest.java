package com.example.mareu.controllers;


import android.support.test.espresso.contrib.PickerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.mareu.R;
import com.example.mareu.controllers.di.DI;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingGuest;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.services.MeetingApiService;
import com.example.mareu.utils.DeleteViewAction;



import static android.support.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static org.hamcrest.Matchers.containsString;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Created by Kevin  - Openclassrooms on 29/11/2019
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MeetingApiService mMeetingApiService = DI.getMeetingApiService();
    private static int ITEMS_COUNT;
    private List<Meeting> mMeetingList = mMeetingApiService.getMeetings();
    private List<MeetingGuest> mMeetingGuests = mMeetingApiService.getMemberList();
    private List<MeetingRoom> mMeetingRooms = mMeetingApiService.getMeetingRooms();


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() {
        MainActivity mainActivity = mActivityTestRule.getActivity();
        assertThat(mainActivity, notNullValue());
        ITEMS_COUNT = mMeetingList.size();
    }


    @Test
    public void myMeetingList_shouldNotBeEmpty() {
        // not null
        onView(withId(R.id.MeetingsList_RecyclerView)).check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void myMeetingList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 0
        onView(withId(R.id.MeetingsList_RecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withId(R.id.MeetingsList_RecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 2
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(ITEMS_COUNT - 1));
    }


    @Test
    public void myMeetingList_addAction_shouldAddItemIntoMeetingList() {
        // Given : create and add custom mmeeting
        onView(withId(R.id.MeetingsList_RecyclerView)).check(matches(isDisplayed()));
        //click on floating action button
        onView(withId(R.id.floating_button_add)).perform(click());
        //check if the custom dialog  is displayed
        onView(withId(R.id.container_dialog)).check(matches(isDisplayed()));

        //Now , set the dialog to add meeting into meeting list

        //subject
        onView(withId(R.id.edit_text_subject_dialog)).perform(replaceText("Commerce"));

        // set hour and check the current edit Text
        onView(withId(R.id.edit_text__hour_dialog)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(12, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.edit_text__hour_dialog)).check(matches(withText("12h30")));

        // set date and check the current edit Text
        onView(withId(R.id.edit_text__date_dialog)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019, 12, 30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.edit_text__date_dialog)).check(matches(withText("30/12/2019")));

        // set guests and check the current edit Text
        onView(withId(R.id.edit_text_guest_dialog)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Pierre"))).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.edit_text_guest_dialog)).check(matches(withText(mMeetingGuests.get(0).getMail())));

        // Chosse a meeting room with spinner to finish the configuration of this meeting
        onView(withId(R.id.spinner_dialog)).perform(click());
        onData(anything()).atPosition(7).inRoot(isPlatformPopup()).perform(click());
        onView(withId(R.id.spinner_dialog)).check(matches(withSpinnerText(containsString(mMeetingRooms.get(7).toString()))));


        //Now We can click on android button 1 to create a new meeting into the meeting list
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.MeetingsList_RecyclerView)).check(matches(isDisplayed()));
        //we ensure that the current custom Meeting is now created
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(ITEMS_COUNT+1));
    }


    @Test
    public void myMeetingList_dilterAction_shouldDisplayFilterDate() {
        // Given : We ensure that we have three meetings
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(ITEMS_COUNT));
        // When : We click on the toolbar and select a date
        onView(withId(R.id.filter_icon)).perform(click());
        onView(withText(R.string.mode_date)).check(matches(isDisplayed()));
        // set date for te filter
        onView(withText(R.string.mode_date)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2019 - 2, 27, 11));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(2));
    }

    @Test
    public void myMeetingList_dilterAction_shouldDisplayFilterMeetingRoom() {
        // Given : We check that we have 3 Reunions
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(ITEMS_COUNT));
        // When : We click on the toolbar and select a room
        onView(withId(R.id.filter_icon)).perform(click());
        //set meeting room for the filter
        onView(withText(R.string.mode_meetingRoom)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Luigi"))).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(1));
    }

    @Test
    public void myMeetingList_noFilterAction_shouldRemoveAllFilters() {
        // Given : We check that we have 3 Reunions
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(ITEMS_COUNT));

        onView(withId(R.id.filter_icon)).perform(click());
        //set meeting room for the filter
        onView(withText(R.string.mode_meetingRoom)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Luigi"))).perform(click());
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(1));

        // When : We click on the toolbar and select no filter
        onView(withId(R.id.filter_icon)).perform(click());
        //Click on no filter button to remove all filters in meeting list
        onView(withText(R.string.mode_noFilter)).perform(click());
        onView(withId(R.id.MeetingsList_RecyclerView)).check(withItemCount(3));
    }
}