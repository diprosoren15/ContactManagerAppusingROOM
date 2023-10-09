package com.example.contactmanagerappusingroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.contactmanagerappusingroom.db.Contact;
import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private ContactRepository myRepository;


    public MyViewModel(@NonNull Application application) {
        super(application);

        myRepository = new ContactRepository(application);

    }

    public LiveData<List<Contact>> getAllContact() {
        return myRepository.getAllContacts();
    }

    public LiveData<Contact> getAContact(long contactId) {
        return myRepository.getContact(contactId);
    }

    public void addNewContact(Contact contact) {
        myRepository.addContact(contact);
    }

    public void deleteContact(Contact contact) {
        myRepository.deleteContact(contact);
    }

}
