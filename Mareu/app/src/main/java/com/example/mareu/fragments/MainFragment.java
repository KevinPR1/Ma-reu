package com.example.mareu.fragments;




import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.services.MeetingApiService;
import com.example.mareu.ui_list_meetings.RecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    private MeetingApiService mMeetingApiService ;
    public List<Meeting> mMeetingList;
    public List<MeetingRoom> mMeetingRoomsList;
    private RecyclerViewAdapter mRecyclerViewAdapter ;
    @BindView(R.id.MeetingsList_RecyclerView)
    RecyclerView mRecyclerView ;

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
        mRecyclerView =(RecyclerView) view ;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        init();
        return (view);
    }


    public void init(){
        mMeetingApiService = DI.getMeetingApiService();
        mMeetingList = mMeetingApiService.getMeetings();
        mRecyclerViewAdapter = new RecyclerViewAdapter(mMeetingList);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
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
    public void onDeleteCurrentMeetingEvent(DeleteMeetingEvent event){
        mMeetingApiService.deleteToMeetingList(event.mMeeting);
        init();
    }
}
