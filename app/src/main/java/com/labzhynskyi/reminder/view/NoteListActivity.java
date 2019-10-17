package com.labzhynskyi.reminder.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.labzhynskyi.reminder.R;
import com.labzhynskyi.reminder.model.Day;
import com.labzhynskyi.reminder.model.Note;
import com.labzhynskyi.reminder.presenter.NoteListPresenter;


import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class NoteListActivity extends AppCompatActivity implements NoteListFragment.CallbackOnClickListNote, INoteListActivity, QueryFragment.CallbackOnSelectQuery {

    private static final String TAG = "NoteListActivity";
    private static final String DAY = "DAY";

    private List<Note> mNotes;
    private NoteListPresenter mNoteListPresenter;
    private Toolbar mToolbar;
    private NoteListFragment mNoteListFragment;
    private Day mDay;
    private INoteListFragment mINoteListFragment;
    private SimpleDateFormat mSimpleDateFormat;
    private String mDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        Bundle bundle = getIntent().getExtras();
        mDay = (Day) bundle.getSerializable(DAY);
        mNoteListPresenter = new NoteListPresenter();
        getNotesList();

        if (mNoteListFragment instanceof NoteListFragment) {
            mINoteListFragment = (INoteListFragment) mNoteListFragment;
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNoteListPresenter.detachView();

    }

    @Override
    public void getNotesList() {
        mNoteListPresenter.attachView(this);
        mNoteListPresenter.getNoteList(mDay);
    }


    @Override
    public void onClickedFabNote() {
        mNoteListPresenter.onClickedFabNote();
    }

    @Override
    public void onClickedNote(Note note) {
        mNoteListPresenter.clickedNote(note);
    }


    //implements IDayListActivity
    @Override
    public void updateUI(List<Note> list) {
        mNotes = list;
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        mNoteListFragment = NoteListFragment.newInstance(mNotes);
        Log.d(TAG, "newInstance" + mNotes.size());
        ft.add(R.id.fragment_container, mNoteListFragment);
        ft.commit();
        Log.d(TAG, "updateUI" + mNotes.size());
    }

    @Override
    public void createActivityNote() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void showQueryDialog() {
        mINoteListFragment.showDialogQuery();
    }

    @Override
    public void onClickOk() {
        mNoteListPresenter.deleteNote();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void setTitle(Day day) {
        mSimpleDateFormat = new SimpleDateFormat(" dd.MM.yyyy");
        mDate = mSimpleDateFormat.format(mDay.getDate());
        this.setTitle(day.getName() + " " + mDate);
    }

}
