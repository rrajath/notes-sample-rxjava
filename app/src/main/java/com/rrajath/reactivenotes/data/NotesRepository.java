package com.rrajath.reactivenotes.data;

import com.rrajath.reactivenotes.data.model.Note;
import com.rrajath.reactivenotes.service.RealmService;

import java.util.List;

import javax.inject.Inject;

import io.realm.RealmList;
import rx.Observable;

public class NotesRepository {
    private final RealmService realmService;

    @Inject
    public NotesRepository(RealmService realmService) {
        this.realmService = realmService;
    }

    public Observable<List<Note>> getAllNotes() {
        List<Note> notes = translateToList(realmService.getAllNotes());
        if (notes.size() == 0) {
            return Observable.empty();
        }
        return Observable.just(notes);
    }

    private List<Note> translateToList(RealmList<Note> notes) {
        return notes.subList(0, notes.size());
    }

    public boolean deleteNote(int position) {
        Note note = realmService.getAllNotes().get(position);
        return realmService.deleteNote(note);
    }
}
