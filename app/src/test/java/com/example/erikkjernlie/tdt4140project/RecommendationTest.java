package com.example.erikkjernlie.tdt4140project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Jørgen on 17.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class RecommendationTest {

    Recommendation rec;

    @Before
    public void setUp() throws Exception {
        rec = Robolectric.setupActivity(Recommendation.class);
    }

    @Test
    public void stateNotNullTest() throws Exception {
        assertNotNull(rec);
    }

    @After
    public void tearDown() throws Exception {
        rec = null;
    }

}
