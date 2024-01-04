package com.speer.sharenotex.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.speer.sharenotex.exception.NotFoundException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * Configuration class for creating WebClient to make reactive HTTP requests.
 */
@Configuration
@Slf4j
public class WebClientConfig {

	private String token;

	/**
	 * Setter method for setting the OAuth token.
	 * 
	 * This method is used to set the OAuth token that will be included in the
	 * headers of outgoing requests made by the WebClient.
	 * 
	 * @param token The OAuth token to be set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Creates and returns an ObjectMapper bean.
	 * 
	 * This ObjectMapper bean is used for JSON serialization and deserialization.
	 * 
	 * @return ObjectMapper instance
	 */
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	/**
	 * Creates a WebClient builder for making reactive HTTP requests.
	 * 
	 * @return WebClient.Builder instance
	 */
	@Bean
	public WebClient.Builder webClientBuilder() {
		return WebClient.builder().defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
				.filter(oauthTokenFilter());
	}

	/**
	 * Creates a WebClient for making reactive HTTP requests.
	 * 
	 * @return WebClient instance
	 */
	@Bean
	public WebClient webclient() {
		return webClientBuilder().build();
	}

	/**
	 * Creates an ExchangeFilterFunction for adding OAuth token to the request
	 * headers.
	 * 
	 * This function is used as a filter in the WebClient to include the OAuth token
	 * in the headers of outgoing requests.
	 * 
	 * @return ExchangeFilterFunction instance for processing requests
	 */
	private ExchangeFilterFunction oauthTokenFilter() {
		return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
			// Add OAuth token to the request headers
			ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
					.headers(headers -> headers.setBearerAuth(token)).build();
			return Mono.just(authorizedRequest);
		});
	}

	/**
	 * Performs a GET request to the specified URL and retrieves the response as the
	 * given response type.
	 * 
	 * @param url          URL to send the GET request to
	 * @param responseType Class representing the type of the response
	 * @param <T>          Type of the response
	 * @return Response of the specified type
	 */
	public <T> T get(String url, Class<T> responseType) {
		return webclient().get().uri(url).retrieve().bodyToMono(responseType).block();
	}

	/**
	 * Performs a GET request to the specified URL and retrieves the response as a
	 * list of the given response type.
	 * 
	 * @param url          URL to send the GET request to
	 * @param responseType TypeReference representing the type of the response list
	 * @param <T>          Type of the response
	 * @return List of responses of the specified type
	 */
	public <T> List<T> getList(String url, TypeReference<List<T>> responseType) {
		String jsonResponse = webclient().get().uri(url).retrieve().bodyToMono(String.class).block();
		return parseJsonResponse(jsonResponse, responseType);
	}

	/**
	 * Performs a POST request to the specified URL with the given request body and
	 * retrieves the response as the given response type.
	 * 
	 * @param url          URL to send the POST request to
	 * @param requestBody  Request body to include in the POST request
	 * @param responseType Class representing the type of the response
	 * @param <T>          Type of the response
	 * @return Response of the specified type
	 */
	public <T> T post(String url, Object requestBody, Class<T> responseType) {
		return webclient().post().uri(url).body(BodyInserters.fromValue(requestBody)).retrieve()
				.bodyToMono(responseType).onErrorResume(this::handleException).block();
	}

	/**
	 * Parses the JSON response into a list of the given response type.
	 * 
	 * @param jsonResponse JSON response as a string
	 * @param responseType TypeReference representing the type of the response list
	 * @param <T>          Type of the response
	 * @return List of responses of the specified type
	 */
	private <T> List<T> parseJsonResponse(String jsonResponse, TypeReference<List<T>> responseType) {
		List<T> responseList = new ArrayList<>();
		try {
			JsonNode jsonNode = objectMapper().readTree(jsonResponse).get("value");
			responseList = objectMapper().convertValue(jsonNode, responseType);
		} catch (JsonProcessingException e) {
			log.error("Error mapping JSON response to List: {}", e.getMessage());
		}
		return responseList;
	}

	private <T> Mono<T> handleException(Throwable throwable) {
		if (throwable instanceof WebClientResponseException webClientResponseException) {
			return handleWebClientResponseException(webClientResponseException);
		} else {
			return Mono.error(throwable);
		}
	}

	private <T> Mono<T> handleWebClientResponseException(WebClientResponseException ex) {
		int statusCode = ex.getStatusCode().value();
		if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			return Mono.error(new NotFoundException("id", "Id dont exist"));
		} else if (statusCode == HttpStatus.NOT_FOUND.value()) {
			return Mono.error(new NotFoundException("resource", "Not Found"));
		} else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
			return Mono.error(new NotFoundException("resource", "Inavlid credentials"));
		}
		return Mono.error(ex);
	}
}
