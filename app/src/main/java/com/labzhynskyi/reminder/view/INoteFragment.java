package com.labzhynskyi.reminder.view;

public interface INoteFragment {
    void showDialogDate();

    void showDialogTime();

    void setDate(String date);

    void getDescription();

    void returnActivity();
}
