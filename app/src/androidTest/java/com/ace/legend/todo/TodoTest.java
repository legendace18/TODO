package com.ace.legend.todo;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * Created by rohan on 5/25/15.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest

public class TodoTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(
            MainActivity.class);

    @Test
    public void viewDetail() throws InterruptedException {


       /* onData( allOf( instanceOf(String.class), withContent("Pokhara, Western Region, Nepal")))
                .perform(click());*/
        /*onData(hasToString(startsWith("Pokhara, Western Region, Nepal")))
                .inAdapterView(withId(R.id.autoCompleteTextView))
                .perform(click());*/
       /* onData(allOf(withContent("Pokhara, Western Region, Nepal")))  // Find the row in the list

                .perform(click());*/

        onData(anything()).inAdapterView(withId(R.id.lv_todo)).atPosition(0).perform(click());
       /* onData(Matchers.<Object>allOf(withText("Pokhara, Western Region, Nepal"))).perform(click());*/

        //onView(withId(R.id.btnCreateHotel)).perform(click());

    }
}
