package com.gmail.task04_database.database;

public enum ContactSelectedOption {

    EMAIL("Email"),
    PHONE("Phone");

    private final String text;

    ContactSelectedOption(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
