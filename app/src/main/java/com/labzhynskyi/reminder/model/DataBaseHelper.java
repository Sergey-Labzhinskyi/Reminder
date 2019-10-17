package com.labzhynskyi.reminder.model;

import android.util.Log;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DataBaseHelper {

    private static final String TAG = "DataBaseHelper";

    private AppDataBase mAppDataBase = App.getInstance().getDatabase();
    private NoteDao mNoteDao = mAppDataBase.noteDao();
    private List<Note> mNoteList;
    private DataBaseHelperCallback mDataBaseHelperCallback;

    public interface DataBaseHelperCallback {
        void getNoteCallback(List<Note> mNoteList);

        void noteIsSaved();

        void noteIsDelete();
    }


    public void setNoteList(List<Note> noteList) {
        mNoteList = noteList;
    }

    public DataBaseHelper(DataBaseHelperCallback dataBaseHelperCallback) {
        mDataBaseHelperCallback = dataBaseHelperCallback;
    }

    public void getListNoteOfDay() {

        mNoteDao.getAllNote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<List<Note>>() {
                    @Override
                    public void onSuccess(List<Note> noteList) {
                        mNoteList = noteList;
                        setNoteList(mNoteList);
                        mDataBaseHelperCallback.getNoteCallback(mNoteList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void saveNote(Note note) {
        mNoteDao.insert(note)
                .subscribeOn(Schedulers.io())
                .subscribe();

        mDataBaseHelperCallback.noteIsSaved();
    }

    public void deleteNote(Note note) {
        mNoteDao.delete(note)
                .subscribeOn(Schedulers.io())
                .subscribe();
        mDataBaseHelperCallback.noteIsDelete();
    }
}
