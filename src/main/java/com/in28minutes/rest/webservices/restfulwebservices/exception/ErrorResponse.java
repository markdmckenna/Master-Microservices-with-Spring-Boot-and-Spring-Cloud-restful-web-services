package com.in28minutes.rest.webservices.restfulwebservices.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	private List<String> message;

	private String details;
	
	public ErrorResponse(List<String> message) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
	}

	public ErrorResponse(List<String> message, String requestDescription) {
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.details = requestDescription;
	}

}
