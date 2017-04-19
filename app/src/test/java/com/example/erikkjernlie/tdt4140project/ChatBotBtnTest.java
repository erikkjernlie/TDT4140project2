package com.example.erikkjernlie.tdt4140project;

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

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Jørgen on 17.04.2017.
 */


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class ChatBotBtnTest {

    ChatBot chat;

    @Before
    public void setUp() throws Exception {
        chat = Robolectric.setupActivity(ChatBot.class);
    }

    @Test
    public void stateNotNullTest() throws Exception {
        assertNotNull(chat);
    }

    @Test
    public void backBtnTest() throws Exception {
        chat.findViewById(R.id.back).performClick();
        Intent expectedIntent = new Intent(chat, Menu.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(chat);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void helpBtnNotNullTest() throws Exception {
        chat.findViewById(R.id.help).performClick();

        Dialog alert = ShadowDialog.getLatestDialog();

        assertNotNull(alert);

    }

    @After
    public void tearDown() throws Exception {
        chat = null;
    }

}
