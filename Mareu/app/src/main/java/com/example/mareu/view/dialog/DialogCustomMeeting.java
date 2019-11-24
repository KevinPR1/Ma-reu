package com.example.mareu.view.dialog;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.annotation.NonNull;

import android.support.v4.app.DialogFragment;

import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mareu.R;
import com.example.mareu.controllers.di.DI;
import com.example.mareu.controllers.events.CreateMeetingEvent;
import com.example.mareu.model.Meeting;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.services.MeetingApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Calendar;

import java.util.Locale;
import java.util.Objects;


/**
 * Created by Kevin  - Openclassrooms on 20/11/2019
 */
public class DialogCustomMeeting extends DialogFragment {
    private EditText mSubject, mGuest, mHour, mDate;
    private Spinner mSpinner;
    private MeetingApiService mMeetingApiService;
    private ArrayList<Integer> mSelectedItems = new ArrayList<>();
    private boolean[] mCheckedItems;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_custom_meeting, null);

        mSubject = view.findViewById(R.id.edit_text_subject_dialog);
        mHour = view.findViewById(R.id.edit_text__hour_dialog);
        mGuest = view.findViewById(R.id.edit_text_guest_dialog);
        mSpinner = view.findViewById(R.id.spinner_dialog);
        mDate = view.findViewById(R.id.edit_text__date_dialog);

        mMeetingApiService = DI.getMeetingApiService();

        ArrayAdapter<MeetingRoom> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, mMeetingApiService.getMeetingRooms());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        builder.setView(view)
                .setTitle("Création d'une réunion")
                .setPositiveButton("Créer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        MeetingRoom meetingRoom = (MeetingRoom) mSpinner.getSelectedItem();
                        Meeting meeting = new Meeting(mHour.getText().toString(), meetingRoom.getName(), mSubject.getText().toString(), mGuest.getText().toString(), mDate.getText().toString(), meetingRoom.getImage());
                        which = 0;
                        if (which == mGuest.length() || which == mHour.length() || which == mSubject.length() || which == mDate.length()) {
                            dismiss();
                            Toast.makeText(getActivity(), "Remplissez tous les champs pour  créer une réunion", Toast.LENGTH_LONG).show();
                        } else if (mMeetingApiService.getMeetings().contains(meeting)){
                            dismiss();
                            Toast.makeText(getActivity(), "This meeting already exist !", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getActivity(), "Création en cours...", Toast.LENGTH_LONG).show();
                            EventBus.getDefault().post(new CreateMeetingEvent(meeting));
                        }


                    }
                })
                .setNegativeButton("Retour", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                        Toast.makeText(getContext(), "Création annulé", Toast.LENGTH_SHORT).show();
                    }
                });

        mHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureTimePicker();
            }
        });
        mGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureGuest();
            }
        });
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureDate();
            }
        });

        return builder.create();
    }
    // Create Dialog ---------------------------------------------------------------------------------------------------------------------------


    private void configureTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.AppTheme_TimePicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int customHour, int customMinute) {
                mHour.setText(String.format(Locale.FRANCE, "%02dh%02d", customHour, customMinute));
            }
        }, currentHour, currentMinute, DateFormat.is24HourFormat(getActivity()));
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    // Show TimePicker Dialog ---------------------------------------------------------------------------------------------------------------------------

    private void configureGuest() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        String[] strMemberList = new String[mMeetingApiService.getMemberList().size()];
        for (int i = 0; i < mMeetingApiService.getMemberList().size(); i++) {
            strMemberList[i] = mMeetingApiService.getMemberList().get(i).getName();
        }

        mCheckedItems = new boolean[strMemberList.length];
        for (int i = 0; i < mCheckedItems.length; i++) {
            if (mSelectedItems.contains(i)) {
                mCheckedItems[i] = true;
            }
        }

        builder.setTitle("Choisissez les participants")
                .setMultiChoiceItems(strMemberList, mCheckedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {

                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedItems.add(which);
                            mCheckedItems[which] = true;
                        } else if (mSelectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedItems.remove(Integer.valueOf(which));
                            mCheckedItems[which] = false;
                        }
                    }
                })
                .setPositiveButton("Importer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        StringBuilder items = new StringBuilder();
                        for (int i = 0; i < mSelectedItems.size(); i++) {
                            items.append(mMeetingApiService.getMemberList().get(mSelectedItems.get(i)).getMail());
                            if (i != mSelectedItems.size() - 1) {
                                items.append(", ");
                            }
                        }
                        mGuest.setText(items.toString());
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < mCheckedItems.length; i++) {
                            mCheckedItems[i] = false;
                        }
                        mSelectedItems.clear();
                        mGuest.setText("");
                    }
                });

        builder.show();
    }
    // Show Dialog (MutliChoiceItems) ---------------------------------------------------------------------------------------------------------------------------

    private void configureDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(Objects.requireNonNull(getActivity()), R.style.AppTheme_DatePicker, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String customDate = day + "/" + month + "/" + year;
                mDate.setText(customDate);
            }
        }, year, month, day);

        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

    }
    // Show DatePicker ---------------------------------------------------------------------------------------------------------------------------
}
