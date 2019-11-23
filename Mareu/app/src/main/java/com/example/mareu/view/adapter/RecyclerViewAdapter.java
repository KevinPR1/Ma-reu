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
        public CircleImageView mCircleImageView;
        @BindView(R.id.item_title)
        public TextView title;
        @BindView(R.id.item_guest)
        public TextView guests;
        @BindView(R.id.item_list_meeting_delete_button)
        public ImageButton deleteButton;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void updateView(Meeting meeting) {

            String update = meeting.getSubject() + " - " + meeting.getDate() + " - " + meeting.getHour() + " - " + meeting.getPlace();
            if (update.length() > 30){
                update = update.substring(0,30);
                update+= "...";
            }
            title.setText(update);
            mCircleImageView.setImageResource(meeting.getImage());

            String mail = meeting.getParticipants();
            if (mail.length() > 30){
                mail = mail.substring(0,30);
                mail+= "...";
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
