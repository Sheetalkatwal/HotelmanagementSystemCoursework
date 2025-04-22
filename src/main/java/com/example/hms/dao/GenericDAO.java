package com.example.hms.dao;

import java.util.List;

/**
 * Generic DAO interface with common CRUD operations
 * @param <T> The model class
 * @param <ID> The type of the ID field
 */
public interface GenericDAO<T, ID> {
    
    /**
     * Save a new entity
     * @param entity The entity to save
     * @return The saved entity with ID populated
     */
    T save(T entity);
    
    /**
     * Update an existing entity
     * @param entity The entity to update
     * @return The updated entity
     */
    T update(T entity);
    
    /**
     * Find an entity by its ID
     * @param id The ID of the entity
     * @return The found entity or null if not found
     */
    T findById(ID id);
    
    /**
     * Find all entities
     * @return List of all entities
     */
    List<T> findAll();
    
    /**
     * Delete an entity by its ID
     * @param id The ID of the entity to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteById(ID id);
}
