package com.example.GroupProject;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HabitEventsUnitTest {
    public HabitEvents testEvents;

    @Before
    public void setUpEvent() {
        testEvents = new HabitEvents("Hello","ok");
    }

    @Test
    public void testGetTitle() {
        assertEquals(testEvents.getTitle(), "Hello");
    }

    @Test
    public void testSetTitle() throws NoSuchFieldException, IllegalAccessException {
        testEvents.setTitle("Bye");
        final Field field = testEvents.getClass().getDeclaredField("title");
        field.setAccessible(true);
        assertEquals(field.get(testEvents), "Bye");
    }

    @Test
    public void testGetComment(){

        assertEquals(testEvents.getComment(), "ok");
    }

    @Test
    public void testSetComment() throws NoSuchFieldException, IllegalAccessException {
        testEvents.setComment("no");
        final Field field = testEvents.getClass().getDeclaredField("comment");
        field.setAccessible(true);
        assertEquals(field.get(testEvents), "no");
    }
}



