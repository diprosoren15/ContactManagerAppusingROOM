package com.example.contactmanagerappusingroom.db;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactAppDatabase extends RoomDatabase {

    //Linking DAO with database
    public abstract ContactDao getContactDao();

    private static ContactAppDatabase dbInstance;

    public static synchronized ContactAppDatabase getInstance(Context context) {

        if (dbInstance == null) {

            dbInstance = Room.databaseBuilder(context.getApplicationContext(),
                    ContactAppDatabase.class,
                    "contacts_db").fallbackToDestructiveMigration().build();

        }
        return dbInstance;
    }

}
