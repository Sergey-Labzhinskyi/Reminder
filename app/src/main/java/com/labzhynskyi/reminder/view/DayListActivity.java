package com.labzhynskyi.reminder.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.labzhynskyi.reminder.R;
import com.labzhynskyi.reminder.model.Day;
import com.labzhynskyi.reminder.presenter.DayListPresenter;


import java.util.List;

public class DayListActivity extends AppCompatActivity implements DayListFragment.CallbackOnClickListDay, IDayListActivity {

    private static final String TAG = "DayListActivity";
    private static final String DAY = "DAY";

     private List<Day> mDays;
      private DayListPresenter mDayListPresenter;
      private Toolbar mToolbar;
      private DayListFragment mNoteListFragment;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        mDayListPresenter = new DayListPresenter();
        getDaysList();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);




    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDayListPresenter.detachView();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getDaysList() {
        mDayListPresenter.attachView(this);
        mDayListPresenter.getWeek();
    }



    //implements DayListFragment.CallbackOnClickListDay
    @Override
    public void onClickedDay(int position, Day day) {
        mDayListPresenter.onClickDay(position, day);
    }

    @Override
    public void onClickedFab() {
        mDayListPresenter.onClickedFab();
    }




    //implements IDayListActivity
    @Override
    public void updateUI(List<Day> list) {
        mDays = list;
        final FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        mNoteListFragment = DayListFragment.newInstance(mDays);
        Log.d(TAG, "newInstance" + mDays.size());
        ft.add(R.id.fragment_container, mNoteListFragment);
        ft.commit();
        Log.d(TAG, "updateUI" + mDays.size());
    }

    @Override
    public void createActivityNote() {
         Intent intent = new Intent(this, NoteActivity.class);
         startActivity(intent);
    }

    @Override
    public void createActivityListNote(Day day) {
        Intent intent = new Intent(this, NoteListActivity.class);
         intent.putExtra(DAY, day);
        startActivity(intent);
    }
}
