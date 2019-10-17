package com.labzhynskyi.reminder.presenter;

import android.icu.text.SimpleDateFormat;
import android.widget.Toast;


import com.labzhynskyi.reminder.model.App;
import com.labzhynskyi.reminder.model.DataBaseHelper;
import com.labzhynskyi.reminder.model.Day;
import com.labzhynskyi.reminder.model.Note;
import com.labzhynskyi.reminder.view.INoteListActivity;
import com.labzhynskyi.reminder.view.NoteListActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


public class NoteListPresenter implements INoteListPresenter, DataBaseHelper.DataBaseHelperCallback {

    private static final String TAG = "NoteListPresenter";

    private INoteListActivity mINoteListActivity;
    private List<Note> mNotes;
    private Note mNote;
    private DataBaseHelper mDataBaseHelper;
    private Day mDay;


    public void attachView(NoteListActivity noteListActivity) {
        mINoteListActivity = noteListActivity;
    }

    public void detachView() {
        mINoteListActivity = null;
    }

    @Override
    public void getNoteCallback(List<Note> noteList) {
    }

    @Override
    public void noteIsSaved() {

    }

    @Override
    public void noteIsDelete() {
        Toast.makeText(App.getAppContext(), "Заметка удалена", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void getNoteList(final Day day) {
        mDay = day;
        setDayNote(day.getNotes());
    }


    @Override
    public void onClickedFabNote() {
        mINoteListActivity.createActivityNote();
    }

    @Override
    public void clickedNote(Note note) {
        mNote = note;
        mINoteListActivity.showQueryDialog();
    }

    @Override
    public void deleteNote() {
        mDataBaseHelper = new DataBaseHelper(this);
        mDataBaseHelper.deleteNote(mNote);

    }


    private void setDayNote(Set<Note> notes) {
        mNotes = new ArrayList<>(notes);
        Collections.sort(mNotes);
        mINoteListActivity.updateUI(mNotes);
        mINoteListActivity.setTitle(mDay);

    }


}
