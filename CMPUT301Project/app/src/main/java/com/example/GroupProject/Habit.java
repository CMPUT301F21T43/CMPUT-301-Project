package com.example.GroupProject;

import java.util.Date;
import java.util.Dictionary;

/**
 * Habit class
 * Class of habit objects with a name, date, and reason
 * @Author Marcus Bengert
 */

public class Habit {

    private String name;
    private String reason;
    private Date dateToStart;
    private String[] activeDays;

    Habit(String name, String reason, Date dateToStart, String[] activeDays){
        this.name = name;
        this.reason = reason;
        this.dateToStart = dateToStart;
        this.activeDays = activeDays;
    }

    /**
     * Function to return habit name
     * @return name
     *      Returns name as a String
     */
    public String getName() {
        return name;
    }

    /**
     * Function to set habit name
     * @param name
     *      name of habit as a string
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Function to return habit reason
     * @return reason
     *      Returns reason as a String
     */
    public String getReason() {
        return reason;
    }

    /**
     * Function to set habit reason
     * @param reason
     *      reason for habit as a string
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Function to return habit dateToStart
     * @return dateToStart
     *      Returns dateToStart as a Date object
     */
    public Date getDateToStart() {
        return dateToStart;
    }

    /**
     * Function to set habit date
     * @param dateToStart
     *      dateToStart started as a Date object
     */
    public void setDateToStart(Date dateToStart) {
        this.dateToStart = dateToStart;
    }
}
