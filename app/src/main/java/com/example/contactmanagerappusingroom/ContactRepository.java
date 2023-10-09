package com.example.contactmanagerappusingroom;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.contactmanagerappusingroom.db.ContactAppDatabase;
import com.example.contactmanagerappusingroom.db.ContactDao;
import com.example.contactmanagerappusingroom.db.Contact;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {

    ContactDao contactDao;
    ExecutorService executorService;
    Handler handler;


    public ContactRepository(Application application) {

        ContactAppDatabase contactAppDatabase = ContactAppDatabase.getInstance(application);
        this.contactDao = contactAppDatabase.getContactDao();
        executorService = Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

    }

    public void addContact(Contact contact) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.addContact(contact);
            }
        });

    }

    public void updateContact(Contact contact) {

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.updateContact(contact);
            }
        });

    }

    public void deleteContact(Contact contact) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                contactDao.deleteContact(contact);
            }
        });
    }

    public LiveData<List<Contact>> getAllContacts() {
        return contactDao.getAllContacts();
    }

    public LiveData<Contact> getContact(long contactId) {
        return contactDao.getContact(contactId);
    }
}
