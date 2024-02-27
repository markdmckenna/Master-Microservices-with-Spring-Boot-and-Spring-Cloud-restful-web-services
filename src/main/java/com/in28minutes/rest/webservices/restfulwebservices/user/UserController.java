package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	private UserDaoService userDaoService;

	@GetMapping()
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userDaoService.finaAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		LOGGER.info("[IN]UserDaoService - getUser - id: {}", id);
		User user = userDaoService.findOne(id);
		LOGGER.info("[OUT]UserDaoService - getUser - user: {}", user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
		LOGGER.info("[IN]UserDaoService - saveUser - user: {}", user);
		User savedUser = userDaoService.saveUser(user);
		String location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedUser.getId())
				.toUriString().replace("http://localhost:8080", "");
		savedUser.setUri(URI.create(location));
//		return ResponseEntity.created(location).build();
		LOGGER.info("[OUT]UserDaoService - saveUser - savedUser: {}", savedUser);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		LOGGER.info("[IN]UserDaoService - deleteUser - id: {}", id);
		userDaoService.deleteById(id);
		LOGGER.info("[OUT]UserDaoService - deleteUser");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

//	static User unwrapUser(Optional<User> entity, int id) {
//		if (entity.isPresent())
//			return entity.get();
//		else
//			throw new UserNotFoundException(id);
//	}

}
