package com.example.mareu.controllers;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;


import com.example.mareu.R;

import com.example.mareu.controllers.di.DI;
import com.example.mareu.controllers.events.OnDataChangedToFilterListEvent;
import com.example.mareu.controllers.events.OnDateSetToFilterEvent;
import com.example.mareu.controllers.events.OnItemFilterPlaceAndDateEvent;
import com.example.mareu.controllers.events.OnItemNoFilterEvent;
import com.example.mareu.controllers.fragments.MainFragment;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.services.MeetingApiService;
import com.example.mareu.view.dialog.DialogCustomMeeting;


import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Snackbar mSnackbar;
    private MeetingApiService mMeetingApiService;
    private MeetingRoom mMeetingRoom;
    private String customDateToFilter;
    private static final String TAG = "MainActivity";
    private int trackListenner;
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
        MainFragment mainFragment = (MainFragment)
                getSupportFragmentManager().findFragmentById(R.id.framelayout_activity_main);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout_activity_main, mainFragment)
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
                EventBus.getDefault().post(new OnItemNoFilterEvent());
                return true;
            case R.id.filter_mode_meetingRooms:
                trackListenner = 1 ;
                displayDialogToFilterMeetingRooms();
                return true;
            case R.id.filter_mode_date:
                displayDialogToFilterTheDate();
                return true;
            case R.id.filter_mode_place_and_date:
                trackListenner = 2;
                displayDialogToFilterMeetingRooms();
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

        builder.setSingleChoiceItems(roomList, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mMeetingRoom = mMeetingApiService.getMeetingRooms().get(i);
                Toast.makeText(getApplicationContext(), "Salle : " + mMeetingRoom.getName(), Toast.LENGTH_SHORT).show();
            }
        })
                .setPositiveButton("Filtrer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (trackListenner == 1) {
                            EventBus.getDefault().post(new OnDataChangedToFilterListEvent(mMeetingRoom));
                            Log.d(TAG, "onClick: Filter with selected meeting room  = On");
                        }else {
                           displayDialogToFilterTheDate();
                        }
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
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, R.style.AppTheme_DatePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                customDateToFilter = day + "/" + month + "/" + year;
                if (trackListenner == 1) {
                    //eventBus to filter per date
                    EventBus.getDefault().post(new OnDateSetToFilterEvent(customDateToFilter));
                    Toast.makeText(getApplicationContext(), "Filtre : " + customDateToFilter, Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: Filter with selected date  = On");
                }else {
                    //eventBus to filter per meeting room and date
                    EventBus.getDefault().post(new OnItemFilterPlaceAndDateEvent(customDateToFilter,mMeetingRoom));
                    Toast.makeText(getApplicationContext(),"trackListenner : "+trackListenner,Toast.LENGTH_SHORT).show();
                }
            }
        }, year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Filtrer", dialog);
        dialog.show();
    }
}
