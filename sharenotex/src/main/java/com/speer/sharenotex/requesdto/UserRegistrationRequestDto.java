package com.speer.sharenotex.requesdto;

/**
 * A data transfer object (DTO) representing the request for user registration.
 * This record encapsulates the user's first name, last name, email, and password.
 *
 * @param firstName The first name of the user.
 * @param lastName  The last name of the user.
 * @param email     The email address of the user.
 * @param password  The password associated with the user's account.
 */
public record UserRegistrationRequestDto(String firstName, String lastName, String email, String password) {

}
