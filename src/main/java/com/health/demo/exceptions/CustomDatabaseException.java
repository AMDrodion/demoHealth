package com.health.demo.exceptions;

public class CustomDatabaseException extends RuntimeException {
	public CustomDatabaseException(String message) {
		super(message);
	}
}
