package com.example.contactmanagerappusingroom.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.contactmanagerappusingroom.R;
import com.example.contactmanagerappusingroom.databinding.ContactListItemsBinding;
import com.example.contactmanagerappusingroom.db.Contact;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Contact> contacts;


    public MyAdapter(ArrayList<Contact> contacts) {

        this.contacts = contacts;

    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContactListItemsBinding contactListItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.contact_list_items, parent, false );
        return new MyViewHolder(contactListItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

        Contact currentItem = contacts.get(position);
        holder.contactListItemsBinding.setContacts(currentItem);

    }

    @Override
    public int getItemCount() {
        if (contacts != null) {
            return contacts.size();
        }
        return 0;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ContactListItemsBinding contactListItemsBinding;

        public MyViewHolder(@NonNull ContactListItemsBinding contactListItemsBinding) {
            super(contactListItemsBinding.getRoot());
            this.contactListItemsBinding = contactListItemsBinding;
        }
    }
}
