package com.speer.sharenotex.service;

import java.util.List;

import com.speer.sharenotex.requesdto.NoteRequestDto;
import com.speer.sharenotex.requesdto.ShareNoteRequestDto;
import com.speer.sharenotex.responsedto.NoteResponseDto;

/**
 * Service interface defining operations related to notes in the application.
 */
public interface NoteService {

	/**
	 * Creates a new note based on the provided request details.
	 *
	 * @param newNoteRequestDto DTO containing details for creating a new note.
	 * @return True if the new note is created successfully; false otherwise.
	 */
	Boolean createNewNote(NoteRequestDto newNoteRequestDto);

	/**
	 * Retrieves details of a note based on its unique identifier.
	 *
	 * @param id Unique identifier of the note.
	 * @return NoteResponseDto containing details of the requested note.
	 */
	NoteResponseDto findNote(Integer id);

	/**
	 * Retrieves details of all notes in the application.
	 *
	 * @return List of NoteResponseDto containing details of all notes.
	 */
	List<NoteResponseDto> findAll();

	/**
	 * Updates an existing note based on the provided details.
	 *
	 * @param id             Unique identifier of the note to be updated.
	 * @param noteRequestDto DTO containing details for updating the note.
	 * @return A message indicating the result of the update operation.
	 */
	String updateNote(Integer id, NoteRequestDto noteRequestDto);

	/**
	 * Deletes a note based on its unique identifier.
	 *
	 * @param id Unique identifier of the note to be deleted.
	 * @return A message indicating the result of the delete operation.
	 */
	String deleteNoteById(Integer id);

	/**
	 * Shares a note with another user based on the provided details.
	 *
	 * @param shareNoteRequestDto DTO containing details for sharing the note.
	 * @return A message indicating the result of the share operation.
	 */
	String shareNoteWithUser(ShareNoteRequestDto shareNoteRequestDto);

	/**
	 * Searches for notes based on a specified query.
	 *
	 * @param query Search query string.
	 * @return List of NoteResponseDto containing details of notes matching the
	 *         query.
	 */
	List<NoteResponseDto> searchNotes(String query);

}
