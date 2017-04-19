/** Add_informationTest
 *
 * JUNIT-test for inf.
 *
 * Created by Jørgen on 23.03.2017.
 * Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;


import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowDialog;

import java.util.ArrayList;
import java.util.Arrays;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class Add_informationTest {

    private Add_information inf;

    @Before
    public void setUp() throws Exception {
        inf = Robolectric.setupActivity(Add_information.class);
    }

    @Test
    public void constructor_test() throws Exception{
        assertEquals(true, inf.getCalculatedGrade() == 0);
        assertEquals(true, inf.getGender() ==  '\u0000');
        assertEquals(true, inf.getR2Grade() == 0);
        assertEquals(true, inf.getExtraPoints() == 0);
        assertEquals(true, inf.getExtraEducationArray().isEmpty());
        assertEquals(true, inf.getCoursesArray().isEmpty());
    }

    @Test
    public void testCalculation() throws Exception{

        ArrayList<String> c = new ArrayList<>(Arrays.asList("Kjemi 1"));
        inf.setYear(2002);
        inf.setCourses_array(c);

        assertEquals(true, inf.getCalculatedGrade() == 0.5);
        c.add("Matematikk R2");
        inf.setCourses_array(c);
        assertEquals(true, inf.getCalculatedGrade() == 1.5);

    }

    @Test
    public void agePoints_test() throws Exception {
        assertEquals(true, inf.agePoints(1985) == 8);
        assertEquals(true, inf.agePoints(1994) == 8);
        assertEquals(true, inf.agePoints(1995) == 6);
        assertEquals(true, inf.agePoints(1996) == 4);
        assertEquals(true, inf.agePoints(1997) == 2);
        assertEquals(true, inf.agePoints(1998) == 0);
        assertEquals(true, inf.agePoints(2016) == 0);
    }

    @Test
    public void addAvgGradeAlertNotNullTest() throws Exception {
        inf.findViewById(R.id.averageBtn).performClick();

        assertNotNull(ShadowDialog.getLatestDialog());
    }

    @Test
    public void addCoursesNotNullTest() throws Exception {
        inf.findViewById(R.id.dropdownCourses).performClick();

        assertNotNull(ShadowDialog.getLatestDialog());
    }

    @Test
    public void addExtraPointsNotNullTest() throws Exception {
        inf.findViewById(R.id.dropdownExtrapoints).performClick();

        assertNotNull(ShadowDialog.getLatestDialog());
    }

    @Test
    public void submitNoChangeTest () throws Exception {
        Add_information inf2 = inf;
        inf.findViewById(R.id.submit).performClick();
        Assert.assertEquals(inf2, inf);
    }

    @After
    public void tearDown() throws Exception {
        inf = null;
    }
}
