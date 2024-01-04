package com.speer.sharenotex.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.speer.sharenotex.exception.NotFoundException;
import com.speer.sharenotex.model.Note;
import com.speer.sharenotex.model.SharedNote;
import com.speer.sharenotex.repository.NoteRepository;
import com.speer.sharenotex.repository.SharedNoteRepository;
import com.speer.sharenotex.requesdto.NoteRequestDto;
import com.speer.sharenotex.requesdto.ShareNoteRequestDto;
import com.speer.sharenotex.responsedto.NoteResponseDto;
import com.speer.sharenotex.service.KeycloakService;
import com.speer.sharenotex.service.NoteService;
import com.speer.sharenotex.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the {@link NoteService} interface providing operations
 * related to notes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class NoteServiceImpl implements NoteService {

	private final NoteRepository noteRepository;
	private final ModelMapper mapper;
	private final SharedNoteRepository sharedNoteRepository;
	private final KeycloakService keycloakService;

	private String getUserId() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public Boolean createNewNote(NoteRequestDto newNoteRequestDto) {
		log.info("Creating a new note for user: {}", getUserId());
		Note note = new Note();
		note.setUserId(getUserId());
		note.setTitle(newNoteRequestDto.title());
		note.setContent(newNoteRequestDto.content());
		note.setCreatedAt(Timestamp.from(Instant.now()));
		note.setUpdatedAt(Timestamp.from(Instant.now()));
		noteRepository.save(note);
		log.info("New note created successfully.");
		return true;
	}

	@Override
	public NoteResponseDto findNote(Integer id) {
		log.info("Finding note with ID {} for user: {}", id, getUserId());
		Note note = noteRepository.findByIdAndUserId(id, getUserId())
				.orElseThrow(() -> new NotFoundException(Constants.EXCEPTION_KEY_ID, "Not not found"));
		log.info("Note found successfully.");
		return mapper.map(note, NoteResponseDto.class);
	}

	@Override
	public List<NoteResponseDto> findAll() {
		log.info("Finding all notes for user: {}", getUserId());
		List<Note> userNotes = noteRepository.findByUserId(getUserId());
		log.info("Found {} notes for user.", userNotes.size());
		return userNotes.stream().map(note -> mapper.map(note, NoteResponseDto.class)).toList();
	}

	@Override
	public String updateNote(Integer id, NoteRequestDto noteRequestDto) {
		log.info("Updating note with ID {} for user: {}", id, getUserId());
		noteRepository.findByIdAndUserId(id, getUserId()).ifPresentOrElse((note) -> {
			note.setTitle(noteRequestDto.title());
			note.setContent(noteRequestDto.content());
			note.setUpdatedAt(Timestamp.from(Instant.now()));
			noteRepository.save(note);
			log.info("Note updated successfully.");
		}, () -> {
			throw new NotFoundException(Constants.EXCEPTION_KEY_ID, Constants.NOT_FOUND_MESSAGE);
		});

		return Constants.UPDATE_SUCCESS;
	}

	@Override
	public String deleteNoteById(Integer id) {
		log.info("Deleting note with ID {} for user: {}", id, getUserId());
		noteRepository.findByIdAndUserId(id, getUserId()).ifPresentOrElse(noteRepository::delete, () -> {
			throw new NotFoundException(Constants.EXCEPTION_KEY_ID, Constants.NOT_FOUND_MESSAGE);
		});
		log.info("Note deleted successfully.");
		return Constants.DELETE_SUCCESS;
	}

	@Override
	public String shareNoteWithUser(ShareNoteRequestDto shareNoteRequestDto) {
		log.info("Sharing note with ID {} to user: {}", shareNoteRequestDto.noteId(), shareNoteRequestDto.sharedTo());
		noteRepository.findByIdAndUserId(shareNoteRequestDto.noteId(), getUserId()).ifPresentOrElse(note -> {
			keycloakService.getKeycloakUserDetailsById(shareNoteRequestDto.sharedTo());
			SharedNote sharedNote = new SharedNote();
			sharedNote.setSharedBy(note.getUserId());
			sharedNote.setSharedTo(shareNoteRequestDto.sharedTo());
			sharedNote.setSharedAt(Timestamp.from(Instant.now()));
			sharedNote.setNote(note);
			sharedNoteRepository.save(sharedNote);
			log.info("Note shared successfully.");
		}, () -> {
			throw new NotFoundException(Constants.EXCEPTION_KEY_ID, Constants.NOT_FOUND_MESSAGE);
		});

		return Constants.SHARED_SUCCESS;
	}

	@Override
	public List<NoteResponseDto> searchNotes(String query) {
		log.info("Searching notes for user: {} with query: {}", getUserId(), query);
		List<Note> notes = noteRepository.findByUserIdAndContentContainingIgnoreCase(getUserId(), query);
		log.info("Found {} notes matching the query.", notes.size());
		return notes.stream().map(note -> mapper.map(note, NoteResponseDto.class)).toList();
	}
}
