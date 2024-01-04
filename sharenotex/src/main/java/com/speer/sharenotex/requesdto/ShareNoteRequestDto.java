package com.speer.sharenotex.requesdto;

/**
 * A data transfer object (DTO) representing the request for sharing a note with
 * another user. This record encapsulates the note ID and the user identifier
 * with whom the note is shared.
 *
 * @param noteId   The unique identifier of the note to be shared.
 * @param sharedTo The user identifier with whom the note is to be shared.
 */
public record ShareNoteRequestDto(Integer noteId, String sharedTo) {

}
