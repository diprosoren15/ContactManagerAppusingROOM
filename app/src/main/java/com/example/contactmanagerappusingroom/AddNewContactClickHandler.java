package com.example.contactmanagerappusingroom;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.example.contactmanagerappusingroom.db.Contact;

public class AddNewContactClickHandler {

    Contact contact;
    Context context;

    MyViewModel myViewModel;

    public AddNewContactClickHandler(Contact contact, Context context, MyViewModel myViewModel) {
        this.contact = contact;
        this.context = context;
        this.myViewModel = myViewModel;
    }

    public void onAddContactButtonClicked(View view) {

        if (contact.getName() == null || contact.getEmailID() == null) {

            Toast.makeText(context, "Fields cannot be empty!", Toast.LENGTH_SHORT).show();

        } else {

            Intent i = new Intent(context, MainActivity.class);
            Contact c = new Contact(contact.getName(), contact.getEmailID());
            myViewModel.addNewContact(c);
            context.startActivity(i);

        }

    }
}
