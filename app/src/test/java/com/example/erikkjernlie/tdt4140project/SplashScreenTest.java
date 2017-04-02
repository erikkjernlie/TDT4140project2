package com.example.erikkjernlie.tdt4140project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by erikkjernlie on 01/04/17.
 */


public class SplashScreenTest {
    private SplashScreen ss;

    @Before
    public void setUp(){
        ss = new SplashScreen();
    }

    @Test
    public void testOnCreate(){

        assertEquals(true, ss != null);

    }


    @After
    public void tearDown(){
        ss = null;
    }
}
