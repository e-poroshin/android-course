package com.gmail.task02_view;

import java.util.ArrayList;

public class Observer {

    interface onContactActionListener {
        void addContact(Contact contact);
        void onContactChange(Contact contact);
        void removeContact(Contact contact);
    }

    private ArrayList<onContactActionListener> onContactActionListeners = new ArrayList<>();

    private static Observer instance;

    private Observer() {

    }

    public static Observer getInstance() {
        if (instance == null) {
            instance = new Observer();
        }
        return instance;
    }

    public void notifyAddContact(final Contact contact) {
        if (!onContactActionListeners.isEmpty()) {
            for (onContactActionListener onContactActionListener : onContactActionListeners) {
                onContactActionListener.addContact(contact);
            }
        }
    }

    public void notifyChangeContact(final Contact contact) {
        if (!onContactActionListeners.isEmpty()) {
            for (onContactActionListener onContactActionListener : onContactActionListeners) {
                onContactActionListener.onContactChange(contact);
            }
        }
    }

    public void notifyRemoveContact(final Contact contact) {
        if (!onContactActionListeners.isEmpty()) {
            for (onContactActionListener onContactActionListener : onContactActionListeners) {
                onContactActionListener.removeContact(contact);
            }
        }
    }

    public void subscribe(onContactActionListener onContactActionListener) {
        if (onContactActionListener != null) {
            onContactActionListeners.add(onContactActionListener);
        }
    }

    public void unsubscribe(onContactActionListener onContactActionListener) {
        if (onContactActionListener != null) {
            onContactActionListeners.remove(onContactActionListener);
        }
    }
}
