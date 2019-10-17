package com.labzhynskyi.reminder.presenter;

import com.labzhynskyi.reminder.model.Day;
import com.labzhynskyi.reminder.view.IDayListActivity;

interface IDayListPresenter {
    void attachView(IDayListActivity videoListActivity);
    void detachView();
    void getWeek();
    void onClickedFab();
    void onClickDay(int position, Day day);


}
