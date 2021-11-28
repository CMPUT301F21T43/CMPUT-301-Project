/*
 * HabitUnitTest
 *
 * Version 1.0
 *
 * November 3, 2021
 *
 * Copyright (c) 2021-2022. Group 43 CMPUT301 F2021
 * All rights reserved.
 */


package com.example.GroupProject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit testing for Habit class.
 *
 * @author martyrudolf
 */
public class HabitUnitTest {
    public Habit testHabit;

    @Before
    public void setUpHabit() {
        Map<String, Boolean> testActiveDays = new HashMap<>();
        testActiveDays.put("Monday", true);
        testActiveDays.put("Tuesday", false);
        testHabit = new Habit("Foo", "Bar", new Date(2021, 05, 05), testActiveDays, false);
    }

    @Test
    public void testGetTitle() {
        assertEquals(testHabit.getTitle(), "Foo");
    }


    @Test
    public void testSetTitle() throws NoSuchFieldException, IllegalAccessException {
        testHabit.setTitle("biz");
        final Field field = testHabit.getClass().getDeclaredField("title");
        field.setAccessible(true);
        assertEquals(field.get(testHabit), "biz");
    }

    @Test
    public void testGetReason(){
        assertEquals(testHabit.getReason(), "Bar");
    }

    @Test
    public void testSetReason() throws NoSuchFieldException, IllegalAccessException {
        testHabit.setReason("baz");
        final Field field = testHabit.getClass().getDeclaredField("reason");
        field.setAccessible(true);
        assertEquals(field.get(testHabit), "baz");
    }

    @Test
    public void testGetDateToStart() {
        assertEquals(testHabit.getDateToStart(), new Date(2021, 05, 05));
    }


    @Test
    public void testSetDateToStart() throws NoSuchFieldException, IllegalAccessException{
        testHabit.setDateToStart(new Date(2021, 06, 06));
        final Field field = testHabit.getClass().getDeclaredField("dateToStart");
        field.setAccessible(true);
        assertEquals(field.get(testHabit), new Date(2021, 06, 06));
    }

    @Test
    public void testGetActiveDays() {
        Map<String, Boolean> testMap = new HashMap<>();
        testMap.put("Monday", true);
        testMap.put("Tuesday", false);
        assertEquals(testHabit.getActiveDays(), testMap);
    }

    @Test
    public void testSetActiveDays() throws NoSuchFieldException, IllegalAccessException {
        Map<String, Boolean> testMap = new HashMap<>();
        testMap.put("Monday", false);
        testMap.put("Tuesday", true);
        testHabit.setActiveDays(testMap);
        final Field field = testHabit.getClass().getDeclaredField("activeDays");
        field.setAccessible(true);
        assertEquals(field.get(testHabit), testMap);
    }

    @Test
    public void testIsDayActive() {
        assertTrue(testHabit.isDayActive(Calendar.MONDAY));
        assertFalse(testHabit.isDayActive(Calendar.TUESDAY));
    }

    @Test
    public void testGetPublic() {
        assertFalse(testHabit.getPublic());
    }

    @Test
    public void testSetPublic() throws NoSuchFieldException, IllegalAccessException {
        testHabit.setPublic(true);
        final Field field = testHabit.getClass().getDeclaredField("isPublic");
        field.setAccessible(true);
        assertEquals(field.get(testHabit), true);
    }

}
