package com.example.contactmanagerappusingroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.contactmanagerappusingroom.adapter.MyAdapter;
import com.example.contactmanagerappusingroom.databinding.ActivityMainBinding;
import com.example.contactmanagerappusingroom.db.ContactAppDatabase;
import com.example.contactmanagerappusingroom.db.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    MyAdapter contactAdapter;
    ArrayList<Contact> contactsList = new ArrayList<>();
    RecyclerView recyclerView;
    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandler clickHandler;
    ContactAppDatabase contactAppDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        clickHandler = new MainActivityClickHandler(this);
        activityMainBinding.setClickhandler(clickHandler);

        recyclerView = activityMainBinding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        contactAdapter = new MyAdapter(contactsList);
        contactAppDatabase = ContactAppDatabase.getInstance(this);

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);


        viewModel.getAllContact().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {

                contactsList.clear();

                for (Contact c : contacts) {
                    Log.v("TAG", c.getName());
                    contactsList.add(c);
                }
                contactAdapter.notifyDataSetChanged();
            }
        });

        //Linking RecyclerView with adapter
        recyclerView.setAdapter(contactAdapter);

        //swipe to delete contact
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //on swiping items to the left

                Contact c = contactsList.get(viewHolder.getAdapterPosition());
                viewModel.deleteContact(c);

            }
        }).attachToRecyclerView(recyclerView);
    }
}