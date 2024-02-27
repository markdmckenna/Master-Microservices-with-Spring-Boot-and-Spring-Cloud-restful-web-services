package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Component
public class HelloWorldBean {

	private String message;

	public HelloWorldBean() {
		super();
		this.message = "Hello World from the HelloWorldBean's Constructor";
	}

}
