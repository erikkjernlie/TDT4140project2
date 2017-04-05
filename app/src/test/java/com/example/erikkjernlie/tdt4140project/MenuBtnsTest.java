package com.example.erikkjernlie.tdt4140project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Jørgen on 04.04.2017.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MenuBtnsTest {

    Menu menu;
    FirebaseAuth firebaseAuth;

    @Test
    public void aboutUnibotBtnTest() throws Exception {
        menu = Robolectric.setupActivity(Menu.class);
        menu.findViewById(R.id.aboutUnibot).performClick();

        Intent expectedIntent = new Intent(menu, Slideshow_about_unibot.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(menu);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }
    @After
    public void tearDown() throws Exception {
        menu = null;
        //firebaseAuth.signOut();
    }
}
