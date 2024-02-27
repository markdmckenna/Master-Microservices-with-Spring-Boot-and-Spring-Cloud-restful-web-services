package com.in28minutes.rest.webservices.restfulwebservices.helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class HelloWorldController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldController.class);

	private HelloWorldBean helloWorldBean;

	@GetMapping("/helloWorld")
	public String helloWorld() {
		LOGGER.info("[IN]helloWorld");
		return "Hello World";
	}

	@GetMapping("/helloWorldBean")
	public HelloWorldBean helloBean() {
		LOGGER.info("[IN]HelloWorldController - helloBean - helloWorldBean: {}", helloWorldBean);
		return helloWorldBean;
	}

	@GetMapping("/helloWorldBean/{message}")
	public HelloWorldBean getMessageFromUri(@PathVariable String message) {
		LOGGER.info("[IN]HelloWorldController - getMessageFromUri - message: {}", message);
		helloWorldBean.setMessage(message);
		return helloWorldBean;
	}

	@PostMapping("/helloWorldBean/")
	public HelloWorldBean postHelloMessage(@RequestBody HelloWorldBean helloWorldBean) {
		LOGGER.info("[IN]HelloWorldController - postHelloMessage - helloWorldBean: {}", helloWorldBean);
		return helloWorldBean;
	}

}
