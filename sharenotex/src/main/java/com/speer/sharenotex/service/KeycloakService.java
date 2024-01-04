package com.speer.sharenotex.service;

import org.keycloak.representations.idm.UserRepresentation;
import com.speer.sharenotex.requesdto.LoginRequestDto;
import com.speer.sharenotex.requesdto.UserRegistrationRequestDto;
import com.speer.sharenotex.responsedto.LoginResponseDto;

/**
 * Service interface defining operations related to interactions with Keycloak.
 */
public interface KeycloakService {

	/**
	 * Adds a new user to Keycloak based on the provided registration details.
	 *
	 * @param userRegistrationRequestDto DTO containing user registration details.
	 * @return True if the user is successfully added to Keycloak; false otherwise.
	 */
	Boolean addKeycloakUser(UserRegistrationRequestDto userRegistrationRequestDto);

	/**
	 * Retrieves an access token from Keycloak based on the provided login details.
	 *
	 * @param loginRequestDto DTO containing user login details.
	 * @return LoginResponseDto containing information about the access token.
	 */
	LoginResponseDto getAccessToken(LoginRequestDto loginRequestDto);

	/**
	 * Retrieves Keycloak user details based on the provided username.
	 *
	 * @param userName The username for which Keycloak user details are requested.
	 * @return UserRepresentation containing details about the Keycloak user.
	 */
	UserRepresentation getKeycloakUserDetailsById(String userName);

}
