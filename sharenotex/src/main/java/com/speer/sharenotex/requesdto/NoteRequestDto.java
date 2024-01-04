package com.speer.sharenotex.requesdto;

/**
 * A data transfer object (DTO) representing the request for creating or
 * updating a note. This record encapsulates the title and content of the note.
 *
 * @param title   The title of the note.
 * @param content The content of the note.
 */
public record NoteRequestDto(String title, String content) {

}
