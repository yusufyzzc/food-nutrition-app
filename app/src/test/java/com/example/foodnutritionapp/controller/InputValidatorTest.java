package com.example.foodnutritionapp.controller;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit tests for InputValidator class
 * Tests validation logic for user input
 */
public class InputValidatorTest {

    @Test
    public void validateFoodInput_ValidInput_ReturnsValid() {
        // Arrange
        String validInput = "apple";

        // Act
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(validInput);

        // Assert
        assertTrue("Valid input should pass validation", result.isValid());
    }

    @Test
    public void validateFoodInput_EmptyInput_ReturnsInvalid() {
        // Arrange
        String emptyInput = "";

        // Act
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(emptyInput);

        // Assert
        assertFalse("Empty input should fail validation", result.isValid());
        assertEquals("Please enter a food name", result.getMessage());
    }

    @Test
    public void validateFoodInput_NullInput_ReturnsInvalid() {
        // Arrange
        String nullInput = null;

        // Act
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(nullInput);

        // Assert
        assertFalse("Null input should fail validation", result.isValid());
        assertEquals("Please enter a food name", result.getMessage());
    }

    @Test
    public void validateFoodInput_WhitespaceOnly_ReturnsInvalid() {
        // Arrange
        String whitespaceInput = "   ";

        // Act
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(whitespaceInput);

        // Assert
        assertFalse("Whitespace-only input should fail validation", result.isValid());
    }

    @Test
    public void validateFoodInput_TooShort_ReturnsInvalid() {
        // Arrange
        String shortInput = "a";

        // Act
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(shortInput);

        // Assert
        assertFalse("Too short input should fail validation", result.isValid());
        assertEquals("Food name must be at least 2 characters long", result.getMessage());
    }

    @Test
    public void validateFoodInput_TooLong_ReturnsInvalid() {
        // Arrange
        StringBuilder longInput = new StringBuilder();
        for (int i = 0; i < 101; i++) {
            longInput.append("a");
        }

        // Act
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(longInput.toString());

        // Assert
        assertFalse("Too long input should fail validation", result.isValid());
        assertEquals("Food name is too long", result.getMessage());
    }

    @Test
    public void validateFoodInput_SpecialCharacters_ReturnsInvalid() {
        // Arrange
        String inputWithSpecialChars = "apple<script>";

        // Act
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(inputWithSpecialChars);

        // Assert
        assertFalse("Input with special characters should fail validation", result.isValid());
        assertEquals("Please avoid special characters", result.getMessage());
    }

    @Test
    public void validateFoodInput_ValidInputWithSpaces_ReturnsValid() {
        // Arrange
        String validInputWithSpaces = "chicken breast";

        // Act
        InputValidator.ValidationResult result = InputValidator.validateFoodInput(validInputWithSpaces);

        // Assert
        assertTrue("Valid input with spaces should pass validation", result.isValid());
    }
}