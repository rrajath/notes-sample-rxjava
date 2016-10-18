package com.rrajath.reactivenotes;

import android.app.Application;
import android.content.Context;

import com.rrajath.reactivenotes.util.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return application.getApplicationContext();
    }

    @Singleton
    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }
}
