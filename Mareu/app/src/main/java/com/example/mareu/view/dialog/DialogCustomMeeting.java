package com.example.mareu.view.dialog;


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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
    private EditText subject;
    private EditText hour;
    private EditText guest;
    private Spinner spinner;
    private int currentHour;
    private int currentMinute;
    private MeetingApiService service;
    private ArrayList<Integer> selectedItems = new ArrayList<>();
    private boolean[] checkedItems;
    private TextView duration;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_fragment_custom_meeting, null);

        subject = view.findViewById(R.id.edit_text_subject_dialog);
        hour = view.findViewById(R.id.edit_text__hour_dialog);
        guest = view.findViewById(R.id.edit_text_guest_dialog);
        duration = view.findViewById(R.id.TextView_duration_dialog);
        spinner = view.findViewById(R.id.spinner_dialog);

        service = DI.getMeetingApiService();

        ArrayAdapter<MeetingRoom> adapter = new ArrayAdapter<MeetingRoom>(getActivity(), android.R.layout.simple_spinner_item, service.getMeetingRooms());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        builder.setView(view)
                .setTitle("Création d'une réunion")
                .setPositiveButton("Créer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        MeetingRoom meetingRoom = (MeetingRoom) spinner.getSelectedItem();
                        Meeting meeting = new Meeting(hour.getText().toString(), meetingRoom.getName(), subject.getText().toString(), guest.getText().toString(),"", meetingRoom.getImage());
                        which = 0;
                        if (which == guest.length() || which == hour.length() || which == subject.length()) {
                            dismiss();
                            Toast.makeText(getActivity(), "Remplissez tous les caractères pour  créer une réunion", Toast.LENGTH_LONG).show();
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

        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureTimePicker();
            }
        });
        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureGuest();
            }
        });
        return builder.create();
    }
    // Create Dialog ---------------------------------------------------------------------------------------------------------------------------


    private void configureTimePicker() {
        Calendar calendar = Calendar.getInstance();
        currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentMinute = calendar.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(getContext(), R.style.AppTheme_TimePicker, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int customHour, int customMinute) {
                hour.setText(String.format(Locale.FRANCE, "%02dh%02d", customHour, customMinute));
            }
        }, currentHour, currentMinute, DateFormat.is24HourFormat(getActivity()));
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
    // Show TimePicker Dialog ---------------------------------------------------------------------------------------------------------------------------

    private void configureGuest() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        String[] strMemberList = new String[service.getMemberList().size()];
        for (int i = 0; i < service.getMemberList().size(); i++) {
            strMemberList[i] = service.getMemberList().get(i).getName();
        }

        checkedItems = new boolean[strMemberList.length];
        for (int i = 0; i < checkedItems.length; i++) {
            if (selectedItems.contains(i)) {
                checkedItems[i] = true;
            }
        }

        builder.setTitle("Choisissez les participants")
                .setMultiChoiceItems(strMemberList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {

                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            selectedItems.add(which);
                            checkedItems[which] = true;
                        } else if (selectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            selectedItems.remove(Integer.valueOf(which));
                            checkedItems[which] = false;
                        }
                    }
                })
                .setPositiveButton("Importer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        StringBuilder items = new StringBuilder();
                        for (int i = 0; i < selectedItems.size(); i++) {
                            items.append(service.getMemberList().get(selectedItems.get(i)).getMail());
                            if (i != selectedItems.size() - 1) {
                                items.append(", ");
                            }
                        }
                        guest.setText(items.toString());
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
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                        }
                        selectedItems.clear();
                        guest.setText("");
                    }
                });

        builder.show();
    }
    // Show Dialog (MutliChoiceItems) ---------------------------------------------------------------------------------------------------------------------------

}
