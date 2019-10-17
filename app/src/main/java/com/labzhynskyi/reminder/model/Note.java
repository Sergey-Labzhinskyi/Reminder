package com.labzhynskyi.reminder.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

@Entity
public class Note implements Serializable, Comparable<Note> {


    @PrimaryKey(autoGenerate = true)
    private int id;

    private Calendar mCalendar;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Calendar getCalendar() {
        return mCalendar;
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id &&
                description.equals(note.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }


    @Override
    public int compareTo(Note note) {
        return mCalendar.compareTo(note.mCalendar);
    }
}
