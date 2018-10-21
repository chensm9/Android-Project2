package com.example.admin.healthyfoodlistview;

public class MessageEvent {
    private Collection collection;

    public MessageEvent(Collection c) {
        collection = c;
    }

    public Collection getCollection() {
        return collection;
    }
}
