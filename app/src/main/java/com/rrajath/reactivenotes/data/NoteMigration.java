package com.rrajath.reactivenotes.data;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

public class NoteMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            schema.create("Note")
                    .addField("title", String.class)
                    .addField("description", String.class);
            oldVersion++;
        }

        if (oldVersion == 1) {
            schema.create("Note")
                    .addField("id", String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("title", String.class)
                    .addField("description", String.class);
            oldVersion++;
        }
    }

}
