package com.example.mareu.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mareu.Fragments.MainFragment;
import com.example.mareu.R;

public class MainActivity extends AppCompatActivity {

    private MainFragment mMainFragment ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void configureAndShowMainFragment() {

        //  Get FragmentManager (Support) and Try to find existing instance of fragment in FrameLayout container
        mMainFragment = (MainFragment)
                getSupportFragmentManager().findFragmentById(R.id.framelayout_activity_main);

        if (mMainFragment == null){
            mMainFragment= new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout_activity_main,mMainFragment)
                    .commit();
        }
    }


}
