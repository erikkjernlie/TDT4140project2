/** StartUpElementsTest
 *
 * GUI test for the elements displayed on startup.
 * For more detailed information, see the README below
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

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class StartUpElementsTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);


    @Test
    public void logInTextTest() {
        /*
            ---- README ----

            If no user is already logged in, checks that all fields required to log in are displayed.
            Tries to set text to these fields and check that the content of the field has been changed

            If user is logged in, checks that all elements on the main page are displayed and working

            This accounts for both scenarios that can happen when the app is opened

            ---- END OF README -----
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
            ViewInteraction appCompatEditText2 = onView(
                    allOf(withId(R.id.enterEmailAddress), withHint("Enter your email"), isDisplayed()));
            appCompatEditText2.perform(replaceText("exampletext"), closeSoftKeyboard());

            ViewInteraction editText = onView(
                    allOf(withId(R.id.enterEmailAddress),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            2),
                                    0),
                            isDisplayed()));
            editText.check(matches(withText("exampletext")));

            ViewInteraction appCompatEditText3 = onView(
                    allOf(withId(R.id.enterPassword), isDisplayed()));
            appCompatEditText3.perform(replaceText("examplepassword"), closeSoftKeyboard());

            ViewInteraction editText2 = onView(
                    allOf(withId(R.id.enterPassword),
                            childAtPosition(
                                    childAtPosition(
                                            IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class),
                                            2),
                                    1),
                            isDisplayed()));
            editText2.check(matches(withText("examplepassword")));
        } else {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ViewInteraction textView = onView(
                    allOf(withId(R.id.textView), withText("UNIBOT"),
                            isDisplayed()));
            textView.check(matches(withText("UNIBOT")));

            ViewInteraction imageView = onView(
                    allOf(withId(R.id.cogwheel),
                            isDisplayed()));
            imageView.check(matches(isDisplayed()));

            ViewInteraction imageView2 = onView(
                    allOf(withId(R.id.imageView3),
                            isDisplayed()));
            imageView2.check(matches(isDisplayed()));

            ViewInteraction textView2 = onView(
                    allOf(withId(R.id.textView2), withText("Use the chat option to explore opportunities and submit your interests. Based on what you submit, UNIBOT is able to give study recommendations"),
                            isDisplayed()));
            textView2.check(matches(withText("Use the chat option to explore opportunities and submit your interests. Based on what you submit, UNIBOT is able to give study recommendations")));

            ViewInteraction textView3 = onView(
                    allOf(withId(R.id.textView3), withText("You can edit your account information from the settings meny in the top right corner"),
                            isDisplayed()));
            textView3.check(matches(withText("You can edit your account information from the settings meny in the top right corner")));

            ViewInteraction button = onView(
                    allOf(withId(R.id.explore),
                            isDisplayed()));
            button.check(matches(isDisplayed()));

            ViewInteraction button3 = onView(
                    allOf(withId(R.id.recommendation),
                            isDisplayed()));
            button3.check(matches(isDisplayed()));

            ViewInteraction button4 = onView(
                    allOf(withId(R.id.aboutUnibot),
                            isDisplayed()));
            button4.check(matches(isDisplayed()));

            ViewInteraction button5 = onView(
                    allOf(withId(R.id.aboutUs),
                            isDisplayed()));
            button5.check(matches(isDisplayed()));

            ViewInteraction button6 = onView(
                    allOf(withId(R.id.signOut),
                            isDisplayed()));
            button6.check(matches(isDisplayed()));

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
