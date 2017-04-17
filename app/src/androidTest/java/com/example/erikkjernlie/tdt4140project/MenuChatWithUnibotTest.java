/** MenuChatWithUnibotTest
 *
 * GUI test for the Chat with Unibot function on the main menu
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MenuChatWithUnibotTest {

    @Rule
    public ActivityTestRule<SplashScreen> mActivityTestRule = new ActivityTestRule<>(SplashScreen.class);

    @Test
    public void chatWithUnibotTest() {

        /*
            ---- README ----

            Moves to the main screen (either directly or by logging in...)
            Clicks on "Chat with unibot"
            Makes sure all elements are visible
            Clicks the "help" button
            Makes sure all relevant elements for the help function are visible
            Clicks "I don't need any more help"
            Clicks "Back"
            Makes sure the "Sign out" button is visible and clickable

            ---- END OF README ----
         */

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

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.explore), withText("Chat with unibot"), isDisplayed()));
        appCompatButton2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.back), withText("BACK"),
                        isDisplayed()));
        textView.check(matches(withText("BACK")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.unibot), withText("UNIBOT"),
                        isDisplayed()));
        textView2.check(matches(withText("UNIBOT")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.help), withText("HELP"),
                        isDisplayed()));
        textView3.check(matches(withText("HELP")));

        ViewInteraction listView = onView(
                allOf(withId(R.id.msgview),
                        isDisplayed()));
        listView.check(matches(isDisplayed()));

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.msgcontain),
                        isDisplayed()));
        linearLayout.check(matches(isDisplayed()));

        ViewInteraction editText = onView(
                allOf(withId(R.id.msg),
                        isDisplayed()));
        editText.check(matches(withHint("Ask uniBOT...")));

        ViewInteraction button = onView(
                allOf(withId(R.id.send),
                        isDisplayed()));
        button.check(matches(isDisplayed()));


        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.help), withText("HELP"), isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(android.R.id.message),isDisplayed()));
        textView4.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(android.R.id.button1),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction textView5 = onView(
                allOf(IsInstanceOf.<View>instanceOf(android.widget.TextView.class), withText("Help with uniBOT"),
                        isDisplayed()));
        textView5.check(matches(withText("Help with uniBOT")));

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("I don't need any more help"),
                        withParent(allOf(withClassName(is("com.android.internal.widget.ButtonBarLayout")),
                                withParent(withClassName(is("android.widget.LinearLayout"))))),
                        isDisplayed()));
        appCompatButton3.perform(click());
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.back), withText("BACK"), isDisplayed()));
        appCompatTextView2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(6000);
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
