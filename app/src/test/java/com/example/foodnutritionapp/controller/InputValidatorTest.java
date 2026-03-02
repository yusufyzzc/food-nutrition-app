package com.example.foodnutritionapp.controller;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for InputValidator class
 * Updated for Mobile Application Programming II
 * Tests validation logic for user input
 */
public class InputValidatorTest {

    @Before
    public void setUp() {
        // Setup test environment
    }

    @Test
    public void validateFoodInput_ValidInput_ReturnsValid() {
        String validInput = "apple";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(validInput);

        assertTrue("Valid input should pass validation", result.isValid());
        assertEquals("Valid input", result.getMessage());
    }

    @Test
    public void validateFoodInput_ValidInputWithNumbers_ReturnsValid() {
        String validInput = "apple 500g";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(validInput);

        assertTrue("Valid input with numbers should pass", result.isValid());
    }

    @Test
    public void validateFoodInput_EmptyInput_ReturnsInvalid() {
        String emptyInput = "";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(emptyInput);

        assertFalse("Empty input should fail validation", result.isValid());
        assertEquals("Please enter a food name", result.getMessage());
    }

    @Test
    public void validateFoodInput_NullInput_ReturnsInvalid() {
        String nullInput = null;
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(nullInput);

        assertFalse("Null input should fail validation", result.isValid());
        assertEquals("Please enter a food name", result.getMessage());
    }

    @Test
    public void validateFoodInput_WhitespaceOnly_ReturnsInvalid() {
        String whitespaceInput = "   ";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(whitespaceInput);

        assertFalse("Whitespace-only input should fail validation", result.isValid());
        assertEquals("Please enter a food name", result.getMessage());
    }

    @Test
    public void validateFoodInput_TooShort_ReturnsInvalid() {
        String shortInput = "a";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(shortInput);

        assertFalse("Too short input should fail validation", result.isValid());
        assertEquals("Food name must be at least 2 characters long", result.getMessage());
    }

    @Test
    public void validateFoodInput_ExactlyTwoCharacters_ReturnsValid() {
        String twoCharInput = "ab";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(twoCharInput);

        assertTrue("Two character input should pass validation", result.isValid());
    }

    @Test
    public void validateFoodInput_TooLong_ReturnsInvalid() {
        StringBuilder longInput = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            longInput.append("a");
        }

        InputValidator.ValidationResult result = InputValidator.validateFoodInput(longInput.toString());

        assertFalse("Too long input should fail validation", result.isValid());
        assertEquals("Food name is too long", result.getMessage());
    }

    @Test
    public void validateFoodInput_ExactlyHundredCharacters_ReturnsValid() {
        StringBuilder input = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            input.append("a");
        }

        InputValidator.ValidationResult result = InputValidator.validateFoodInput(input.toString());

        assertTrue("100 character input should pass validation", result.isValid());
    }

    @Test
    public void validateFoodInput_SpecialCharacters_ReturnsInvalid() {
        String[] specialCharInputs = {"apple<script>", "test\"food", "food'name", "test&food"};

        for (String input : specialCharInputs) {
            InputValidator.ValidationResult result = InputValidator.validateFoodInput(input);
            assertFalse("Input '" + input + "' with special characters should fail", result.isValid());
            assertEquals("Please avoid special characters", result.getMessage());
        }
    }

    @Test
    public void validateFoodInput_ValidInputWithSpaces_ReturnsValid() {
        String validInputWithSpaces = "chicken breast";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(validInputWithSpaces);

        assertTrue("Valid input with spaces should pass validation", result.isValid());
    }

    @Test
    public void validateFoodInput_ValidInputWithHyphen_ReturnsValid() {
        String validInput = "fat-free milk";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(validInput);

        assertTrue("Valid input with hyphen should pass validation", result.isValid());
    }

    @Test
    public void validateFoodInput_ValidInputWithComma_ReturnsValid() {
        String validInput = "apples, oranges";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(validInput);

        assertTrue("Valid input with comma should pass validation", result.isValid());
    }

    @Test
    public void validateFoodInput_LeadingTrailingSpaces_ReturnsValid() {
        String inputWithSpaces = "  apple  ";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(inputWithSpaces);

        assertTrue("Input with leading/trailing spaces should pass after trim", result.isValid());
    }

    @Test
    public void validateFoodInput_MultipleWords_ReturnsValid() {
        String multiWord = "whole grain brown rice";
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(multiWord);

        assertTrue("Multiple word input should pass validation", result.isValid());
    }
}