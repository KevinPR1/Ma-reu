package com.example.mareu.controllers;


import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.mareu.R;

import com.example.mareu.controllers.di.DI;
import com.example.mareu.controllers.events.OnDataChangedToFilterListEvent;
import com.example.mareu.controllers.fragments.MainFragment;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.services.MeetingApiService;
import com.example.mareu.view.dialog.DialogCustomMeeting;


import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Snackbar mSnackbar;
    private MeetingApiService mMeetingApiService;
    private MainFragment mMainFragment;
    private MeetingRoom mMeetingRoom;
    @BindView(R.id.floating_button_add)
    FloatingActionButton mFloatingActionButtong;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.constraintLayout_activity_main)
    ConstraintLayout mConstraintLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMeetingApiService = DI.getMeetingApiService();
        mSnackbar = Snackbar.make(mConstraintLayout, "Choisissez un filtre", Snackbar.LENGTH_SHORT);
        configureAndShowMainFragment();
        configureFloatingActionButton();
        configureToolbar();
    }

    /* Set elements on MainActivity
     * ToolBar
     * Menu - items of ToolBar
     * MainFragment
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    public void configureToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void configureAndShowMainFragment() {

        //  Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mMainFragment = (MainFragment)
                getSupportFragmentManager().findFragmentById(R.id.framelayout_activity_main);

        if (mMainFragment == null) {
            mMainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout_activity_main, mMainFragment)
                    .commit();
        }
    }

    /* Configurations Clicks
     * FloatingActionButton
     * Menu items
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();

        switch (i) {
            case R.id.filter_icon:
                mSnackbar.show();
                return true;
            case R.id.filter_mode_noFilter:
                mMainFragment.mMeetingList = mMeetingApiService.getMeetings();
                mMainFragment.dataChanged();
                return true;
            case R.id.filter_mode_meetingRooms:
                displayDialogToFilterMeetingRooms();
                return true;
            case R.id.filter_mode_date:
                displayDialogToFilterTheDate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void configureFloatingActionButton() {
        mFloatingActionButtong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DialogCustomMeeting();
                dialogFragment.show(getSupportFragmentManager(), "dialog");
            }
        });

    }

    /* Configurations for each mode
     * Place and Date
     */
    private void displayDialogToFilterMeetingRooms() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] roomList = new String[mMeetingApiService.getMeetingRooms().size()];
        for (int i = 0; i < mMeetingApiService.getMeetingRooms().size(); i++) {
            roomList[i] = mMeetingApiService.getMeetingRooms().get(i).getName();
        }

        builder.setTitle("")
                .setTitle("Filtrer les réunions par salle")
                .setSingleChoiceItems(roomList, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mMeetingRoom = mMeetingApiService.getMeetingRooms().get(i);
                        Toast.makeText(getApplicationContext(),"name : "+mMeetingRoom.getName(),Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Filtrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EventBus.getDefault().post(new OnDataChangedToFilterListEvent(mMeetingRoom));
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        builder.show();
    }

    private void displayDialogToFilterTheDate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] date = getResources().getStringArray(R.array.Filter_date);
        builder.setTitle("")
                .setTitle("Filtrer les réunions par date")
                .setSingleChoiceItems(date, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Filtrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        builder.show();
    }
}
