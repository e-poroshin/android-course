package com.example.mediaplayer;

import java.io.Serializable;

public class AudioFile implements Serializable {

    private String title;
    private int icon;
    private int path;

    public AudioFile(String title, int icon, int path) {
        this.title = title;
        this.icon = icon;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }
}
