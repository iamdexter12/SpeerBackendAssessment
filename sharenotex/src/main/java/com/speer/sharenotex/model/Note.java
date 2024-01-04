package com.speer.sharenotex.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Note in the application.
 * Each Note has a unique identifier (ID), title, content, creation timestamp, update timestamp,
 * and a user identifier associated with it.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {

    /**
     * Unique identifier for the note.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Title of the note.
     */
    @NotBlank(message = "Title cannot be empty")
    private String title;

    /**
     * Content of the note.
     */
    @NotBlank(message = "Content cannot be empty")
    private String content;

    /**
     * Timestamp indicating the creation time of the note.
     */
    private Timestamp createdAt;

    /**
     * Timestamp indicating the last update time of the note.
     */
    private Timestamp updatedAt;

    /**
     * User identifier associated with the note.
     */
    private String userId;

}
