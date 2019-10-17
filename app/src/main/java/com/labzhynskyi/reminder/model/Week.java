package com.labzhynskyi.reminder.model;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

public class Week {

    public static List<Day> mDays;
    private Calendar mCalendar;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Day> getWeek() {

        mCalendar = Calendar.getInstance();
        mDays = new ArrayList<>();


        DateFormat df = new SimpleDateFormat("EEEE");
        for (int i = 0; i < 7; i++) {
            mDays.add(new Day(df.format(mCalendar.getTime()).toUpperCase(), mCalendar.getTime(), new HashSet()));
            Log.d("Week", mDays.get(i).getDate().toString());
            Log.d("Week", String.valueOf(mDays.get(i).getNotes().size()));
            mCalendar.add(Calendar.DATE, 1);
        }

        return mDays;
    }


}
