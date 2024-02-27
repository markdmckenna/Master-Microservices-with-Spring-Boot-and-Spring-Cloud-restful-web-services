package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.exception.PostNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import com.in28minutes.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.jpa.UserRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/jpa/users")
public class UserJpaController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserJpaController.class);
	
	private UserRepository userRepository;
	
	private PostRepository postRepository;
	
	@GetMapping()
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userRepository.findAll();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUser(@PathVariable int id) {
		LOGGER.info("[IN]UserJpaController - getUser - id: {}", id);
		User user = UserJpaController.unwrapUser(userRepository.findById(id), id);
		LOGGER.info("[OUT]UserJpaController - getUser - user: {}", user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
		LOGGER.info("[IN]UserJpaController - saveUser - user: {}", user);
		User savedUser = userRepository.save(user);
		String location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(savedUser.getId())
				.toUriString().replace("http://localhost:8080", "");
		savedUser.setUri(URI.create(location));
		userRepository.save(savedUser);
//		return ResponseEntity.created(location).build();
		LOGGER.info("[OUT]UserJpaController - saveUser - savedUser: {}", savedUser);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		LOGGER.info("[IN]UserJpaController - deleteUser - id: {}", id);
		userRepository.deleteById(id);
		LOGGER.info("[OUT]UserJpaController - deleteUser");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("{id}/posts")
	public ResponseEntity<List<Post>> getPostsForUser(@PathVariable int id) {
		LOGGER.info("[IN]UserJpaController - getPostsForUser - id: {}", id);
		User user = UserJpaController.unwrapUser(userRepository.findById(id), id);
		List<Post> posts = user.getPosts();
		LOGGER.info("[OUT]UserJpaController - getPostsForUser - posts: {}", posts);
		return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	 
	@PostMapping("{id}/posts")
	public ResponseEntity<Post> createPostForUser(@Valid @RequestBody Post post, @PathVariable int id) {
		LOGGER.info("[IN]UserJpaController - createPostForUser - post: {} - id: {}", post, id);
		User user = UserJpaController.unwrapUser(userRepository.findById(id), id);
		post.setUser(user);
		post = postRepository.save(post);
		LOGGER.info("[OUT]UserJpaController - createPostForUser - post: {}", post);
		return new ResponseEntity<>(post, HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}/posts/{postId}")
	public ResponseEntity<Post> getUserPost(@PathVariable int userId, @PathVariable int postId) {
		LOGGER.info("[IN]UserJpaController - getUserPost - userId: {} - postId: {}", userId, postId);
		User user = UserJpaController.unwrapUser(userRepository.findById(userId), userId);
		Post post = unwrapPost(user.getPosts().stream().filter(p -> p.getId().equals(postId))
				.collect(Collectors.reducing((a, b) -> null)), postId);
		LOGGER.info("[PUT]UserJpaController - getUserPost - post: {}", post);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	static User unwrapUser(Optional<User> entity, int id) {
		if (entity.isPresent())
			return entity.get();
		else
			throw new UserNotFoundException(id);
	}
	
	static Post unwrapPost(Optional<Post> entity, int id) {
		if (entity.isPresent())
			return entity.get();
		else
			throw new PostNotFoundException(id);
	}

}
