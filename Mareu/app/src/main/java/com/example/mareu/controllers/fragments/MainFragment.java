package com.example.mareu.controllers.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.controllers.di.DI;
import com.example.mareu.controllers.events.CreateMeetingEvent;
import com.example.mareu.controllers.events.DeleteMeetingEvent;
import com.example.mareu.controllers.events.OnDataChangedToFilterListEvent;
import com.example.mareu.controllers.events.OnDateSetToFilterEvent;
import com.example.mareu.controllers.events.OnItemNoFilterEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.services.MeetingApiService;
import com.example.mareu.view.adapter.RecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private MeetingApiService mMeetingApiService;
    public List<Meeting> mMeetingList;
    private RecyclerView.Adapter mAdapter;
    @BindView(R.id.MeetingsList_RecyclerView)
    RecyclerView mRecyclerView;

    public MainFragment newInstance() {
        // Required empty public constructor
        return (new MainFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        init();
        return (view);
    }


    public void init() {
        mMeetingApiService = DI.getMeetingApiService();
        mMeetingList = mMeetingApiService.getMeetings();
        mAdapter = new RecyclerViewAdapter(mMeetingList);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void dataChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDeleteCurrentMeetingEvent(DeleteMeetingEvent event) {
        mMeetingApiService.deleteToMeetingList(event.meeting);
        init();
    }

    @Subscribe
    public void onAddCustomMeetingEvent(CreateMeetingEvent event) {
        mMeetingApiService.addToMeetingList(event.customMeeting);
        init();
    }

    @Subscribe
    public void onDataChangedToFilterWithMeetingRoom(OnDataChangedToFilterListEvent event) {
        mMeetingList = mMeetingApiService.filterMeetingRoom(event.meetingRoom);
        mAdapter = new RecyclerViewAdapter(mMeetingList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onNoFilterSelected(OnItemNoFilterEvent event) {
        mMeetingList = mMeetingApiService.getMeetings();
        mAdapter = new RecyclerViewAdapter(mMeetingList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void onDataSetToFilterWithDate(OnDateSetToFilterEvent event) {
        mMeetingList = mMeetingApiService.filterDate(event.date);
        mAdapter = new RecyclerViewAdapter(mMeetingList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}

