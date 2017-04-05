package com.example.erikkjernlie.tdt4140project;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Jørgen on 05.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class Sign_inBtnTest {

    Sign_in in;

    @Before
    public void setUp() throws Exception {
        in = Robolectric.setupActivity(Sign_in.class);
    }

    @Test
    public void aboutUnibotBtnTest() throws Exception {
        in.findViewById(R.id.switchLoginToRegister).performClick();

        Intent expectedIntent = new Intent(in, Register_user.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(in);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

}
