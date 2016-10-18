package com.rrajath.reactivenotes;

import android.app.Application;
import android.content.Context;

public class ReactiveNotesApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static ReactiveNotesApplication get(Context context) {
        return (ReactiveNotesApplication) context.getApplicationContext();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
