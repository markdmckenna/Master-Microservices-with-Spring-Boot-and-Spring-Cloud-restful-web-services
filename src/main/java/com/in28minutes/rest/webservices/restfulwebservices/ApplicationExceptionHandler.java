package com.in28minutes.rest.webservices.restfulwebservices;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.in28minutes.rest.webservices.restfulwebservices.exception.ErrorResponse;
import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ UserNotFoundException.class })
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(RuntimeException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse(Arrays.asList(ex.getMessage()), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		ErrorResponse error = new ErrorResponse(LocalDateTime.now(), Arrays.asList(ex.getMessage()),
				request.getDescription(false));
		new ErrorResponse(LocalDateTime.now(), Arrays.asList(ex.getMessage()), request.getDescription(false));
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
	}

}
