package com.example.expensetracker.Migration;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
         final RealmConfiguration realmConfiguration=new RealmConfiguration.Builder()
                                                                           .name("MyRealm.realm")
                                                                           .schemaVersion(4)
                                                                            .build();

         Realm.setDefaultConfiguration(realmConfiguration);
    }
}
