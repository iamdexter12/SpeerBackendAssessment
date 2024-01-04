package com.speer.sharenotex.responsedto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object (DTO) representing the response for user login. This
 * class encapsulates information such as access token, expiration time, refresh
 * token, and refresh token expiration time.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

	/**
	 * The access token associated with the user's login session.
	 */
	@JsonProperty("access_token")
	private String accessToken;

	/**
	 * The expiration time (in seconds) of the access token.
	 */
	@JsonProperty("expires_in")
	private int expiresIn;

	/**
	 * The refresh token used for obtaining a new access token.
	 */
	@JsonProperty("refresh_token")
	private String refreshToken;

	/**
	 * The expiration time (in seconds) of the refresh token.
	 */
	@JsonProperty("refresh_expires_in")
	private int refreshExpiresIn;
}
