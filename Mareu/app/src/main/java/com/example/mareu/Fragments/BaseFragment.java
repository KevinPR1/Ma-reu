package com.example.mareu.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Kevin  - Openclassrooms on 09/11/2019
 */
    public abstract class BaseFragment extends Fragment {

        // 1 - Force developer implement those methods
        protected abstract com.example.mareu.Fragments.BaseFragment newInstance();
        protected abstract void updateDesign();
        protected abstract int getFragmentLayout();
        protected abstract void configureDesign();
        protected abstract void init();
        protected abstract void  configureClick();




        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.updateDesign();
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // 2 - Get layout identifier from abstract method
            View view = inflater.inflate(getFragmentLayout(), container, false);
            // 3 - Binding Views
            ButterKnife.bind(this, view);
            this.configureDesign();
            this.init();
            this.configureClick();
            return(view);
        }
    }

