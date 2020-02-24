package com.example.task_contentresolver;

public class Contact {

    private String name;
    private String data;
    private String option;
    private String id;

    public Contact(String name, String data, String option, String id) {
        this.name = name;
        this.data = data;
        this.option = option;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getData() {
        return data;
    }

    public String getOption() {
        return option;
    }

    public String getId() {
        return id;
    }

}
