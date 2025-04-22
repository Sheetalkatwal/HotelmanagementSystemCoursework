package com.example.hms.util;

import java.util.regex.Pattern;

/**
 * Utility class for input validation
 */
public class ValidationUtil {
    // Regular expressions for validation
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String USERNAME_REGEX = "^[A-Za-z0-9_]{3,20}$";
    private static final String PASSWORD_REGEX = "^.{6,}$";
    private static final String PHONE_REGEX = "^[0-9+()-]{10,15}$";
    
    // Compiled patterns for better performance
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    
    /**
     * Validate an email address
     * @param email The email to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validate a username
     * @param username The username to validate
     * @return true if the username is valid, false otherwise
     */
    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }
    
    /**
     * Validate a password
     * @param password The password to validate
     * @return true if the password is valid, false otherwise
     */
    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }
    
    /**
     * Validate a phone number
     * @param phone The phone number to validate
     * @return true if the phone number is valid, false otherwise
     */
    public static boolean isValidPhone(String phone) {
        return phone == null || phone.isEmpty() || PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Validate that a string is not empty
     * @param str The string to validate
     * @return true if the string is not empty, false otherwise
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Validate that a number is positive
     * @param number The number to validate
     * @return true if the number is positive, false otherwise
     */
    public static boolean isPositive(Number number) {
        return number != null && number.doubleValue() > 0;
    }
    
    /**
     * Validate that a number is in a range
     * @param number The number to validate
     * @param min The minimum value (inclusive)
     * @param max The maximum value (inclusive)
     * @return true if the number is in the range, false otherwise
     */
    public static boolean isInRange(Number number, double min, double max) {
        return number != null && number.doubleValue() >= min && number.doubleValue() <= max;
    }
}
