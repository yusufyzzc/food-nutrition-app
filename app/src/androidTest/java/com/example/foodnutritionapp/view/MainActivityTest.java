package com.example.foodnutritionapp.view;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final String PACKAGE_NAME = "com.example.foodnutritionapp";
    private static final int LAUNCH_TIMEOUT = 5000;

    private UiDevice device;

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Wait for launcher
        device.wait(Until.hasObject(By.pkg(device.getLauncherPackageName()).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);

        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
    }


    @Test
    public void testAppLaunches() {
        UiObject2 app = device.findObject(By.pkg(PACKAGE_NAME));
        assertNotNull("App should launch successfully", app);
    }

    @Test
    public void testSearchButtonExists() {
        UiObject2 searchButton = device.findObject(By.text("Search"));
    }


    @Test
    public void testAppTitleExists() {
        UiObject2 title = device.findObject(By.text("Food Nutrition"));
        assertNotNull("App title should exist", title);
    }


    @Test
    public void testQuickManualAddExists() {
        UiObject2 quickAdd = device.findObject(By.text("Quick Manual Add"));
        assertNotNull("Quick Manual Add section should exist", quickAdd);
    }


    @Test
    public void testAddToDailyButtonExists() {
        UiObject2 addButton = device.findObject(By.text("ADD TO DAILY"));
        assertNotNull("Add to Daily button should exist", addButton);
    }


    @Test
    public void testCompareCardExists() {
        UiObject2 compare = device.findObject(By.text("Compare"));
        assertNotNull("Compare card should exist", compare);
    }


    @Test
    public void testResetButtonExists() {
        UiObject2 reset = device.findObject(By.text("RESET"));
        assertNotNull("Reset button should exist", reset);
    }


    @Test
    public void testTodayCaloriesDisplayed() {
        UiObject2 todayKcal = device.findObject(By.text("Today's kcal"));
        assertNotNull("Today's kcal text should be displayed", todayKcal);
    }
}