package com.labzhynskyi.reminder.view;

import com.labzhynskyi.reminder.model.Day;
import com.labzhynskyi.reminder.model.Note;

import java.util.List;

public interface INoteListActivity {

    void updateUI(List<Note> list);

    void createActivityNote();

    void showQueryDialog();

    void getNotesList();

    void setTitle(Day day);
}
