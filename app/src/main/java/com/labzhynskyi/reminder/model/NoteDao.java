package com.labzhynskyi.reminder.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import io.reactivex.Single;

@Dao
public interface NoteDao {


    @Query("SELECT * FROM note")
    Single<List<Note>> getAllNote();

    @Insert
    Single<Long> insert(Note note);


    @Delete
    Single<Integer> delete(Note note);
}
