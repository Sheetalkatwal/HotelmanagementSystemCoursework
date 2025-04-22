package com.example.hms.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for password hashing and verification
 */
public class PasswordUtil {
    private static final Logger LOGGER = Logger.getLogger(PasswordUtil.class.getName());
    
    /**
     * Hash a password using SHA-256
     * @param password The password to hash
     * @return The hashed password
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.SEVERE, "Error hashing password", e);
            return null;
        }
    }
    
    /**
     * Verify a password against a hashed password
     * @param password The password to verify
     * @param hashedPassword The hashed password to verify against
     * @return true if the password matches the hash, false otherwise
     */
    public static boolean verifyPassword(String password, String hashedPassword) {
        String hashedInput = hashPassword(password);
        return hashedInput != null && hashedInput.equals(hashedPassword);
    }
}
