package com.example.erikkjernlie.tdt4140project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by Jørgen on 19.04.2017.
 */

public class InterviewTest {

    Interview intView;

    @Test
    public void stateFailTest() throws Exception {
        assertNotNull(intView = new Interview());
    }

    @After
    public void tearDown() throws Exception {
        intView = null;
    }

}
