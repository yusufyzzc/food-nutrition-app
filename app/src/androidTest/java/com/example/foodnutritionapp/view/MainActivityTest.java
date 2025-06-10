package com.example.foodnutritionapp.view;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.foodnutritionapp.MainActivity;
import com.example.foodnutritionapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * UI tests for MainActivity
 * Tests user interactions and navigation
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testUIElementsVisible() {
        // Are EditText and Button visible?
        Espresso.onView(ViewMatchers.withId(R.id.editTextFoodName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        Espresso.onView(ViewMatchers.withId(R.id.buttonSearch))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));


        Espresso.onView(ViewMatchers.withId(R.id.textViewTitle))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testFoodSearch() {
        String testFood = "apple";

        // type and touch
        Espresso.onView(ViewMatchers.withId(R.id.editTextFoodName))
                .perform(ViewActions.typeText(testFood), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.buttonSearch))
                .perform(ViewActions.click());

    }

    @Test
    public void testEmptySearchHandling() {
        // Empty click
        Espresso.onView(ViewMatchers.withId(R.id.buttonSearch))
                .perform(ViewActions.click());

        // EditText should still be visible instead of error message
        Espresso.onView(ViewMatchers.withId(R.id.editTextFoodName))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
