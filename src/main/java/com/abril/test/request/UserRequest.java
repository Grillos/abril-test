package com.abril.test.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	
	@NotBlank(message = "name cannot be empty")
	private String name;
	
	@NotBlank(message = "password cannot be empty")
	private String password;
	

}
