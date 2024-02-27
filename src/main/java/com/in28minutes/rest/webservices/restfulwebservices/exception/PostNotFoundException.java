package com.in28minutes.rest.webservices.restfulwebservices.exception;

public class PostNotFoundException extends RuntimeException {
	
	public PostNotFoundException(int id) {
        super("The post id '" + id + "' does not exist in our records");
    }

}
