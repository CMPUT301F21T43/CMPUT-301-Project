/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import java.io.Serializable;

/**
 * Habit class
 * Class of habit event objects
 */
public class HabitEvent implements Serializable {
    private String title;
    private String comment;
    private String photoID;
    private double latitude, longitude;
    private boolean isDone;

    /**
     * Generic constructor for HabitEvent.
     * @param title name of the habit event, needs to be unique for a particular habit.
     * @param comment optional comment
     * @param photoID optional photo (references a link stored in a realtime database)
     */
    public HabitEvent(String title, String comment, String photoID) {
        this.title = title;
        this.comment = comment;
        this.photoID = photoID;
        this.latitude = this.longitude = 1;    // default location
        this.isDone = false;
    }

    /**
     * Generic constructor for HabitEvent.
     * @param title name of the habit event, needs to be unique for a particular habit.
     * @param comment optional comment
     * @param photoID optional photo (references a link stored in a realtime database)
     */
    public HabitEvent(String title, String comment, String photoID, double latitude, double longitude, boolean isDone) {
        this.title = title;
        this.comment = comment;
        this.photoID = photoID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isDone = isDone;
    }

    /**
     * Function to return habit event title
     * @return title
     *      Returns title as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set habit event title
     * @param title
     *      title of event as a string
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Function to return an event comment
     * @return comment
     *      Returns comment as a String
     */
    public String getComment() {
        return comment;
    }

    /**
     * Function to set event comment
     * @param comment
     *      comment as a String
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Function to return photoID
     * @return photoID
     *      Returns photoID as a String
     */
    public String getPhotoID() {
        return photoID;
    }

    /**
     * Function to set photoID
     * @param photoID
     *      photoID as a String
     */
    public void setPhotoID(String photoID) {
        this.photoID = photoID;
    }

    /**
     * Function to return latitude
     * @return latitude
     *      Returns latitude as a double
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Function to set latitude
     * @param latitude
     *      latitude as a double
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Function to return longitude
     * @return longitude
     *      Returns longitude as a double
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Function to set longitude
     * @param longitude
     *      longitude as a double
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Function to return description of the event location
     * @return
     *      Returns a String description of the location
     */
    public String getLocationString() {
        if (latitude == 1 && longitude == 1) {  // default location
            return "Optional Location";
        }
        return String.format("LatLon: (%.2f, %.2f)", latitude, longitude);
    }

    /**
     * Get whether HabitEvent is done or not.
     * @return isDone
     *      isDone as Boolean
     */
    public boolean getDone() {
        return isDone;
    }

    /**
     * Set Habit Event as done or not.
     * @param isDone
     *      isDone is Boolean
     */
    public void setPublic(Boolean isDone) {
        this.isDone = isDone;
    }
}