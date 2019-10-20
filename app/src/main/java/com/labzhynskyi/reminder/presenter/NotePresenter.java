package com.labzhynskyi.reminder.presenter;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.labzhynskyi.reminder.model.App;
import com.labzhynskyi.reminder.model.DataBaseHelper;
import com.labzhynskyi.reminder.model.Note;

import com.labzhynskyi.reminder.view.INoteActivity;

import java.util.Calendar;

import java.util.List;


public class NotePresenter implements INotePresenter, DataBaseHelper.DataBaseHelperCallback {

    private static final String TAG = "NotePresenter";
    private INoteActivity mINoteActivity;
    private String mDate;
    private Note mNote;
    private Calendar mCalendar;

    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private SimpleDateFormat mSimpleDateFormat;
    private boolean mClick;
    private DataBaseHelper mDataBaseHelper;

    @Override
    public void attachView(INoteActivity iNoteActivity) {
        mINoteActivity = iNoteActivity;
        Log.d(TAG, "attachView");
    }

    @Override
    public void detachView() {
        mINoteActivity = null;
    }

    @Override
    public void onSelectDate() {
        mINoteActivity.showDialogDate();
    }

    @Override
    public void saveDate(int year, int month, int dayOfMonth) {
        mYear = year;
        mMonth = month;
        mDayOfMonth = dayOfMonth;

        if (mNote == null) {
            mNote = new Note();
        }
        mINoteActivity.showDialogTime();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void saveTime(int hour, int minute) {

        mCalendar = Calendar.getInstance();
        mCalendar.set(mYear, mMonth, mDayOfMonth, hour, minute);
        mNote.setCalendar(mCalendar);


        mSimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        mDate = mSimpleDateFormat.format(mNote.getCalendar().getTime());
        mINoteActivity.setDate(mDate);


    }

    @Override
    public void onClickedDescription(boolean click) {
        mClick = click;
        mINoteActivity.getDescription();

    }

    @Override
    public void saveDescription(String description) {

        if (mNote == null) {
            mNote = new Note();
        }
        mNote.setDescription(description);
    }

    @Override
    public void saveNote() {
        mDataBaseHelper = new DataBaseHelper(this);
        

        if (mClick) {
            if (mNote.getDescription() != null && mNote.getCalendar() != null) {
mDataBaseHelper.saveNote(mNote);
            } else {
                Toast.makeText(App.getAppContext(), "Введите данные", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(App.getAppContext(), "Введите данные", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void getNoteCallback(List<Note> mNoteList) {

    }

    @Override
    public void noteIsSaved() {
        Toast.makeText(App.getAppContext(), "Заметка сохранена", Toast.LENGTH_SHORT).show();
        mINoteActivity.returnActivity();
    }

    @Override
    public void noteIsDelete() {

    }
}
