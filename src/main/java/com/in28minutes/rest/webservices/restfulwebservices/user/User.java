package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "user_details")
public class User {
	
	public User(Integer id) {
		this.id = id;
	}

	@GeneratedValue
	@Id
	private Integer id;

	@NotBlank(message = "Name cannot be blank")
	//@JsonProperty("user_name")
	private String name;

	@Past(message = "The birth date must be in the past")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate birthDate;

	private URI uri;
	
	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Post> posts;
}
