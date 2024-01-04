package com.speer.sharenotex.service.impl;

import org.springframework.stereotype.Service;

import com.speer.sharenotex.requesdto.LoginRequestDto;
import com.speer.sharenotex.requesdto.UserRegistrationRequestDto;
import com.speer.sharenotex.responsedto.LoginResponseDto;
import com.speer.sharenotex.service.AuthService;
import com.speer.sharenotex.service.KeycloakService;

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the {@link AuthService} interface providing authentication-related operations.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final KeycloakService keycloakService;

	@Override
	public Boolean addNewUser(UserRegistrationRequestDto userRegistrationRequestDto) {
		return keycloakService.addKeycloakUser(userRegistrationRequestDto);

	}

	@Override
	public LoginResponseDto getAccessToken(LoginRequestDto loginRequestDto) {
		return keycloakService.getAccessToken(loginRequestDto);
	}

}
