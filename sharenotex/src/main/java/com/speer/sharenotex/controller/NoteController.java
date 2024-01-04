package com.speer.sharenotex.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.speer.sharenotex.requesdto.NoteRequestDto;
import com.speer.sharenotex.requesdto.ShareNoteRequestDto;
import com.speer.sharenotex.responsedto.NoteResponseDto;
import com.speer.sharenotex.service.NoteService;
import com.speer.sharenotex.util.Constants;
import com.speer.sharenotex.util.RateLimited;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller class for managing notes, providing endpoints for note creation,
 * retrieval, update, deletion, sharing, and searching.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notes")
@Tag(name = "Note Controller", description = "Endpoint for notes")
public class NoteController {

	private final NoteService noteService;

	/**
	 * Endpoint for creating a new note.
	 *
	 * @param noteRequestDto DTO containing details for creating a new note.
	 * @return ResponseEntity indicating the success of note creation.
	 */
	@PostMapping()
	@RateLimited
	@Operation(summary = "Create a new note", description = "Endpoint to create a new note.")
	ResponseEntity<String> newNote(@Valid @RequestBody NoteRequestDto noteRequestDto) {
		noteService.createNewNote(noteRequestDto);
		return ResponseEntity.ok(Constants.NOTE_ADDED_SUCCESS);
	}

	/**
	 * Endpoint for retrieving a note by its ID.
	 *
	 * @param id ID of the note to retrieve.
	 * @return ResponseEntity containing the retrieved note.
	 */
	@GetMapping("/{id}")
	@RateLimited
	@Operation(summary = "Get a note by ID", description = "Endpoint to retrieve a note by its ID.")
	ResponseEntity<NoteResponseDto> fetchNoteById(@PathVariable Integer id) {
		return ResponseEntity.ok(noteService.findNote(id));
	}

	/**
	 * Endpoint for retrieving all notes.
	 *
	 * @return ResponseEntity containing a list of all notes.
	 */
	@GetMapping()
	@RateLimited
	@Operation(summary = "Get all notes", description = "Endpoint to retrieve all notes.")
	ResponseEntity<List<NoteResponseDto>> fetchAllNote() {
		return ResponseEntity.ok(noteService.findAll());
	}

	/**
	 * Endpoint for updating a note by its ID.
	 *
	 * @param id             ID of the note to update.
	 * @param noteRequestDto DTO containing updated details for the note.
	 * @return ResponseEntity indicating the success of the note update.
	 */
	@PutMapping("/{id}")
	@RateLimited
	@Operation(summary = "Update a note by ID", description = "Endpoint to update a note by its ID.")
	ResponseEntity<String> updateNote(@Valid @PathVariable Integer id, @RequestBody NoteRequestDto noteRequestDto) {
		return ResponseEntity.ok(noteService.updateNote(id, noteRequestDto));
	}

	/**
	 * Endpoint for deleting a note by its ID.
	 *
	 * @param id ID of the note to delete.
	 * @return ResponseEntity indicating the success of the note deletion.
	 */
	@DeleteMapping("/{id}")
	@RateLimited
	@Operation(summary = "Delete a note by ID", description = "Endpoint to delete a note by its ID.")
	ResponseEntity<String> deleteNote(@PathVariable Integer id) {
		return ResponseEntity.ok(noteService.deleteNoteById(id));
	}

	/**
	 * Endpoint for sharing a note with a user.
	 *
	 * @param shareNoteRequestDto DTO containing details for sharing a note.
	 * @return ResponseEntity indicating the success of note sharing.
	 */
	@PostMapping("/share")
	@RateLimited
	@Operation(summary = "Share a note with a user", description = "Endpoint to share a note with a user.")
	ResponseEntity<String> shareNote(@RequestBody ShareNoteRequestDto shareNoteRequestDto) {
		return ResponseEntity.ok(noteService.shareNoteWithUser(shareNoteRequestDto));
	}

	/**
	 * Endpoint for searching notes based on a query.
	 *
	 * @param query Search query string.
	 * @return ResponseEntity containing a list of notes matching the search query.
	 */
	@GetMapping("/search")
	@RateLimited
	@Operation(summary = "Search notes", description = "Endpoint to search notes based on a query.")
	ResponseEntity<List<NoteResponseDto>> searchNotes(@RequestParam("query") String query) {
		List<NoteResponseDto> searchResults = noteService.searchNotes(query);
		return ResponseEntity.ok(searchResults);
	}

}
