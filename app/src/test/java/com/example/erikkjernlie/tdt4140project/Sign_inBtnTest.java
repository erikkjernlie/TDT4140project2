package com.example.erikkjernlie.tdt4140project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.thoughtworks.xstream.mapper.Mapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadow.api.Shadow;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowDialog;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by Jørgen on 05.04.2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class Sign_inBtnTest {

    Sign_in in;
    Sign_in in2;

    @Before
    public void setUp() throws Exception {
        in = Robolectric.setupActivity(Sign_in.class);
        in2 = new Sign_in();
    }

    @Test
    public void constructorTest() throws Exception {
        assertNotNull(in);
    }

    @Test(expected = NullPointerException.class)
    public void hideKeyBoardTest() throws Exception {
        in2.hideKeyboard(new View(null));
    }

    @Test
    public void setGetUserTest() throws Exception {
        UserInfo user = new UserInfo(1999, 54.3, 1,1,1,1,1,1, null, null, 'M', 5, null);
        in2.setUser(user);
        assertTrue(UserInfo.userInfo.equals(user));
        assertTrue(in2.getUser().equals(user));
    }

    @Test
    public void addUnionsTest() throws Exception {
        Union union = new Union("TP", "TP er best", 100);
        in2.addUnions(union);
        assertTrue(Union.unions.containsKey(union.getName()));
    }

    @Test
    public void addStudyProgramsTest() throws Exception {
        StudyProgramInfo study = new StudyProgramInfo(54.0, 54.5, false, null, null,
                "Very nice study", "Very good", "Hybrida", null);
        in2.addStudyPrograms("IKT", study);
        assertTrue(StudyProgramInfo.studyPrograms.containsKey("IKT"));
        assertTrue(UserInfo.userInfo.studyPrograms.containsKey("IKT"));
    }

    @Test(expected = NullPointerException.class)
    public void logInUserFailTest() throws Exception {
        in2.logInUser();
    }

    @Test(expected = NullPointerException.class)
    public void getUserInfoDatabaseFailTest() throws Exception {
        in2.getUserInfoDatabase();
    }

    @Test
    public void getStudyInfoDatabaseTest() throws Exception {
        in2.getStudyInfoDatabase();
        assertFalse(StudyProgramInfo.studyPrograms.isEmpty());
    }

    @Test
    public void getUnionInfoDatabaseTest() throws Exception {
        in2.getUnionInfoDatabase();
        assertFalse(Union.unions.isEmpty());
    }

    @Test
    public void switchToRegisterBtnTest() throws Exception {
        in.findViewById(R.id.switchLoginToRegister).performClick();

        Intent expectedIntent = new Intent(in, Register_user.class);

        ShadowActivity shadowActivity = Shadows.shadowOf(in);
        Intent actualIntent = shadowActivity.getNextStartedActivity();

        assertTrue(actualIntent.filterEquals(expectedIntent));
    }

    @Test
    public void forgotPasswordBtnTest() throws Exception {
        View v = in.findViewById(R.id.forgotPassword);
        v.performClick();

        Dialog alert = ShadowDialog.getLatestDialog();

        assertNotNull(alert);

    }

    @After
    public void tearDown() throws Exception {
        in = null;
    }

}
