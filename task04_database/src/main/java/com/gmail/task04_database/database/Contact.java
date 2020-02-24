package com.gmail.task04_database.database;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;

@Entity (tableName = "contacts")
public class Contact implements Serializable {

    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "data")
    private String data;
    @ColumnInfo(name = "option")
    @TypeConverters({ContactSelectedOptionConverter.class})
    private ContactSelectedOption option;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private String id;

    public Contact(String name, String data, ContactSelectedOption option, String id) {
        this.name = name;
        this.data = data;
        this.option = option;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ContactSelectedOption getOption() {
        return option;
    }

    public void setOption(ContactSelectedOption option) {
        this.option = option;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public static Contact fromContentValues(ContentValues values) {
        String name = "";
        String data = "";
        ContactSelectedOption option = null;
        String id = "";

        if (values.containsKey("name")) {
            name = values.getAsString("name");
        }
        if (values.containsKey("data")) {
            data = values.getAsString("data");
        }
        if (values.containsKey("option")) {
            option = ContactSelectedOptionConverter.fromStringToOption(values.getAsString("option"));
        }
        if (values.containsKey("id")) {
            id = values.getAsString("id");
        }
        final Contact contact = new Contact(name, data, option, id);
        return contact;
    }
}
