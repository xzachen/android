package com.toddway.sandbox;

public class Thing {
    public int ID;
    public String content;
    public Thing() {

    }

    public Thing(int ID, String content) {
        this.ID = ID;
        this.content = content;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return getContent();
    }
}
