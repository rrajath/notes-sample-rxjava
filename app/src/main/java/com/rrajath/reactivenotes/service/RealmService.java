package com.rrajath.reactivenotes.service;

import com.rrajath.reactivenotes.data.model.Note;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class RealmService {
    private final Realm realm;

    public RealmService(Realm realm) {
        this.realm = realm;
    }

    public void closeRealm() {
        realm.close();
    }

    public RealmList<Note> getAllNotes() {
        RealmResults<Note> results = realm.where(Note.class).findAll();
        RealmList<Note> notes = new RealmList<>();
        notes.addAll(results.subList(0, results.size()));
        return notes;
    }

    public void saveNote(Note note) {
        realm.beginTransaction();
        realm.copyToRealm(note);
        realm.commitTransaction();
    }

    public boolean deleteNote(Note note) {
        realm.beginTransaction();
        try {
            final RealmResults rows = realm.where(Note.class).equalTo("id", note.getId()).findAll();
            rows.deleteFromRealm(0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        realm.commitTransaction();
        return true;
    }

}
