package com.abril.test.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.abril.test.domain.User;
import com.abril.test.enumaration.ExceptionEnum;
import com.abril.test.exception.ErrorResponseException;
import com.abril.test.exception.Response;
import com.abril.test.repository.UserRepository;
import com.abril.test.request.UserRequest;
import com.abril.test.response.UserResponse;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;

	public UserResponse validate(UserRequest request) {
		User user = repository.findByNameAndPassword(request.getName(), DigestUtils.sha256Hex(request.getPassword())).orElseThrow(() -> new ErrorResponseException(
				Response.builder()
				.code(ExceptionEnum.NOT_FOUND.getId())
				.description(ExceptionEnum.NOT_FOUND.getDescription())
				.message(ExceptionEnum.NOT_FOUND.getDescription()).build(),
		HttpStatus.NOT_FOUND));
		
		return UserResponse.builder()
				.id(user.getId())
				.name(user.getName())
				.build();
	}
	
	public List<UserResponse> create(List<UserRequest> users) {
		
		List<User> usersSaved = repository.saveAll(users
				.stream()
				.map(user-> User
						.builder()
						.name(user.getName())
						.password(DigestUtils.sha256Hex(user.getPassword()))
						.build())
				.collect(Collectors.toList()));
		
		return usersSaved.stream()
				.map(user-> UserResponse
						.builder()
						.id(user.getId())
						.name(user.getName())
						.build())
				.collect(Collectors.toList());
		
	}
}
