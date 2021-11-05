/*
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */

package com.example.GroupProject;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Unit tests for the User class.
 *
 * @author martyrudolf
 */
public class UserUnitTest {
    public User testUser;

    @Before
    public void setUpUser() {
        testUser = new User("Bob Dylan", "Robert", "Dylan");
    }

    @Test
    public void testGetUsername() {
        assertEquals(testUser.getUsername(), "Bob Dylan");
    }

    @Test
    public void testGetFirstName() {
        assertEquals(testUser.getFirstName(), "Robert");
    }

    @Test
    public void testGetLastName() {
        assertEquals(testUser.getLastName(), "Dylan");
    }

    @Test
    public void testSetUserName() throws NoSuchFieldException, IllegalAccessException{
        testUser.setUsername("Bobby Dylan");
        final Field field = testUser.getClass().getDeclaredField("username");
        field.setAccessible(true);
        assertEquals(field.get(testUser), "Bobby Dylan");
    }

    @Test
    public void testSetFirstName() throws NoSuchFieldException, IllegalAccessException{
        testUser.setFirstName("Bobby");
        final Field field = testUser.getClass().getDeclaredField("firstName");
        field.setAccessible(true);
        assertEquals(field.get(testUser), "Bobby");
    }
    @Test
    public void testSetLastName() throws NoSuchFieldException, IllegalAccessException{
        testUser.setLastName("Dyl");
        final Field field = testUser.getClass().getDeclaredField("lastName");
        field.setAccessible(true);
        assertEquals(field.get(testUser), "Dyl");
    }
}
