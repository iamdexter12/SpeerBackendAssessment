package com.speer.sharenotex.service.impl;

import java.util.Collections;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.speer.sharenotex.config.KeycloakConfig;
import com.speer.sharenotex.config.WebClientConfig;
import com.speer.sharenotex.exception.AlreadyExistException;
import com.speer.sharenotex.exception.KeycloakUserCreationFailException;
import com.speer.sharenotex.exception.NotFoundException;
import com.speer.sharenotex.requesdto.LoginRequestDto;
import com.speer.sharenotex.requesdto.UserRegistrationRequestDto;
import com.speer.sharenotex.responsedto.LoginResponseDto;
import com.speer.sharenotex.service.KeycloakService;
import com.speer.sharenotex.util.Constants;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of the {@link KeycloakService} interface that provides
 * operations for managing users in Keycloak.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {

	@Value("${com.keycloak.user.realm}")
	private String userRealm;
	@Value("${com.keycloak.config.clientId}")
	private String clientId;
	@Value("${com.keycloak.config.secret}")
	private String clientSecret;

	private final KeycloakConfig keycloakConfig;
	private final WebClientConfig webClientConfig;

	@Override
	public Boolean addKeycloakUser(UserRegistrationRequestDto userRegistrationRequestDto) {
		Keycloak keycloak = keycloakConfig.keycloakAdminClient();
		UsersResource usersResource = keycloak.realm(userRealm).users();
		UserRepresentation createUserRepresentation = createUserRepresentation(userRegistrationRequestDto);
		CredentialRepresentation credentials = setCredentials(userRegistrationRequestDto.password());

		createUserRepresentation.setCredentials(Collections.singletonList(credentials));
		boolean isUserCreated = false;
		try (Response response = usersResource.create(createUserRepresentation)) {
			if (response.getStatus() == 201) {
				String userId = CreatedResponseUtil.getCreatedId(response);
				UserResource userResource = usersResource.get(userId);
				UserRepresentation updatedUser = userResource.toRepresentation();
				updatedUser.setEnabled(true);
				userResource.update(updatedUser);
				RoleRepresentation userRole = findUserRoleByName(keycloak, Constants.ROLE_USER);
				userResource.roles().realmLevel().add(Collections.singletonList(userRole));
				isUserCreated = true;
				log.info("User '{}' created successfully.", userRegistrationRequestDto.email());
			} else if (response.getStatus() == 409) {
				throw new AlreadyExistException("email", Constants.EMAIL_ALREADY_EXIST_MESSAGE);
			} else if (response.getStatus() == 404) {
				throw new NotFoundException("realm", Constants.REALM_NOT_FOUND_MESSAGE);
			}
		} catch (WebApplicationException e) {
			throw new KeycloakUserCreationFailException("user", e.getMessage());
		}

		return isUserCreated;
	}

	@Override
	public UserRepresentation getKeycloakUserDetailsById(String userId) {
		Keycloak keycloak = keycloakConfig.keycloakAdminClient();
		UsersResource usersResource = keycloak.realm(userRealm).users();
		UserRepresentation userRepresentation = null;
		try {
			userRepresentation = usersResource.get(userId).toRepresentation();
			log.info("User details retrieved for user ID: {}", userId);
		} catch (NotFoundException e) {
			throw new NotFoundException(Constants.EXCEPTION_KEY_ID, Constants.NOT_FOUND_MESSAGE);
		}

		return userRepresentation;
	}

	private RoleRepresentation findUserRoleByName(Keycloak keycloak, String roleName) {
		return keycloak.realm(userRealm).roles().get(roleName).toRepresentation();
	}

	private UserRepresentation createUserRepresentation(UserRegistrationRequestDto userRegistrationRequestDto) {
		UserRepresentation newUser = new UserRepresentation();
		newUser.setUsername(userRegistrationRequestDto.email());
		newUser.setFirstName(userRegistrationRequestDto.firstName());
		newUser.setLastName(userRegistrationRequestDto.lastName());
		newUser.setEmail(userRegistrationRequestDto.email());
		newUser.setEmailVerified(true);
		newUser.setEnabled(true);
		return newUser;
	}

	private CredentialRepresentation setCredentials(String password) {
		CredentialRepresentation credentials = new CredentialRepresentation();
		credentials.setType(CredentialRepresentation.PASSWORD);
		credentials.setValue(password);
		credentials.setTemporary(false);
		return credentials;
	}

	@Override
	public LoginResponseDto getAccessToken(LoginRequestDto loginRequestDto) {
		MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
		formData.add(Constants.KEY_CLIENT_ID, clientId);
		formData.add(Constants.KEY_CLIENT_SECRET, clientSecret);
		formData.add(Constants.KEY_GRANT_TYPE, "password");
		formData.add(Constants.KEY_USER_NAME, loginRequestDto.userName());
		formData.add(Constants.KEY_PASSWORD, loginRequestDto.password());
		return webClientConfig.post(Constants.KEYCLOAK_ACCESS_TOKEN_URL, formData, LoginResponseDto.class);
	}
}
