package com.labzhynskyi.reminder.presenter;

import com.labzhynskyi.reminder.view.INoteActivity;

import java.util.Calendar;
import java.util.Date;

interface INotePresenter {
    void attachView(INoteActivity iNoteActivity);
    void detachView();
    void onSelectDate();

    void saveDate(int year, int month, int dayOfMonth);

    void saveTime(int hour, int minute);

    void saveNote();

    void saveDescription(String description);

    void onClickedDescription(boolean click);
}
