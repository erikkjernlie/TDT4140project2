package com.example.erikkjernlie.tdt4140project;

import android.app.Activity;
import android.content.Intent;
import android.support.test.espresso.ViewAssertion;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.InstrumentationRegistry;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.runners.JUnit4;

/**
 * Created by herman on 17.03.2017.
 */

@RunWith(AndroidJUnit4.class)
public class InitialTest extends ActivityInstrumentationTestCase2<Sign_in> {
    Activity activity;

    public InitialTest() {
        super(Sign_in.class);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        setActivityInitialTouchMode(true);
        activity = getActivity();
    }

    @Test
    public void testLogoIsDisplayed() {
        // When the phone_icon view is available,
        // check that it is displayed.
        onView(withId(R.id.logInBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.enterEmailAddress)).check(matches(isDisplayed()));
        onView(withId(R.id.aboutUnibot)).check(doesNotExist());
     //   onView(withId(R.id.btn_next)).check(doesNotExist());

    }

}