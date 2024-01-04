package com.speer.sharenotex.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response class representing error details, typically used for exception
 * handling. Utilizes Lombok annotations for simplified code.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private String fieldName;
	private String message;

}