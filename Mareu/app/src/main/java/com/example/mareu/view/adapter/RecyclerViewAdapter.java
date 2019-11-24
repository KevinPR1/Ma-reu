package com.example.mareu.view.adapter;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mareu.R;

import com.example.mareu.controllers.events.DeleteMeetingEvent;
import com.example.mareu.model.Meeting;


import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kevin  - Openclassrooms on 09/11/2019
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Meeting> mMeetingList;


    public RecyclerViewAdapter(List<Meeting> meetingList) {
        mMeetingList = meetingList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list_meetings, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Meeting meeting = mMeetingList.get(position);
        viewHolder.updateView(meeting);
        viewHolder.configureOnclickDeleteButton(meeting);

    }

    @Override
    public int getItemCount() {
        return mMeetingList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_meeting_avatar)
        public CircleImageView circleImageView;
        @BindView(R.id.item_title)
        public TextView title;
        @BindView(R.id.item_guest)
        public TextView guests;
        @BindView(R.id.item_list_meeting_delete_button)
        public ImageButton deleteButton;
        @BindView(R.id.item_title2)
        public TextView title2;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void updateView(Meeting meeting) {

            String updateTitle1 = meeting.getSubject();
            if (updateTitle1.length() > 30) {
                updateTitle1 = updateTitle1.substring(0, 30);
                updateTitle1 += "...";
            }
            title.setText(updateTitle1);

            String updateTitle2 = meeting.getDate() + " - " + meeting.getHour() + " - " + meeting.getPlace();
            if (updateTitle2.length() > 30) {
                updateTitle2 = updateTitle2.substring(0, 30);
                updateTitle2 += "...";
            }
            title2.setText(updateTitle2);

            circleImageView.setImageResource(meeting.getImage());

            String mail = meeting.getParticipants();
            if (mail.length() > 30) {
                mail = mail.substring(0, 30);
                mail += "...";
            }
            guests.setText(mail);


        }


        public void configureOnclickDeleteButton(final Meeting meeting) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(), "Réunion supprimé: " + meeting.getSubject(), Toast.LENGTH_SHORT).show();
                    EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
                }
            });
        }


    }

}
