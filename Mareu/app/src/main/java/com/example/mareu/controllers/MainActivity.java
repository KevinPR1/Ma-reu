package com.example.mareu.controllers;


import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.mareu.R;

import com.example.mareu.controllers.fragments.MainFragment;
import com.example.mareu.view.dialog.DialogCustomMeeting;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private MainFragment mMainFragment ;
    private Snackbar mSnackbar;
    @BindView(R.id.floating_button_add)
    FloatingActionButton mFloatingActionButtong;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.constraintLayout_activity_main)
    ConstraintLayout mConstraintLayout ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSnackbar = Snackbar.make(mConstraintLayout,"Le click a été capturé",Snackbar.LENGTH_SHORT);
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
        getMenuInflater().inflate(R.menu.menu_activity_main,menu);
        return true;
    }

    public void configureToolbar(){
        setSupportActionBar(mToolbar);
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

    /* Configurations Clicks
    * FloatingActionButton
    * items of ToolBar
    */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.filter_icon) {
            mSnackbar.show();
        }
            return super.onOptionsItemSelected(item);
    }

    public void configureFloatingActionButton(){
        mFloatingActionButtong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = new DialogCustomMeeting();
                dialogFragment.show(getSupportFragmentManager(),"dialog");
            }
        });

    }

}
