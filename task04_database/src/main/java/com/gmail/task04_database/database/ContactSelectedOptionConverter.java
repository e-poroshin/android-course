package com.gmail.task04_database.database;

import androidx.room.TypeConverter;


public class ContactSelectedOptionConverter {

    @TypeConverter
    public static String fromOptionToString(ContactSelectedOption option) {
        if (option == null)
            return null;
        return option.toString();
    }

    @TypeConverter
    public static ContactSelectedOption fromStringToOption(String option) {
        if (option.equals(ContactSelectedOption.EMAIL.toString())) {
            return ContactSelectedOption.EMAIL;
        } else if (option.equals(ContactSelectedOption.PHONE.toString())) {
            return ContactSelectedOption.PHONE;
        } else {
            throw new IllegalArgumentException("Could not recognize option");
        }
    }
}
