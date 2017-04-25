package com.unibot.erikkjernlie.tdt4140project;

import android.app.Dialog;
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
import org.robolectric.shadows.ShadowDialog;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by erikkjernlie on 01/04/17.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MenuBtnTest {

    Menu menu;

    @Before
    public void setUp() throws Exception {
        menu = Robolectric.setupActivity(Menu.class);

    }

    @Test
    public void initiateTest() throws Exception {
        assertTrue(menu != null);
        menu.initButtons();
    }

    @Test
    public void switchToChatBotBtnTest() throws Exception {
        menu.findViewById(R.id.explore).performClick();

        Intent expectedIntent = new Intent(menu, ChatBot.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(menu);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void switchToRecommendationBtnTest() throws Exception {
        menu.findViewById(R.id.recommendation).performClick();

        Intent expectedIntent = new Intent(menu, Recommendation.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(menu);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void switchToAboutUnibotBtnTest() throws Exception {
        menu.findViewById(R.id.aboutUnibot).performClick();

        Intent expectedIntent = new Intent(menu, Slideshow_about_unibot.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(menu);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void switchToAboutUsBtnTest() throws Exception {
        menu.findViewById(R.id.aboutUs).performClick();

        Intent expectedIntent = new Intent(menu, Slideshow_about_us.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(menu);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test(expected = IllegalStateException.class)
    public void SignOutBtnFailTest() throws Exception {
        menu.findViewById(R.id.signOut).performClick();

        Intent expectedIntent = new Intent(menu, Sign_in.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(menu);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void alertSettingsTest() throws Exception {
        menu.findViewById(R.id.cogwheel).performClick();

        Dialog alert = ShadowDialog.getLatestDialog();

        assertNotNull(alert);
    }

    @Test(expected = NullPointerException.class)
    public void retrievePasswordFailTest() throws Exception {
        menu.alertRetrievePassword();
        menu.findViewById(R.id.confirm_email).performClick();
    }

    @Test(expected = IllegalStateException.class)
    public void onCreateFailTest() throws Exception {
        menu.onCreate(null);
    }

    @Test
    public void initButtonsTest() throws Exception {
        Menu menu1 = menu;
        menu.initButtons();
        assertEquals(menu1, menu);
    }

    @After
    public void tearDown() throws Exception {
        menu = null;
    }
}
