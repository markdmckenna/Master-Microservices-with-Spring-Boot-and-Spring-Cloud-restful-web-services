package com.in28minutes.rest.webservices.restfulwebservices;

import java.net.URI;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.in28minutes.rest.webservices.restfulwebservices.jpa.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.jpa.UserRepository;
import com.in28minutes.rest.webservices.restfulwebservices.user.Post;
import com.in28minutes.rest.webservices.restfulwebservices.user.User;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SpringBootApplication
public class RestfulWebServicesApplication implements CommandLineRunner {
	
	private UserRepository userRepository;
	
	private PostRepository postRepository;
	
	private static int userCount = 0;

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userRepository.save(new User(++userCount, "Adam JPA", LocalDate.now().minusYears(30), URI.create("/users/" + userCount), null));
		userRepository.save(new User(++userCount, "Eve JPA", LocalDate.now().minusYears(25), URI.create("/users/" + userCount), null));
		userRepository.save(new User(++userCount, "Jim JPA", LocalDate.now().minusYears(20), URI.create("/users/" + userCount), null));
		
		postRepository.save(new Post(1,  "This is POST 1", new User(1)));
		postRepository.save(new Post(2,  "This is POST 2", new User(1)));
		postRepository.save(new Post(3,  "This is POST 3", new User(2)));
		postRepository.save(new Post(4,  "This is POST 4", new User(2)));
	}
	


}
