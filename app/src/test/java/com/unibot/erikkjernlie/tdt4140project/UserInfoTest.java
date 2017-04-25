/** StudyProgramInfoTest
 *
 * JUNIT-test for StudyProgramInfo.
 *
 * Created by erikkjernlie on 02/04/17.
 * Copyright © uniBOT
 */

package com.unibot.erikkjernlie.tdt4140project;


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

        u = new UserInfo(1995, 5.6,0,0,0,0,0,0,c,c,'F',6,c);
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

    @Test
    public void setNumber1Grade(){
        u.setNumber1grade(1);
        assertEquals(1, u.getNumber1grade());
        assertNotEquals(0, u.getNumber1grade());
    }

    @Test
    public void setNumber2Grade(){
        u.setNumber2grade(1);
        assertEquals(1, u.getNumber2grade());
        assertNotEquals(0, u.getNumber2grade());
    }

    @Test
    public void setNumber3Grade(){
        u.setNumber3grade(1);
        assertEquals(1, u.getNumber3grade());
        assertNotEquals(0, u.getNumber3grade());
    }

    @Test
    public void setNumber4Grade(){
        u.setNumber4grade(1);
        assertEquals(1, u.getNumber4grade());
        assertNotEquals(0, u.getNumber4grade());
    }

    @Test
    public void setNumber5Grade(){
        u.setNumber5grade(1);
        assertEquals(1, u.getNumber5grade());
        assertNotEquals(0, u.getNumber5grade());
    }

    @Test
    public void setNumber6Grade(){
        u.setNumber6grade(1);
        assertEquals(1, u.getNumber6grade());
        assertNotEquals(0, u.getNumber6grade());
    }

    @After
    public void tearDown() throws Exception {
        u = null;
    }

}
