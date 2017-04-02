/** InitialTest
 *
 * Initialtesting.
 *
 * Created by herman on 17.03.2017.
 * Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MenuTest extends ActivityInstrumentationTestCase2<Menu> {
    Activity activity;
    Menu menu;

    public MenuTest() {
        super(Menu.class);
    }


    @Before
    public void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
    }

    @Test
    public void testLogoIsDisplayed() {

        menu = new Menu();
        menu.initButtons();
        assertTrue(activity.findViewById(R.id.register).isClickable());
        assertTrue(activity.findViewById(R.id.explore).isClickable());
        assertTrue(activity.findViewById(R.id.aboutUs).isClickable());
        assertTrue(activity.findViewById(R.id.aboutUnibot).isClickable());
        assertTrue(activity.findViewById(R.id.signOut).isClickable());
    }

    @After
    public void tearDown(){
        activity = null;
    }

}