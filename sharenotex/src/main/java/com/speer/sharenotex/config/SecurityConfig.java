package com.speer.sharenotex.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security, enabling web security and
 * method-level security.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	/**
	 * Configures the security filter chain for the application.
	 *
	 * @param http The HttpSecurity object to configure.
	 * @return The configured SecurityFilterChain.
	 * @throws Exception If an error occurs during configuration.
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// Disable CSRF protection
		http.csrf(t -> t.disable());

		// Configure authorization rules
		http.authorizeHttpRequests(authorize -> {
			authorize.requestMatchers(HttpMethod.POST, "/api/auth/*").permitAll().requestMatchers(SWAGGER_PATH_ALLOWED)
					.permitAll().anyRequest().authenticated();
		});

		// Configure OAuth2 resource server with JWT authentication
		http.oauth2ResourceServer(t -> {
			t.jwt(Customizer.withDefaults());
		});

		// Configure session management to be stateless
		http.sessionManagement(t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}

	/**
	 * Provides a JwtDecoder bean for decoding JWT tokens.
	 *
	 * @return JwtDecoder bean configured with the JWK set URI.
	 */
	@Bean
	public JwtDecoder jwtDecoder() {
		return NimbusJwtDecoder.withJwkSetUri("http://localhost:8080/realms/Security/protocol/openid-connect/certs")
				.build();
	}

	/**
	 * Array of Swagger paths allowed without authentication.
	 */
	protected static final String[] SWAGGER_PATH_ALLOWED = { "/api/v1/auth/**", "/v2/api-docs", "/v3/api-docs",
			"/v3/api-docs/**", "/swagger-resources", "/swagger-resources/**", "/configuration/ui",
			"/configuration/security", "/swebjars/**", "swagger-ui/index.html", "swagger-ui/**", "/swagger-ui.html",
			"/v3/api-docs.yml" };
}
