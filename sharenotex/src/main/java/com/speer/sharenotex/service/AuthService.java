package com.speer.sharenotex.service;

import com.speer.sharenotex.requesdto.LoginRequestDto;
import com.speer.sharenotex.requesdto.UserRegistrationRequestDto;
import com.speer.sharenotex.responsedto.LoginResponseDto;

/**
 * Service interface defining authentication-related operations.
 */
public interface AuthService {

	/**
	 * Adds a new user based on the provided registration details.
	 *
	 * @param userRegistrationRequestDto DTO containing user registration details.
	 * @return True if the user is successfully added; false otherwise.
	 */
	Boolean addNewUser(UserRegistrationRequestDto userRegistrationRequestDto);

	/**
	 * Retrieves an access token based on the provided login details.
	 *
	 * @param loginRequestDto DTO containing user login details.
	 * @return LoginResponseDto containing information about the access token.
	 */
	LoginResponseDto getAccessToken(LoginRequestDto loginRequestDto);

}
