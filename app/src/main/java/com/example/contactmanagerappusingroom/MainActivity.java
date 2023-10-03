package com.example.contactmanagerappusingroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactmanagerappusingroom.adapter.MyAdapter;
import com.example.contactmanagerappusingroom.db.ContactAppDatabase;
import com.example.contactmanagerappusingroom.db.Entity.Contact;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    MyAdapter contactAdapter;
    ArrayList<Contact> contactsList = new ArrayList<>();
    RecyclerView recyclerView;

    ContactAppDatabase contactAppDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Manager App");

        //adapterview = recyclerview

        recyclerView = findViewById(R.id.recyclerView);

        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Log.d("TAG", "Database has been created");

            }

            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);

                Log.d("TAG", "Database has been opened");
            }
        };


        contactAppDatabase = Room.databaseBuilder(getApplicationContext(), ContactAppDatabase.class, "ContactDB").allowMainThreadQueries().build();

        //Displays all contacts in background
        displayAllContactsInBackground();


        contactAdapter = new MyAdapter(this, contactsList, MainActivity.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndUpdateContact(false, null, -1);
            }
        });


    }

    public void addAndUpdateContact(final boolean isUpdated, final Contact contact, final int position) {

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View itemView = layoutInflater.inflate(R.layout.layout_add_contacts, null);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setView(itemView);

        TextView contactTitle = itemView.findViewById(R.id.textViewTitle);
        final EditText name = itemView.findViewById(R.id.addNameET);
        final EditText email = itemView.findViewById(R.id.addEmailET);

        contactTitle.setText(!isUpdated ? "Add new contact" : "Edit Contact");

        if (isUpdated && contact != null) {

            name.setText(contact.getName());
            email.setText(contact.getEmailID());

        }

        alertDialog.setCancelable(false).setPositiveButton(isUpdated ? "Update" : "Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        }).setNegativeButton(!isUpdated ? "Cancel" : "Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (isUpdated) {
                    DeleteContact(contact, position);
                } else {
                    dialogInterface.cancel();
                }

            }
        });

        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(name.getText().toString()) || TextUtils.isEmpty(email.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Fields cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    dialog.dismiss();
                }
                if (isUpdated && contact != null) {
                    updateContact(name.getText().toString(), email.getText().toString(), position);
                } else {
                    createContact(name.getText().toString(), email.getText().toString());
                }

            }
        });

    }

    private void createContact(String name, String email) {

        long id = contactAppDatabase.getContactDao().addContact(new Contact(0, name, email));
        Contact contact = contactAppDatabase.getContactDao().getContact(id);

        if (contact != null) {
            contactsList.add(0, contact);
            contactAdapter.notifyDataSetChanged();
        }
    }

    private void updateContact(String name, String email, int position) {

        Contact contact = contactsList.get(position);

        contact.setName(name);
        contact.setEmailID(email);

        contactAppDatabase.getContactDao().updateContact(contact);

        contactsList.set(position, contact);
        contactAdapter.notifyDataSetChanged();

    }

    //Method to display all contact in the database
    //Prevents ANR(Application not responding)
    //Efficient way to load data from database
    //Useful when database size is large
    private void displayAllContactsInBackground() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                //Background Work
                contactsList.addAll(contactAppDatabase.getContactDao().getAllContacts());

                //Execute after background work is complete
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        contactAdapter.notifyDataSetChanged();
                    }
                });

            }
        });

    }
    private void DeleteContact(Contact contact, int position) {

        contactsList.remove(position);
        contactAppDatabase.getContactDao().deleteContact(contact);
        contactAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.settingsMenu) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}