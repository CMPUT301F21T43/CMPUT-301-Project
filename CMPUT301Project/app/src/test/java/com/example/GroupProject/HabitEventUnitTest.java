package com.example.GroupProject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

/**
 * Unit testing for HabitEvent class.
 *
 * @author martyrudolf
 */
public class HabitEventUnitTest {
    public HabitEvent testEvent;

    @Before
    public void setUpHabitEvent() {
        testEvent = new HabitEvent("Title", "Comment", "photo");
    }

    @Test
    public void testGetTitle() {
        assertEquals(testEvent.getTitle(), "Title");
    }


    @Test
    public void testSetTitle() throws NoSuchFieldException, IllegalAccessException {
        testEvent.setTitle("biz");
        final Field field = testEvent.getClass().getDeclaredField("title");
        field.setAccessible(true);
        assertEquals(field.get(testEvent), "biz");
    }

    @Test
    public void testGetComment() { assertEquals(testEvent.getComment(), "Comment"); }

    @Test
    public void testSetComment() throws NoSuchFieldException, IllegalAccessException {
        testEvent.setComment("baz");
        final Field field = testEvent.getClass().getDeclaredField("comment");
        field.setAccessible(true);
        assertEquals(field.get(testEvent), "baz");
    }

    @Test
    public void testGetPhotoID() { assertEquals(testEvent.getPhotoID(), "photo"); }

    @Test
    public void testSetPhotoID() throws NoSuchFieldException, IllegalAccessException {
        testEvent.setPhotoID("boz");
        final Field field = testEvent.getClass().getDeclaredField("photoID");
        field.setAccessible(true);
        assertEquals(field.get(testEvent), "boz");
    }
}
