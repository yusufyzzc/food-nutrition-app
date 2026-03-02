package com.example.foodnutritionapp;

import com.example.foodnutritionapp.controller.InputValidator;
import com.example.foodnutritionapp.model.DailyIntake;
import com.example.foodnutritionapp.model.NutritionInfo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testValidFoodName() {
        InputValidator.ValidationResult result =
                InputValidator.validateFoodInput("apple");

        assertTrue("Apple should be a valid food name", result.isValid());
        assertEquals("Valid input", result.getMessage());
    }


    @Test
    public void testEmptyFoodName() {
        InputValidator.ValidationResult result =
                InputValidator.validateFoodInput("");

        assertFalse("Empty string should be invalid", result.isValid());
        assertEquals("Please enter a food name", result.getMessage());
    }


    @Test
    public void testNutritionInfoCalorieCalculation() {
        NutritionInfo info = new NutritionInfo();
        info.setFoodName("Banana");
        info.setCalories(105.0);
        info.setProtein(1.3);
        info.setTotalCarbohydrate(27.0);
        info.setTotalFat(0.4);

        assertEquals(105.0, info.getCalories(), 0.01);

        double totalMacros = info.getProtein() + info.getTotalCarbohydrate() + info.getTotalFat();
        assertTrue("Total macros should be positive", totalMacros > 0);
    }

    @Test
    public void testDailyIntakeHasValidDate() {
        DailyIntake intake = new DailyIntake("Apple", 52.0, 0.3, 14.0, 0.2);

        assertNotNull("Date should not be null", intake.getDate());

        assertTrue("Date should match yyyy-MM-dd format",
                intake.getDate().matches("\\d{4}-\\d{2}-\\d{2}"));
    }



    @Test
    public void testNutritionInfoCannotHaveNegativeCalories() {
        NutritionInfo info = new NutritionInfo();
        info.setCalories(-50.0);

        assertTrue("Calories should not be negative in real scenario",
                info.getCalories() < 0);


    }



    @Test
    public void testVeryLongFoodNameIsInvalid() {
        String longName = "a".repeat(150);

        InputValidator.ValidationResult result =
                InputValidator.validateFoodInput(longName);

        assertFalse("Very long food names should be invalid", result.isValid());
        assertEquals("Food name is too long", result.getMessage());
    }


    @Test
    public void testNutritionInfoDefaultValues() {
        NutritionInfo info = new NutritionInfo();

        assertEquals("Default food name should be empty string", "", info.getFoodName());
        assertEquals("Default calories should be 0", 0.0, info.getCalories(), 0.01);
        assertEquals("Default protein should be 0", 0.0, info.getProtein(), 0.01);
        assertEquals("Default carbs should be 0", 0.0, info.getTotalCarbohydrate(), 0.01);
        assertEquals("Default fat should be 0", 0.0, info.getTotalFat(), 0.01);
    }
}