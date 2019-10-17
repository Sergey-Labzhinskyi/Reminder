package com.labzhynskyi.reminder.presenter;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;
import android.os.Build;


import androidx.annotation.RequiresApi;


import com.labzhynskyi.reminder.model.DataBaseHelper;
import com.labzhynskyi.reminder.model.Day;
import com.labzhynskyi.reminder.model.Note;
import com.labzhynskyi.reminder.model.Week;
import com.labzhynskyi.reminder.view.IDayListActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DayListPresenter implements IDayListPresenter, DataBaseHelper.DataBaseHelperCallback {

    private static final String TAG = "DayListPresenter";
    private Week mWeek;
    private List<Day> mListDay;
    private IDayListActivity mIDayListActivity;
    private SimpleDateFormat mSimpleDateFormat;
    private DataBaseHelper mDataBaseHelper;
    private Calendar mCalendar;

    @Override
    public void attachView(IDayListActivity iDayListActivity) {
        mIDayListActivity = iDayListActivity;
    }

    @Override
    public void detachView() {
        mIDayListActivity = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getWeek() {
        mDataBaseHelper = new DataBaseHelper(this);
        mListDay = new ArrayList<>();
        mWeek = new Week();
        mListDay = mWeek.getWeek();

        mDataBaseHelper.getListNoteOfDay();

    }

    @Override
    public void onClickedFab() {
        mIDayListActivity.createActivityNote();
    }

    @Override
    public void onClickDay(int position, Day day) {
        mIDayListActivity.createActivityListNote(day);
    }




    @SuppressLint("NewApi")
    public void sortOfDay(List<Note> noteList) {

        for (Note note : noteList) {
            int day = note.getCalendar().get(Calendar.DAY_OF_YEAR);
            int dayEvent = note.getCalendar().get(Calendar.DAY_OF_YEAR);
            mCalendar = Calendar.getInstance();
            int dayCurrentOfYear = mCalendar.get(Calendar.DAY_OF_YEAR);

            if (dayEvent >= dayCurrentOfYear && dayEvent <= dayCurrentOfYear + 7) {

                if (day == dayCurrentOfYear) {
                    mListDay.get(0).getNotes().add(note);
                } else if (day == dayCurrentOfYear + 1) {
                    mListDay.get(1).getNotes().add(note);
                } else if (day == dayCurrentOfYear + 2) {
                    mListDay.get(2).getNotes().add(note);
                } else if (day == dayCurrentOfYear + 3) {
                    mListDay.get(3).getNotes().add(note);
                } else if (day == dayCurrentOfYear + 4) {
                    mListDay.get(4).getNotes().add(note);
                } else if (day == dayCurrentOfYear + 5) {
                    mListDay.get(5).getNotes().add(note);

                } else if (day == dayCurrentOfYear + 6) {
                    mListDay.get(6).getNotes().add(note);
                }
            }
        }

        mIDayListActivity.updateUI(mListDay);

    }

    @Override
    public void getNoteCallback(List<Note> mNoteList) {
        sortOfDay(mNoteList);
    }

    @Override
    public void noteIsSaved() {

    }

    @Override
    public void noteIsDelete() {

    }

}
