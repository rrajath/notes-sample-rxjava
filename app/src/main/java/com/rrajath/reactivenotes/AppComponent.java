package com.rrajath.reactivenotes;

import android.app.Application;

import com.rrajath.reactivenotes.notes.NotesComponent;
import com.rrajath.reactivenotes.notes.NotesModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(
        modules = {
                AppModule.class,
                DatabaseModule.class
        }
)
public interface AppComponent {
    NotesComponent plus(NotesModule notesModule);
    Application application();
}
