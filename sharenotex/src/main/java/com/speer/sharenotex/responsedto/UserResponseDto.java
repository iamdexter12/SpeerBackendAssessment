package com.speer.sharenotex.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object (DTO) representing the response for a user entity.
 * This class encapsulates information such as the user's unique identifier
 * (ID), first name, last name, and email.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

	/**
	 * The unique identifier of the user.
	 */
	private String id;

	/**
	 * The first name of the user.
	 */
	private String firstName;

	/**
	 * The last name of the user.
	 */
	private String lastName;

	/**
	 * The email address of the user.
	 */
	private String email;

}
