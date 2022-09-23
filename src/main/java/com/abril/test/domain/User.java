package com.abril.test.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import com.abril.test.request.UserRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = -1330120291666923843L;
	
	public User(UserRequest request) {
		this.name = request.getName();
		this.password = request.getPassword();
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank(message = "name cannot be empty")
	private String name;
	
	@NotBlank(message = "password cannot be empty")
	private String password;
	
	
	
}
