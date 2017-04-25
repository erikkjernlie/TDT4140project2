/** RegisterUserTest
 *
 * GUI test for the Register-function
 * For more details see the README in the test below
 *
 * Created by Herman Horn
 * Copyright © uniBOT
 */

package com.unibot.erikkjernlie.tdt4140project;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterUserTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);

    @Test
    public void registerUserTest() {
        /*
            ---- README -----

            Deletes the testregister-user if already exists
            Registers the user and makes sure the user registration process is completed
            with all relevant information in the addInfo-window being entered correctly

            NOTE: REQUIRES THAT THERE IS NO ACTIVE USER ALREADT LOGGED IN BEFORE RUNNING THE TEST
                  If the test moves straight to the menu and fails, you are not propperly logged out

            ---- END OF README ---
         */

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            ViewInteraction button10 = onView(
                    allOf(withId(R.id.signOut),
                            isDisplayed()));
            button10.check(matches(isDisplayed()));

            ViewInteraction appCompatButton20 = onView(
                    allOf(withId(R.id.signOut), withText("Sign out"), isDisplayed()));
            appCompatButton20.perform(click());
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            FirebaseAuth.getInstance().signInWithEmailAndPassword("testregister@user.com", "test1234");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.delete();
        } catch (Exception e) {
        }

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.switchLoginToRegister), isDisplayed()));
        appCompatButton.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.enterEmailAddress), isDisplayed()));
        appCompatEditText.perform(replaceText("testregister@user.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.enterPassword1), isDisplayed()));
        appCompatEditText5.perform(replaceText("test1234"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.enterPassword2), isDisplayed()));
        appCompatEditText6.perform(replaceText("test1234"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.registerBtn), isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageButton = onView(
                allOf(withId(R.id.man), isDisplayed()));
        imageButton.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.cbPicView),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction imageButton2 = onView(
                allOf(withId(R.id.man),
                        isDisplayed()));
        imageButton2.check(matches(isDisplayed()));

        ViewInteraction imageButton3 = onView(
                allOf(withId(R.id.female),
                        isDisplayed()));
        imageButton3.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.birthYear),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.EditText.class),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));


        ViewInteraction button = onView(
                allOf(withId(R.id.gradeBtn),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.averageBtn),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.dropdownCourses),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.dropdownExtrapoints),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction button5 = onView(
                allOf(withId(R.id.submit),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.gradeBtn), isDisplayed()));
        appCompatButton3.perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.add_grades),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.plus6), isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.add_grades), isDisplayed()));
        appCompatTextView.perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.dropdownCourses), isDisplayed()));
        appCompatButton5.perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button6 = onView(
                allOf(withId(android.R.id.button1),
                        isDisplayed()));
        button6.check(matches(isDisplayed()));

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(android.R.id.button1),
                        isDisplayed()));
        appCompatButton6.perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.dropdownExtrapoints), isDisplayed()));
        appCompatButton7.perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button7 = onView(
                allOf(withId(android.R.id.button1),
                        isDisplayed()));
        button7.check(matches(isDisplayed()));

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(android.R.id.button1),
                        isDisplayed()));
        appCompatButton8.perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.submit), isDisplayed()));
        appCompatButton9.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button10 = onView(
                allOf(withId(R.id.signOut),
                        isDisplayed()));
        button10.check(matches(isDisplayed()));

        ViewInteraction appCompatButton20 = onView(
                allOf(withId(R.id.signOut), withText("Sign out"), isDisplayed()));
        appCompatButton20.perform(click());

        try {
            FirebaseAuth.getInstance().signOut();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword("testregister@user.com", "test1234");
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            user.delete();
        } catch (Exception e) {
        }
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
