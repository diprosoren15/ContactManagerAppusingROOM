package com.example.contactmanagerappusingroom.db.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {


    //Variables
    @ColumnInfo(name = "contact_id")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contact_name")
    private String name;

    @ColumnInfo(name = "contact_email")
    private String emailID;

    //Constructors
    @Ignore
    public Contact() {
    }

    public Contact(String name, String emailID) {
        this.name = name;
        this.emailID = emailID;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}
