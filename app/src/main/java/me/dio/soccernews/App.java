package me.dio.soccernews;

import android.app.Application;

// FIXME change to DI using Dagger or Hilt

public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }
}
