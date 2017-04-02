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


public class StudyProgramInfoTest {

    private StudyProgramInfo spi;

    private String info;
    private Double grade;
    private boolean isGirlPoints;
    private double girPercentage;
    private String studentUnion;
    private ArrayList<String> specializations;
    private String studyEnvironment;

    @Before
    public void setUp() throws Exception {
        spi = new StudyProgramInfo();
    }




    @Test
    public void testStudyProgramInfo(){
        ArrayList<String> spec = new ArrayList<>(Arrays.asList("programming", "data"));
        spi = new StudyProgramInfo("info", 0.0, true, 50, "studentUnion", spec, "studyEnvironment");
        assertEquals(true, spi.getStudentUnion().equals("studentUnion"));
        assertEquals(true, spi.getInfo().equals("info"));
        spi = new StudyProgramInfo();
    }



    @Test
    public void testGetInfo() throws Exception {
        spi.setInfo("info");
        assertEquals(true, spi.getInfo().equals("info"));
        assertEquals(false, "hello".equals(spi.getInfo()));
        spi.setInfo("nfo");
        assertEquals(false, spi.getInfo().equals("hello"));
        assertEquals(true, "nfo".equals(spi.getInfo()));
    }

    @Test
    public void testGetGrade() throws Exception {
        spi.setGrade(5.0);
        assertEquals(true, 5.0 == spi.getGrade());
        assertEquals(false, 4.9 == spi.getGrade());
        spi.setGrade(4.9);
        assertEquals(false, 5.0 == spi.getGrade());
        assertEquals(true, 4.9 == spi.getGrade());
    }

    @Test
    public void testGetGirPercentage() throws Exception {
        spi.setGirPercentage(49);
        assertEquals(true, 49 == spi.getGirPercentage());
        assertEquals(false, 50 == spi.getGirPercentage());
        spi.setGirPercentage(48);
        assertEquals(true, 48 == spi.getGirPercentage());
        assertEquals(false, 50 == spi.getGirPercentage());
    }
    @Test
    public void testGetStudentUnion() throws Exception {
        spi.setStudentUnion("Hybrida");
        assertEquals(true, spi.getStudentUnion().equals("Hybrida"));
        assertEquals(false, spi.getStudentUnion().equals("Hybrid"));
        spi.setStudentUnion("Abakus");
        assertEquals(false, spi.getStudentUnion().equals("Hybrida"));
        assertEquals(true, spi.getStudentUnion().equals("Abakus"));
    }
    @Test
    public void testGetSpecializations() throws Exception {
        ArrayList<String> spec = new ArrayList<>(Arrays.asList("programming", "data"));
        spi.setSpecializations(spec);
        assertEquals(true,spi.getSpecializations().equals(spec));
        assertEquals(true, spi.getSpecializations().contains("programming"));
        assertEquals(false, spi.getSpecializations().contains("program"));
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
    public void testIsGirlPoints(){
        spi.setGirlPoints(true);
        assertEquals(true, spi.isGirlPoints());
        spi.setGirlPoints(false);
        assertEquals(false, spi.isGirlPoints());
    }

    @After
    public void tearDown() throws Exception {
        spi = null;
    }
}
