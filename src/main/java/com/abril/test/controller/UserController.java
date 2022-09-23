package com.abril.test.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abril.test.request.UserRequest;
import com.abril.test.response.UserResponse;
import com.abril.test.service.UserService;

@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

	@Autowired
	private UserService service;
    
    
    @PostMapping("/validate")
    public ResponseEntity<UserResponse> validate(@RequestBody UserRequest request) {
    	
    	return new ResponseEntity<>(service.validate(request), HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<List<UserResponse>> create(@RequestBody @Valid List<UserRequest> request) {
    	List<UserResponse> users = service.create(request);
    	
    	return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
}
