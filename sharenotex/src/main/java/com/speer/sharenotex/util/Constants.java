package com.speer.sharenotex.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

	public static final String KEY_USER_NAME = "username";
	public static final String KEY_GRANT_TYPE = "grant_type";
	public static final String KEY_CLIENT_SECRET = "client_secret";
	public static final String KEY_CLIENT_ID = "client_id";
	public static final String KEY_PASSWORD = "password";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String REFRESH_TOKEN = "refresh_token";
	public static final String USER_REGISTER_SUCCESS = "User registered successfully!";
	public static final String USER_REGISTER_FAIL = "User registeration failed!";
	public static final String NOTE_ADDED_SUCCESS = "Note added successfully";
	public static final String ROLE_USER = "USER";
	public static final String REALM_NOT_FOUND_MESSAGE = "Realm not found";
	public static final String EMAIL_ALREADY_EXIST_MESSAGE = "User already exists with this email";
	public static final String NOT_FOUND_MESSAGE = "Not found";
	public static final String KEYCLOAK_ACCESS_TOKEN_URL = "http://localhost:8080/realms/Security/protocol/openid-connect/token";
	public static final String UPDATE_SUCCESS = "Note updated successfully";
	public static final String DELETE_SUCCESS = "Note deleted successfully";
	public static final String SHARED_SUCCESS = "Note shared successfully";
	public static final String EXCEPTION_KEY_ID = "id";

}