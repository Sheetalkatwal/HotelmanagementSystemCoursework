package com.example.hms.dao;

import com.example.hms.model.User;

import java.util.List;

/**
 * DAO interface for User entity with additional methods specific to User
 */
public interface UserDAO extends GenericDAO<User, Integer> {

    /**
     * Find a user by username
     * @param username The username to search for
     * @return The found user or null if not found
     */
    User findByUsername(String username);

    /**
     * Find a user by email
     * @param email The email to search for
     * @return The found user or null if not found
     */
    User findByEmail(String email);

    /**
     * Authenticate a user with username and password
     * @param username The username
     * @param password The password
     * @return The authenticated user or null if authentication fails
     */
    User authenticate(String username, String password);

    /**
     * Find users by role
     * @param role The role to search for
     * @return List of users with the specified role
     */
    List<User> findByRole(String role);

    /**
     * Find users by search term (username, email, or full name)
     * @param search The search term
     * @return List of users matching the search term
     */
    List<User> findBySearch(String search);

    /**
     * Find users by role and search term
     * @param role The role to search for
     * @param search The search term
     * @return List of users matching both criteria
     */
    List<User> findByRoleAndSearch(String role, String search);
}
