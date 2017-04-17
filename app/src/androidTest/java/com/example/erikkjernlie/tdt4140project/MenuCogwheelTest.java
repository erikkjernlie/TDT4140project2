/** MenuCogwheelTest
 *
 * GUI test for the Cogwheel function on the main menu
 * For more details see the README in the test below
 *
 * Created by Herman Horn
 * Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MenuCogwheelTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);

    @Test
    public void menuCogwheelTest() {
        /*

            Checks that the cog wheel from the main menu view
            works as intented and allows the user to view and change
            user information

         */

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            ViewInteraction appCompatEditText4 = onView(
                    allOf(withId(R.id.enterEmailAddress), isDisplayed()));
            appCompatEditText4.perform(replaceText("test@user.com"), closeSoftKeyboard());

            ViewInteraction appCompatEditText5 = onView(
                    allOf(withId(R.id.enterPassword), isDisplayed()));
            appCompatEditText5.perform(replaceText("testuser"), closeSoftKeyboard());

            ViewInteraction appCompatButton = onView(
                    allOf(withId(R.id.logInBtn), isDisplayed()));
            appCompatButton.perform(click());
        }

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction imageView = onView(
                allOf(withId(R.id.cogwheel), isDisplayed()));
        imageView.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.change_password),
                        isDisplayed()));
        textView.check(matches(isDisplayed()));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.return_to_settings),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.info),
                        isDisplayed()));
        textView3.check(matches(isDisplayed()));

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.info), isDisplayed()));
        appCompatTextView.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button = onView(
                allOf(withId(R.id.submit),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.gradeBtn), isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.add_grades), isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.submit), isDisplayed()));
        appCompatButton3.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button2 = onView(
                allOf(withId(R.id.signOut),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction imageView2 = onView(
                allOf(withId(R.id.cogwheel), isDisplayed()));
        imageView2.perform(click());

        ViewInteraction appCompatTextView3 = onView(
                allOf(withId(R.id.change_password), isDisplayed()));
        appCompatTextView3.perform(click());

        ViewInteraction editText = onView(
                allOf(withId(R.id.email_retrieve_password),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.confirm_email),
                        isDisplayed()));
        textView5.check(matches(isDisplayed()));

        pressBack();

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView4 = onView(
                allOf(withId(R.id.return_to_settings), isDisplayed()));
        appCompatTextView4.perform(click());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button3 = onView(
                allOf(withId(R.id.signOut),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

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
