package com.example.mareu.Fragments;



import android.support.v4.app.Fragment;


import com.example.mareu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {


    @Override
    protected BaseFragment newInstance() {
        return (new MainFragment());
    }

    @Override
    protected void updateDesign() {

    }

    @Override
    protected int getFragmentLayout() {
        return (R.layout.fragment_main);
    }

    @Override
    protected void configureDesign() {

    }

    @Override
    protected void init() {

    }

    @Override
    protected void configureClick() {

    }
}
