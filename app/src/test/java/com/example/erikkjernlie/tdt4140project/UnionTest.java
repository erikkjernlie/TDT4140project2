/*  UnionTest
 *
 *  Test for the Union-activity
 *
 *  Created by Erik Kjernlie on 02/04/17.
 *  Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class UnionTest {

    private Union u;


    @Before
    public void setUp(){
        u = new Union();

    }

    @Test
    public void testUnion(){
        assertEquals(0,u.getMembers());
        u = new Union("Hybrida", "info", 50);
        u.toString();
        assertEquals("Union{members=50, info='info', name='Hybrida'}", u.toString());
        assertEquals(50, u.getMembers());
        u = new Union();
    }


    @Test
    public void testGetName(){
        u.setName("Hybrida");
        assertTrue(u.getName().equals("Hybrida"));
        assertFalse(u.getName().equals("H"));
    }

    @Test
    public void testGetInfo(){
       u.setInfo("info");
        assertTrue(u.getInfo().equals("info"));
        assertFalse(u.getInfo().equals("ufo"));
    }

    @Test
    public void testGetMembers(){
        u.setMembers(50);
        assertEquals(50, u.getMembers());
        assertNotEquals(49, u.getMembers());
    }

    public void tearDown(){
        u = null;
    }

}

