package com.example.GroupProject;

/**
 * User class
 * Class of a user, containing username, first name and last name
 * @Author Kyle Bricker
 *
 */
public class User {
    String username;
    String firstName;
    String lastName;

    User(String username, String firstName, String lastName) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Get username
     * @return username
     *      returns users username as a String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Get first name
     * @return firstName
     *      returns users first name as a String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get last name
     * @return lastName
     *      returns users last name as a string
     */
    public String getLastName() {
        return lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
