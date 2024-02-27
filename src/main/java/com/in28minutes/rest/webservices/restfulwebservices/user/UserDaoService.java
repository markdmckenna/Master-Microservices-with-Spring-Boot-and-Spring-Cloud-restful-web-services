package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;

import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<>();

	private static int userCount = 0;

	static {
		users.add(new User(++userCount, "Adam", LocalDate.now().minusYears(30), URI.create("/users/" + userCount), null));
		users.add(new User(++userCount, "Eve", LocalDate.now().minusYears(25), URI.create("/users/" + userCount), null));
		users.add(new User(++userCount, "Jim", LocalDate.now().minusYears(20), URI.create("/users/" + userCount), null));
	}

	public List<User> finaAll() {
		return users;
	}

	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		Optional<User> usersOptional = users.stream().filter(predicate).findFirst();
		return unwrapUser(usersOptional, id);
	}

	public User saveUser(User user) {
		user.setId(++userCount);
		users.add(user);
		return this.findOne(user.getId());
	}
	
	public void deleteById(int id) {
		users.remove(findOne(id));
	}
	
	static User unwrapUser(Optional<User> entity, int id) {
		if (entity.isPresent())
			return entity.get();
		else
			throw new UserNotFoundException(id);
	}

}
