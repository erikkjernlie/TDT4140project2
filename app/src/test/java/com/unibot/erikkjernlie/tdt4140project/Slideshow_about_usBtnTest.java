package com.unibot.erikkjernlie.tdt4140project;

import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Jørgen on 05.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class Slideshow_about_usBtnTest {

    Slideshow_about_us sau;

    @Before
    public void setUp() throws Exception {
        sau = Robolectric.setupActivity(Slideshow_about_us.class);
    }

    @Test
    public void stateNotNullTest() throws Exception {
        assertNotNull(sau);
    }

    @Test
    public void switchToMenuBtnTest() throws Exception {
        sau.findViewById(R.id.btn_back).performClick();

        Intent expectedIntent = new Intent(sau, Menu.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(sau);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @After
    public void tearDown () throws Exception {
        sau = null;
    }
}
