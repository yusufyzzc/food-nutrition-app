package com.example.foodnutritionapp;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.foodnutritionapp.controller.InputValidator;
import com.example.foodnutritionapp.model.DailyIntake;
import com.example.foodnutritionapp.model.NutritionInfo;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.foodnutritionapp", appContext.getPackageName());
    }

    // ========== YENİ TESTLER ==========

    /**
     * Test 1: InputValidator - geçerli giriş testi
     */
    @Test
    public void testInputValidatorWithValidInput() {
        InputValidator.ValidationResult result =
                InputValidator.validateFoodInput("apple");

        assertTrue("Valid input should return true", result.isValid());
        assertEquals("Valid input", result.getMessage());
    }

    /**
     * Test 2: InputValidator - boş giriş testi
     */
    @Test
    public void testInputValidatorWithEmptyInput() {
        InputValidator.ValidationResult result =
                InputValidator.validateFoodInput("");

        assertFalse("Empty input should return false", result.isValid());
        assertEquals("Please enter a food name", result.getMessage());
    }

    /**
     * Test 3: InputValidator - çok kısa giriş testi
     */
    @Test
    public void testInputValidatorWithTooShortInput() {
        InputValidator.ValidationResult result =
                InputValidator.validateFoodInput("a");

        assertFalse("Too short input should return false", result.isValid());
        assertEquals("Food name must be at least 2 characters long", result.getMessage());
    }

    /**
     * Test 4: InputValidator - özel karakterler testi
     */
    @Test
    public void testInputValidatorWithSpecialCharacters() {
        InputValidator.ValidationResult result =
                InputValidator.validateFoodInput("apple<script>");

        assertFalse("Special characters should not be allowed", result.isValid());
        assertEquals("Please avoid special characters", result.getMessage());
    }

    /**
     * Test 5: NutritionInfo model testi
     */
    @Test
    public void testNutritionInfoModel() {
        NutritionInfo info = new NutritionInfo();
        info.setFoodName("Apple");
        info.setCalories(52.0);
        info.setProtein(0.3);
        info.setTotalCarbohydrate(14.0);
        info.setTotalFat(0.2);

        assertEquals("Apple", info.getFoodName());
        assertEquals(52.0, info.getCalories(), 0.01);
        assertEquals(0.3, info.getProtein(), 0.01);
        assertEquals(14.0, info.getTotalCarbohydrate(), 0.01);
        assertEquals(0.2, info.getTotalFat(), 0.01);
    }

    /**
     * Test 6: DailyIntake model testi
     */
    @Test
    public void testDailyIntakeModel() {
        DailyIntake intake = new DailyIntake("Banana", 89.0, 1.1, 22.8, 0.3);

        assertEquals("Banana", intake.getFoodName());
        assertEquals(89.0, intake.getCalories(), 0.01);
        assertEquals(1.1, intake.getProtein(), 0.01);
        assertEquals(22.8, intake.getCarbs(), 0.01);
        assertEquals(0.3, intake.getFat(), 0.01);
        assertNotNull("Date should be auto-generated", intake.getDate());
        assertTrue("Timestamp should be positive", intake.getTimestamp() > 0);
    }

    /**
     * Test 7: Context kullanılabilirlik testi
     */
    @Test
    public void testContextIsUsable() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertNotNull("App context should not be null", appContext);
        assertNotNull("Resources should not be null", appContext.getResources());
        assertNotNull("Package manager should not be null", appContext.getPackageManager());
    }

    /**
     * Test 8: NutritionInfo - varsayılan değerler testi
     */
    @Test
    public void testNutritionInfoDefaultValues() {
        NutritionInfo info = new NutritionInfo();

        // FoodName boş string olarak initialize edilmiş (NonNull annotation var)
        assertEquals("", info.getFoodName());
        assertEquals(0.0, info.getCalories(), 0.01);
        assertEquals(0.0, info.getProtein(), 0.01);
    }

    /**
     * Test 9: InputValidator - null giriş testi
     */
    @Test
    public void testInputValidatorWithNullInput() {
        InputValidator.ValidationResult result =
                InputValidator.validateFoodInput(null);

        assertFalse("Null input should return false", result.isValid());
        assertEquals("Please enter a food name", result.getMessage());
    }
}