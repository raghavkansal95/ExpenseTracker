package com.example.expensetracker.Migration;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class MyMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema realmSchema=realm.getSchema();

        if (oldVersion==1){
            final RealmObjectSchema userSchema=realmSchema.get("User");
            userSchema.addField("name",String.class);
            userSchema.addField("phone",String.class);
            userSchema.addField("email",String.class);
        }

          if (oldVersion==2){
              final RealmObjectSchema userSchema=realmSchema.get("User");
              userSchema.addField("displayimage",String.class);
          }

        if (oldVersion==3){
            final RealmObjectSchema userSchema=realmSchema.get("BuyList");
            userSchema.addField("date",String.class);
        }

        if (oldVersion==4){
            final RealmObjectSchema userSchema=realmSchema.get("BuyList");
            userSchema.addField("updateddate",String.class);
        }
    }
}
