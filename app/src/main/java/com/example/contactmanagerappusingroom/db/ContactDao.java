package com.example.contactmanagerappusingroom.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactmanagerappusingroom.db.Entity.Contact;

import java.util.List;

@Dao
//DAO= Data Access Object
public interface ContactDao {

    @Insert
    public long addContact(Contact contact);

    @Update
    public void updateContact(Contact contact);

    @Delete
    public void deleteContact(Contact contact);

    @Query("select * from contacts")
    public List<Contact> getAllContacts();

    @Query("select * from contacts where contact_id ==:contactId")
    public Contact getContact(long contactId);
}
