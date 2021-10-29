package com.example.GroupProject;

public class HabitEvents {

    private String title;
    private boolean done = true;

    public HabitEvents(String title, boolean done) {
        this.title = title;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
