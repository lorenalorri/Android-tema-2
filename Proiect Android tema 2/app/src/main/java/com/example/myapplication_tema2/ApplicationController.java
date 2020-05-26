package com.example.myapplication_tema2;

import android.app.Application;

import androidx.room.Room;

public class ApplicationController extends Application {
    private AppDatabase appDatabase;
    private static ApplicationController applicationController;

    static ApplicationController getInstance(){
        return applicationController;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationController = this;
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "userdb").build();
    }

    public AppDatabase getDataBaseInstance(){
        return appDatabase;
    }
}

