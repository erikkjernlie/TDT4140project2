/** StudyProgramInfoTest
 *
 * JUNIT-test for StudyProgramInfo.
 *
 * Created by erikkjernlie on 02/04/17.
 * Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;


import com.thoughtworks.xstream.mapper.Mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class UserInfoTest  {

    private UserInfo u;


    @Before
    public void setUp() throws Exception {
        u = new UserInfo();
    }

    @Test
    public void stateNotNullTest() throws Exception {
        assertNotNull(u);
    }

    @Test
    public void testUserInfo() throws Exception {
        assertEquals(0,u.getBirthYear());
        ArrayList<String> c = new ArrayList<String>();
        c.add("IKT");

        u = new UserInfo(1995, 5.6, 0, 0, 0, 0, 0, 0, c, c,'F',6,c);
        System.out.println(u.toString());
        assertEquals("UserInfo{birthYear=1995, calculatedGrade=5.6, courses=[IKT], "+
                "extraEducation=[IKT], gender=F, interests=[IKT], R2Grade=6}", u.toString());
        assertEquals(1995, u.getBirthYear());
        assertNotEquals(5.5, u.getBirthYear());
        u = new UserInfo();
    }

    @Test
    public void testGetBirthYear() throws Exception {
        u.setBirthYear(1995);
        assertEquals(1995, u.getBirthYear());
        assertNotEquals(1996, u.getBirthYear());
    }

    @Test
    public void testGetCalculatedGrade() throws Exception {
        u.setCalculatedGrade(5.5);
        assertTrue(5.5 == u.getCalculatedGrade());
        assertFalse(5.6 == u.getCalculatedGrade());

    }

    @Test
    public void testGetCourses() throws Exception {
        ArrayList<String> c = new ArrayList<String>();
        c.add("IKT");
        u.setCourses(c);
        assertFalse(c.isEmpty());
        assertTrue(u.getCourses().equals(c));
    }

    @Test
    public void testGetExtraEducation() throws Exception {
        ArrayList<String> c = new ArrayList<String>();
        c.add("IKT");
        u.setExtraEducation(c);
        assertFalse(c.isEmpty());
        assertTrue(u.getExtraEducation().equals(c));
    }
    @Test
    public void testGetInterests() throws Exception {
        ArrayList<String> c = new ArrayList<String>();
        c.add("IKT");
        u.setInterests(c);
        assertFalse(c.isEmpty());
        assertTrue(u.getInterests().equals(c));
    }

    @Test
    public void getR2GradeTest() throws Exception {
        u.setR2Grade(6);
        assertEquals(6, u.getR2Grade());
        assertNotEquals(5, u.getR2Grade());
    }

    @Test
    public void getGenderTest() throws Exception {
        u.setGender('F');
        assertEquals('F', u.getGender());
        assertNotEquals('M', u.getGender());
    }

    @Test(expected = IllegalStateException.class)
    public void updateFirebaseFailTest() throws Exception {
        u.updateFirebase();
    }

    @After
    public void tearDown() throws Exception {
        u = null;
    }

}
