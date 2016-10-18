package com.rrajath.reactivenotes.notes;

import com.rrajath.reactivenotes.util.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
        modules = {
                NotesModule.class
        }
)
public interface NotesComponent {
    void inject(NotesActivity notesActivity);
}
