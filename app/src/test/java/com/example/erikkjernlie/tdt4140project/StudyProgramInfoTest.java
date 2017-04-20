/** StudyProgramInfoTest
 *
 * JUNIT-test for StudyProgramInfo.
 *
 * Created by erikkjernlie on 01/04/17.
 * Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class StudyProgramInfoTest {

    private StudyProgramInfo spi;

    @Before
    public void setUp() throws Exception {
        spi = new StudyProgramInfo();
    }




    @Test
    public void testStudyProgramInfo() throws Exception {
        ArrayList<String> spec = new ArrayList<>(Arrays.asList("programming", "data"));
        spi = new StudyProgramInfo(5.0, 5.1, true,spec, spec, "info","studyEnvironment", "studentUnion", spec);
        assertEquals(true, spi.getStudentUnion().equals("studentUnion"));
        assertEquals(true, spi.getInfo().equals("info"));
        assertTrue(spi.getStudentUnion().equals("studentUnion"));
        assertTrue(spi.getStudyEnvironment().equals("studyEnvironment"));
        assertTrue(spi.getInfo().equals("info"));
        assertTrue(spi.getCommonWorkFields().equals(spec));
        assertEquals(spi.toString(), "StudyProgramInfo{firstTimeGrade=5.0, ordinaryGrade=5.1, girlPoints=true, keywords=[programming, data], commonWorkFields=[programming, data], info='info', studyEnvironment='studyEnvironment', studentUnion='studentUnion', courses=[programming, data]}");
        spi = new StudyProgramInfo();

    }



    @Test
    public void testGetInfo() throws Exception {
        spi.setInfo("info");
        assertTrue(spi.getInfo().equals("info"));
        assertFalse("hello".equals(spi.getInfo()));
        spi.setInfo("nfo");
        assertFalse(spi.getInfo().equals("hello"));
        assertTrue("nfo".equals(spi.getInfo()));
    }

    @Test
    public void testGetGrade() throws Exception {
        spi.setFirstTimeGrade(5.0);
        assertTrue(5.0 == spi.getFirstTimeGrade());
        assertFalse(4.9 == spi.getFirstTimeGrade());
        spi.setFirstTimeGrade(4.9);
        assertFalse(5.0 == spi.getFirstTimeGrade());
        assertTrue(4.9 == spi.getFirstTimeGrade());
    }


    @Test
    public void testGetStudentUnion() throws Exception {
        spi.setStudentUnion("Hybrida");
        assertTrue(spi.getStudentUnion().equals("Hybrida"));
        assertFalse(spi.getStudentUnion().equals("Hybrid"));
        spi.setStudentUnion("Abakus");
        assertFalse(spi.getStudentUnion().equals("Hybrida"));
        assertTrue(spi.getStudentUnion().equals("Abakus"));
    }
    @Test
    public void testCommonWorkFields() throws Exception {
        ArrayList<String> spec = new ArrayList<>(Arrays.asList("programming", "data"));
        spi.setCommonWorkFields(spec);
        assertTrue(spi.getCommonWorkFields().equals(spec));
        assertTrue(spi.getCommonWorkFields().contains("programming"));
        assertFalse(spi.getCommonWorkFields().contains("program"));
    }

    @Test
    public void testGetCourses() throws Exception {
        ArrayList<String> spec = new ArrayList<>(Arrays.asList("programming", "data"));
        spi.setCourses(spec);
        assertTrue(spi.getCourses().equals(spec));
        assertTrue(spi.getCourses().contains("programming"));
        assertFalse(spi.getCourses().contains("program"));
    }

    @Test
    public void testGetKeywords() throws Exception {
        ArrayList<String> spec = new ArrayList<>(Arrays.asList("programming", "data"));
        assertNull(spi.getKeywords());
        spi.setKeywords(spec);
        assertTrue(spi.getKeywords().equals(spec));
        assertTrue(spi.getKeywords().contains("programming"));
        assertFalse(spi.getKeywords().contains("program"));
    }
    @Test
    public void testGetStudyEnvironment() throws Exception {
        spi.setStudyEnvironment("awesome");
        assertEquals(true, "awesome".equals(spi.getStudyEnvironment()));
        assertEquals(false, "!awesome".equals(spi.getStudyEnvironment()));
        spi.setStudyEnvironment("!awesome");
        assertEquals(false, "awesome".equals(spi.getStudyEnvironment()));
        assertEquals(true, "!awesome".equals(spi.getStudyEnvironment()));

    }

    @Test
    public void testIsGirlPoints() throws Exception {
        spi.setGirlPoints(true);
        assertTrue(spi.isGirlPoints());
        spi.setGirlPoints(false);
        assertFalse(spi.isGirlPoints());
    }



    @After
    public void tearDown() throws Exception {
        spi = null;
    }
}
