package com.speer.sharenotex.responsedto;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object (DTO) representing the response for a Note entity.
 * This class encapsulates information such as the note's unique identifier
 * (ID), title, content, creation timestamp, and update timestamp.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponseDto {

	/**
	 * The unique identifier of the note.
	 */
	private Integer id;

	/**
	 * The title of the note.
	 */
	private String title;

	/**
	 * The content of the note.
	 */
	private String content;

	/**
	 * The timestamp indicating when the note was created.
	 */
	private Timestamp createdAt;

	/**
	 * The timestamp indicating when the note was last updated.
	 */
	private Timestamp updatedAt;
}
