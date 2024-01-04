package com.speer.sharenotex.requesdto;

/**
 * A data transfer object (DTO) representing the request for user login.
 * This record encapsulates the user's username and password.
 *
 * @param userName The username of the user attempting to log in.
 * @param password The password associated with the user's account.
 */
public record LoginRequestDto(String userName, String password) {

}
