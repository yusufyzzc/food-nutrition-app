package com.example.foodnutritionapp.controller;

/**
 * Input validation utility following Single Responsibility Principle
 */
public class InputValidator {

    /**
     * Validate food search input
     * @param input User input to validate
     * @return Validation result
     */
    public static ValidationResult validateFoodInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ValidationResult(false, "Please enter a food name");
        }

        if (input.trim().length() < 2) {
            return new ValidationResult(false, "Food name must be at least 2 characters long");
        }

        if (input.trim().length() > 100) {
            return new ValidationResult(false, "Food name is too long");
        }

        // Check for special characters that might cause API issues
        if (input.matches(".*[<>\"'&].*")) {
            return new ValidationResult(false, "Please avoid special characters");
        }

        return new ValidationResult(true, "Valid input");
    }

    public static class ValidationResult {
        private final boolean isValid;
        private final String message;

        public ValidationResult(boolean isValid, String message) {
            this.isValid = isValid;
            this.message = message;
        }

        public boolean isValid() { return isValid; }
        public String getMessage() { return message; }
    }
}