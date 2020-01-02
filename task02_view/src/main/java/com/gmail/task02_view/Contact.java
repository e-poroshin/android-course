package com.gmail.task02_view;

import java.io.Serializable;

public class Contact implements Serializable {

    private String name;
    private String data;
    private boolean isEmailSelected;
    private int position;

    public Contact() {
    }

    public Contact(String name, String data1, boolean isPhoneSelected, int position) {
        this.name = name;
        this.data = data1;
        this.isEmailSelected = isPhoneSelected;
        this.position = position;
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

    public boolean isEmailSelected() {
        return isEmailSelected;
    }

    public void setEmailSelected(boolean emailSelected) {
        isEmailSelected = emailSelected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
