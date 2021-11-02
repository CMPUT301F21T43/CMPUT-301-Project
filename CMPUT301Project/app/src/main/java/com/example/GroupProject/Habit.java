package com.example.GroupProject;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Habit class
 * Class of habit objects with a title, date, and reason
 * @Author Marcus Bengert, Martin Rudolf
 */

public class Habit implements Serializable {

    private String title;
    private String reason;
    private Date dateToStart;
    private Map<String, Boolean> activeDays;
    private Boolean isPublic;

    Habit(String title, String reason, Date dateToStart, Map<String, Boolean> activeDays, Boolean isPublic){
        this.title = title;
        this.reason = reason;
        this.dateToStart = dateToStart;
        this.activeDays = activeDays;
        this.isPublic = isPublic;
    }

    /**
     * Function to return habit title
     * @return title
     *      Returns title as a String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Function to set habit title
     * @param title
     *      title of habit as a string
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Returns dateToStart formatted as "YYYY-MM-DD" string
     * @return "YYYY-MM-DD"
     *      Returns the dateToStart formatted as "YYYY-MM-DD"
     */
    public String getDateToStartAsString() {
        Date date = dateToStart;
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonth());
        String day = String.valueOf(date.getDate());
        return year + "-" + month + "-" + day;
    }

    /**
     * Function to set habit date
     * @param dateToStart
     *      dateToStart started as a Date object
     */
    public void setDateToStart(Date dateToStart) {
        this.dateToStart = dateToStart;
    }

    /**
     * Get active days of week.
     * @return activeDays
     *      activeDays as a Map with K: day of week (String) and V: active (Boolean)
     */
    public Map<String, Boolean> getActiveDays() {
        return activeDays;
    }

    /**
     * Set active days of week.
     * @param activeDays
     *      activeDays is a Map with K: day of week (String) and V: active (Boolean)
     */
    public void setActiveDays(Map<String, Boolean> activeDays) {
        this.activeDays = activeDays;
    }

    /**
     * Get whether Habit is public or not.
     * @return isPublic
     *      isPublic as Boolean
     */
    public Boolean getPublic() {
        return isPublic;
    }

    /**
     * Set Habit as public or not.
     * @param isPublic
     *      isPublic is Boolean
     */
    public void setPublic(Boolean isPublic) {
        isPublic = isPublic;
    }
}
