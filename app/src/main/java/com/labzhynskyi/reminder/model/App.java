package com.labzhynskyi.reminder.model;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

public class App extends Application {

    public static App instance;
    private static Context appContext;
    private AppDataBase mAppDataBase;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mAppDataBase = Room.databaseBuilder(this, AppDataBase.class, "database")
                .build();
        appContext = getApplicationContext();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDataBase getDatabase() {
        return mAppDataBase;
    }

    public static Context getAppContext() {
        return appContext;
    }
}