package com.abril.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abril.test.domain.Signature;
import com.abril.test.request.SignatureRequest;
import com.abril.test.service.SignatureService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/v1/signatures")
@Api(tags = "Signature Controller")
public class SignatureController {

	@Autowired
	private SignatureService service;
    
    
	@GetMapping
    public ResponseEntity<List<Signature>> find(SignatureRequest request) {
    	List<Signature> signatures = service.find(request);
    	return (signatures != null) ? new ResponseEntity<>(signatures, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    
}
