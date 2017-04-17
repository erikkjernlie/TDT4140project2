package com.example.erikkjernlie.tdt4140project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowDialog;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Jørgen on 05.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class Register_UserBtnTest {

    Register_user reg;

    @Before
    public void setUp() throws Exception {
        reg = Robolectric.setupActivity(Register_user.class);
    }

    @Test
    public void stateNotNullTest() throws Exception {
        assertNotNull(reg);
    }

    @Test
    public void registerUserNothingHappensTest() throws Exception {
        Register_user reg2 = reg;
        reg.findViewById(R.id.registerBtn).performClick();
        assertEquals(reg2, reg);
    }

    @Test
    public void switchToRegisterBtnTest() throws Exception {
        reg.findViewById(R.id.switchRegisterToLogin).performClick();

        Intent expectedIntent = new Intent(reg, Sign_in.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(reg);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @After
    public void tearDown() throws Exception {
        reg = null;
    }

}
