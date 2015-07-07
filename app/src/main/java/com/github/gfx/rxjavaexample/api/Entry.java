package com.github.gfx.rxjavaexample.api;

public class Entry {

    public final String id;

    public final String title;

    public final String body;

    public Entry(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
