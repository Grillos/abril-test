package com.abril.test.domain;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ErrorResponseException extends RuntimeException {

	private static final long serialVersionUID = -947909800477869008L;

	private final Response response;

	private final HttpStatus status;

	public ErrorResponseException() {
		this(null, null);
	}

	public ErrorResponseException(final Response response, final HttpStatus httpStatus) {
		super();
		this.response = response;
		this.status = httpStatus;
	}

}