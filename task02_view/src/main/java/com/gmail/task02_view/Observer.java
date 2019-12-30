package com.gmail.task02_view;

import java.util.ArrayList;

public class Observer {

    interface Listener {
        void onContactChange(Contact contact);

    }

    private ArrayList<Listener> listeners = new ArrayList<Listener>();

    private static Observer instance;

    private Observer() {

    }

    public static Observer getInstance() {
        if (instance == null) {
            instance = new Observer();
        }
        return instance;
    }

    public void notifyContactChanged(final Contact contact) {
        if (!listeners.isEmpty()) {
            for (Listener listener : listeners) {
                listener.onContactChange(contact);
            }
        }
    }

    public void subscribe(Listener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void unsubscribe(Listener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
    }
}
