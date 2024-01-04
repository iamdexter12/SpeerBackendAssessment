package com.speer.sharenotex.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for integrating Keycloak authentication within the
 * application. Provides a Spring bean for creating a Keycloak admin client to
 * interact with the Keycloak server.
 */
@Configuration
public class KeycloakConfig {

	// Keycloak server URL configuration property
	@Value("${com.keycloak.server.url}")
	private String keycloakServerUrl;

	// Keycloak master realm configuration property
	@Value("${com.keycloak.master.realm}")
	private String keycloakMasterRealm;

	// Keycloak master client ID configuration property
	@Value("${com.keycloak.master.clientId}")
	private String keycloakMasterClientId;

	// Keycloak admin username configuration property
	@Value("${com.keycloak.admin.username}")
	private String adminUsername;

	// Keycloak admin password configuration property
	@Value("${com.keycloak.admin.password}")
	private String adminPassword;

	// Keycloak user realm configuration property
	@Value("${com.keycloak.user.realm}")
	private String userRealm;

	/**
	 * Creates and configures a Keycloak admin client bean for interacting with the
	 * Keycloak server.
	 *
	 * @return Keycloak admin client bean configured based on the specified
	 *         properties.
	 */
	@Bean
	public Keycloak keycloakAdminClient() {
		return KeycloakBuilder.builder().serverUrl(keycloakServerUrl).realm(keycloakMasterRealm)
				.clientId(keycloakMasterClientId).grantType(OAuth2Constants.PASSWORD).username(adminUsername)
				.password(adminPassword).build();
	}

}
