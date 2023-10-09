package com.example.contactmanagerappusingroom.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
//DAO= Data Access Object
public interface ContactDao {

    @Insert
    long addContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Query("select * from contacts")
    LiveData<List<Contact>> getAllContacts();

    @Query("select * from contacts where contact_id ==:contactId")
    LiveData<Contact> getContact(long contactId);
}
