package com.labzhynskyi.reminder.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

public class Day implements Serializable {

    private String name;
    private Calendar mCalendar;
    private Date mDate;
    private Set<Note> mNotes;

    public Day(String name, Date date, Set notes) {
        this.name = name;
        this.mDate = date;
        this.mNotes = notes;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getCalendar() {
        return mCalendar = Calendar.getInstance();
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }

    public Set<Note> getNotes() {
        return mNotes;
    }

    public void setNotes(Set<Note> notes) {
        mNotes = notes;
    }
}
