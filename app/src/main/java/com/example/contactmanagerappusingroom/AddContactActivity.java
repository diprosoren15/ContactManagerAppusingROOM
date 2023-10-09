package com.example.contactmanagerappusingroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.contactmanagerappusingroom.databinding.ActivityAddContactBinding;
import com.example.contactmanagerappusingroom.db.Contact;

public class AddContactActivity extends AppCompatActivity {

    private ActivityAddContactBinding addContactBinding;
    private AddNewContactClickHandler clickHandler;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        contact = new Contact();

        addContactBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_contact);
        addContactBinding.setContact(contact);

        //Instance of view model
        MyViewModel myViewModel = new ViewModelProvider(this).get(MyViewModel.class);

        clickHandler = new AddNewContactClickHandler(contact,this, myViewModel);
        addContactBinding.setClickhandler(clickHandler);



    }
}