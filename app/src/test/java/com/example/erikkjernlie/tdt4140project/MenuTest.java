package com.example.erikkjernlie.tdt4140project;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Created by erikkjernlie on 01/04/17.
 */


public class MenuTest {
    private Menu ss;
    private UserInfo ui;

    @Before
    public void setUp() throws Exception {
        ss = new Menu();
        ui = new UserInfo();
    }

    @After
    public void tearDown() throws Exception {
        ss = null;
    }
}
