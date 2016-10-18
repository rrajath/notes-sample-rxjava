package com.rrajath.reactivenotes.data.model;

import java.util.UUID;

import io.realm.RealmObject;

public class Note extends RealmObject {
    private String id;
    private String title;
    private String description;

    public Note() {
        this.id = UUID.randomUUID().toString();
        this.title = "";
        this.description = "";
    }

    public Note(String title, String description) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
