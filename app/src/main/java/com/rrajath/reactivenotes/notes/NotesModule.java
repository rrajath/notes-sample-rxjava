package com.rrajath.reactivenotes.notes;

import com.rrajath.reactivenotes.data.NotesRepository;
import com.rrajath.reactivenotes.util.ActivityScope;
import com.rrajath.reactivenotes.util.schedulers.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

@Module
public class NotesModule {

    private final NotesContract.View notesView;

    public NotesModule(NotesContract.View notesView) {
        this.notesView = notesView;
    }

    @Provides
    @ActivityScope
    NotesContract.View provideNotesView() {
        return this.notesView;
    }

    @Provides
    @ActivityScope
    NotesActivity provideNotesActivity() {
        return new NotesActivity();
    }

    @Provides
    @ActivityScope
    NotesPresenter provideNotesPresenter(NotesRepository repository, NotesContract.View notesView, SchedulerProvider schedulerProvider) {
        return new NotesPresenter(repository, notesView, schedulerProvider);
    }
}
