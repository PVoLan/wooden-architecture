package ru.pvolan.archsample.application;

import android.app.Application;

import ru.pvolan.container.Container;

public class SampleApp extends Application {

    private static SampleApp app;

    public static SampleApp getApp() {
        return app;
    }

    public static Container cnt() {
        return app.container;
    }

    private Container container;

    @Override
    public void onCreate() {
        super.onCreate();

        container = new Container(this);
        app = this;
    }
}
