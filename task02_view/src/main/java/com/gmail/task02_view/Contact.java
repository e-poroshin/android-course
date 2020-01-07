package com.gmail.task02_view;

import java.io.Serializable;

public class Contact implements Serializable {

    private String name;
    private String data;
    private ContactSelectedOption option;
    private String id;

    public Contact() {
    }

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

}
