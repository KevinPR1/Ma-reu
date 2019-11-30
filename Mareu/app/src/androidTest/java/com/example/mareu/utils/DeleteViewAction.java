package com.example.mareu.utils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;

import com.example.mareu.R;

import org.hamcrest.Matcher;

/**
 * Created by Kevin  - Openclassrooms on 29/11/2019
 */
public class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {
        View button = view.findViewById(R.id.item_list_meeting_delete_button);
        // Maybe check for null
        button.performClick();
    }
}
