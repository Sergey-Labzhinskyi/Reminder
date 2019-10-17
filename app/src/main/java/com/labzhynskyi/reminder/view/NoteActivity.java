package com.labzhynskyi.reminder.view;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.labzhynskyi.reminder.R;
import com.labzhynskyi.reminder.presenter.NotePresenter;

import java.util.Calendar;
import java.util.Date;

public class NoteActivity extends AppCompatActivity implements NoteFragment.CallbackOnClickNote, INoteActivity, DateFragment.CallbackOnSelectDate, TimeFragment.CallbackOnSelectTime {


    private static final String TAG = "NoteActivity";
    private NoteFragment mNoteFragment;
    private NotePresenter mNotePresenter;
    private INoteFragment mINoteFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        mNotePresenter = new NotePresenter();

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mNoteFragment = NoteFragment.newInstance();
            ft.add(R.id.fragment_container, mNoteFragment);
            ft.commit();
        }

        if (mNoteFragment instanceof NoteFragment){
            mINoteFragment = (INoteFragment) mNoteFragment;
        }


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        this.setTitle(R.string.app_name);


        mNotePresenter.attachView(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNotePresenter.attachView(this);
    }

//implements NoteFragment.CallbackOnClickNote
    @Override
    public void onClickedDescription(boolean click) {
        mNotePresenter.onClickedDescription(click);
    }

    @Override
    public void onSaveDescription(String description) {
        mNotePresenter.saveDescription(description);
    }

    @Override
    public void onClickedSaveNote() {
        mNotePresenter.saveNote();

    }

    @Override
    public void onClickedSelectDate() {
        mNotePresenter.onSelectDate();
    }

   // implements DateFragment.CallbackOnSelectDate

    @Override
    public void onSelectDate(int year, int month, int dayOfMonth) {
        mNotePresenter.saveDate(year, month, dayOfMonth);
    }
    // implements TimeFragment.CallbackOnSelectTime
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSelectTime(int hour, int minute) {
        mNotePresenter.saveTime(hour, minute);
    }
    // implements  INoteActivity
    @Override
    public void showDialogDate() {
        mINoteFragment.showDialogDate();
    }

    @Override
    public void showDialogTime() {
        mINoteFragment.showDialogTime();
    }

    @Override
    public void setDate(String date) {
        mINoteFragment.setDate(date);
    }

    @Override
    public void getDescription() {
        mINoteFragment.getDescription();
    }

    @Override
    public void returnActivity() {
        mINoteFragment.returnActivity();
    }




}
