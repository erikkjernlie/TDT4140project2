/** InitialTest
 *
 * Initialtesting.
 *
 * Created by herman on 17.03.2017.
 * Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

import android.app.Activity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class InitialTest extends ActivityInstrumentationTestCase2<Menu> {
    Activity activity;

    public InitialTest() {
        super(Menu.class);
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
        assertTrue(activity.findViewById(R.id.aboutUnibot).isClickable());
        assertTrue(activity.findViewById(R.id.aboutUs).isClickable());
        assertTrue(activity.findViewById(R.id.signOut).isClickable());
        assertTrue(activity.findViewById(R.id.cogwheel).isClickable());
        assertTrue(activity.findViewById(R.id.explore).isClickable());
        assertTrue(activity.findViewById(R.id.recommendation).isClickable());





    }

}