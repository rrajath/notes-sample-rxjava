package com.rrajath.reactivenotes;

import android.content.Context;

import com.rrajath.reactivenotes.data.NoteMigration;
import com.rrajath.reactivenotes.data.NotesRepository;
import com.rrajath.reactivenotes.service.RealmService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

@Module
public class DatabaseModule {
    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration(Context context) {
        return new RealmConfiguration.Builder(context)
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .migration(new NoteMigration())
                .build();
    }

    @Provides
    @Singleton
    Realm provideRealm(RealmConfiguration realmConfiguration) {
        Realm.setDefaultConfiguration(realmConfiguration);
        return Realm.getDefaultInstance();
    }

    @Provides
    @Singleton
    RealmService provideRealmService(Realm realm) {
        return new RealmService(realm);
    }

    @Provides
    @Singleton
    NotesRepository provideNotesRepository(RealmService realmService) {
        return new NotesRepository(realmService);
    }
}
