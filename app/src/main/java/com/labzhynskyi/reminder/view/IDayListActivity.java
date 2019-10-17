package com.labzhynskyi.reminder.view;

import com.labzhynskyi.reminder.model.Day;

import java.util.List;

public interface IDayListActivity {
    void updateUI(List<Day> list);
    void createActivityNote();
    void createActivityListNote(Day day);
}
