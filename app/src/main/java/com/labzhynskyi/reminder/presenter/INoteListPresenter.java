package com.labzhynskyi.reminder.presenter;

import com.labzhynskyi.reminder.model.Day;
import com.labzhynskyi.reminder.model.Note;
import com.labzhynskyi.reminder.view.NoteListActivity;


public interface INoteListPresenter {
     void detachView();

     void attachView(NoteListActivity noteListActivity);

     void getNoteList(Day day);

    void onClickedFabNote();

    void clickedNote(Note note);

    void deleteNote();
}

