package com.example.erikkjernlie.tdt4140project;

/**
 * Created by Jørgen on 23.03.2017.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.regex.Pattern;
import static org.junit.Assert.*;


public class Add_informationTest {

    private Add_information add_information;

    @Before
    public void setUp() throws Exception {
        add_information = new Add_information();
    }

    @Test
    public void constructor_test() throws Exception{
        assertEquals(true, add_information.getCalculatedGrade() == 0);
        assertEquals(true, add_information.getGender() ==  '\u0000');
        assertEquals(true, add_information.getR2Grade() == 0);
        assertEquals(true, add_information.getExtraPoints() == 0);
    }

    @Test
    public void agePoints_test() throws Exception {
        assertEquals(true, add_information.agePoints(1985) == 8);
        assertEquals(true, add_information.agePoints(1994) == 8);
        assertEquals(true, add_information.agePoints(1995) == 6);
        assertEquals(true, add_information.agePoints(1996) == 4);
        assertEquals(true, add_information.agePoints(1997) == 2);
        assertEquals(true, add_information.agePoints(1998) == 0);
        assertEquals(true, add_information.agePoints(2016) == 0);
    }

    @After
    public void tearDown() throws Exception {
        add_information = null;
    }
}
