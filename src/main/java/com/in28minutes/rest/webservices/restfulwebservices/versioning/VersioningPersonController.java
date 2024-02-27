package com.in28minutes.rest.webservices.restfulwebservices.versioning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.rest.webservices.restfulwebservices.user.UserController;

@RestController
public class VersioningPersonController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("V1/person")
	public PersonV1 getFirstVersionOfPerson() {
		LOGGER.info("getFirstVersionOfPerson");
		return new PersonV1("Mark McKenna");
	}
	
	@GetMapping("V2/person")
	public PersonV2 getSecondVersionOfPerson() {
		LOGGER.info("getSecondVersionOfPerson");
		return new PersonV2(new Name("Mark", "McKenna"));
	}
	
	@GetMapping(path = "/person", params = "version=1")
	public PersonV1 getFirstVersionOfPersonRequestParameter() {
		LOGGER.info("getFirstVersionOfPersonRequestParameter");
		return new PersonV1("Mark McKenna");
	}
	

	@GetMapping(path = "/person", params = "version=2")
	public PersonV2 getSecondVersionOfPersonRequestParameter() {
		LOGGER.info("getSecondVersionOfPersonRequestParameter");
		return new PersonV2(new Name("Mark", "McKenna"));
	}

}
