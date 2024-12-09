package com.health.demo.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ui.Model;
import org.springframework.dao.DataIntegrityViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public String handleDataIntegrityViolation(DataIntegrityViolationException ex, Model model) {
		model.addAttribute("errorMessage", ex.getRootCause() != null ? ex.getRootCause().getMessage() : "Ошибка при обработке данных.");
		return "error";
	}
}
