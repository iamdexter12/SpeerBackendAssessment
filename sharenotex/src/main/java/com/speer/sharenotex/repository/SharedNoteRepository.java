package com.speer.sharenotex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.speer.sharenotex.model.SharedNote;

/**
 * Repository interface for managing SharedNote entities in the database.
 * Extends JpaRepository for basic CRUD operations on the SharedNote entity.
 */
public interface SharedNoteRepository extends JpaRepository<SharedNote, Integer> {

}
