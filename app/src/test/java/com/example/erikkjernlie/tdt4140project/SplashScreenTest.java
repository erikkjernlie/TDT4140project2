package com.example.erikkjernlie.tdt4140project;

import android.content.Intent;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowLooper;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by erikkjernlie on 01/04/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SplashScreenTest {

    private SplashScreen ss;

    @Before
    public void setUp() throws Exception {
        ss = Robolectric.setupActivity(SplashScreen.class);

    }

   /* @Test
    public void testOnCreate() throws Exception {
        assertEquals(true, ss != null);
    }*/

    @Test
    public void testSplashScreen() throws Exception {
        //ActivityController<SplashScreen> controller = Robolectric.buildActivity(SplashScreen.class).create().start();
        ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

        //SplashScreen splashScreenActivity = controller.get();
        Intent expectedIntent = new Intent(ss, Sign_in.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(ss);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));

    }


    /*@After
    public void tearDown() throws Exception {
        ss = null;
    }*/
}
