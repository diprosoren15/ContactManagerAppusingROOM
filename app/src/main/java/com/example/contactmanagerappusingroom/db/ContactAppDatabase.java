package com.example.contactmanagerappusingroom.db;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.example.contactmanagerappusingroom.db.Entity.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactAppDatabase extends RoomDatabase {

    //Linking DAO with database
    public abstract ContactDao getContactDao();

}
