package com.speer.sharenotex.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.speer.sharenotex.model.Note;

/**
 * Repository interface for managing Note entities in the database. Extends
 * JpaRepository for basic CRUD operations on the Note entity.
 */
@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	/**
	 * Retrieves an optional Note by its unique identifier (ID) and associated user
	 * ID.
	 *
	 * @param id     Unique identifier of the note.
	 * @param userId User identifier associated with the note.
	 * @return Optional containing the found Note or an empty Optional if not found.
	 */
	Optional<Note> findByIdAndUserId(Integer id, String userId);

	/**
	 * Retrieves a list of Notes by the associated user ID.
	 *
	 * @param userId User identifier associated with the notes.
	 * @return List of notes belonging to the specified user.
	 */
	List<Note> findByUserId(String userId);

	/**
	 * Retrieves a list of Notes by the associated user ID and containing a
	 * specified query in the content (case-insensitive).
	 *
	 * @param id    User identifier associated with the notes.
	 * @param query Search query string to be found in the content of notes.
	 * @return List of notes belonging to the specified user and matching the query
	 *         in content.
	 */
	List<Note> findByUserIdAndContentContainingIgnoreCase(String id, String query);
}
