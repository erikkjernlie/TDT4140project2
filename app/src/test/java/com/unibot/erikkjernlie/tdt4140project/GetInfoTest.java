package com.unibot.erikkjernlie.tdt4140project;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by erikkjernlie on 02/04/17.
 */

public class GetInfoTest {


    private GetInfo gi;
    private String mting = "The ‘Engineering and ICT' programme is a fairly new and unique advanced engineering programme in Norway. Norwegian industry increasingly demands engineers with both ICT expertise and an advanced engineering education. There is also an increasing need for candidates who understand and are able to futher develop the existing computer tools used by engineers themselves. Development of tomorrow's computer tools requires hybrid engineers — that is — engineers with both the right engineering background for the field, as well as the requisite programming abilities. Robot Challenge: Lagene bygger og programmerer avanserte roboter. Foto: Johan Røed (IVT) Why choose the Engineering and ICT programme? You would like to work with technology, but you don't know what to specialize in? You options are numerous even after choosing 'Engineering and ICT'! You are interested in scientific subjects and computing? (Admission to the degree programme does not require computer skills, we will teach you everything from scratch.) Industry needs engineers with a combination of computer and traditional engineering qualifications. You would like a fascinating role as a bridge builder between people with backgrounds from computing and engineering. Specialization alternatives The first two years include ICT, mathematics, physics and mechanics. After the first two years students may choose between six specialization fields. In addition to the technological specialization students also aquire special qualifications within ICT for which there is a high demand.";

    @Before
    public void setUp() throws Exception {
        gi = new GetInfo();
    }

    @Test
    public void TestGetBasicInformation() throws Exception {
        assertEquals(mting,gi.getBasicInformation("mting"));
    }


    @After
    public void tearDown() throws Exception {
        gi = null;
    }


}

