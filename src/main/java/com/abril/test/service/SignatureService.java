package com.abril.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.abril.test.domain.Signature;
import com.abril.test.enumaration.ExceptionEnum;
import com.abril.test.exception.ErrorResponseException;
import com.abril.test.exception.Response;
import com.abril.test.repository.SignatureRepository;
import com.abril.test.request.SignatureRequest;

@Service
public class SignatureService {
	
	@Autowired
	private SignatureRepository repository;

	public List<Signature> find(SignatureRequest request){
		return repository.findByCepAndProduct(request.getCep(), request.getProduct()).orElseThrow(() -> new ErrorResponseException(
				Response.builder()
				.code(ExceptionEnum.NOT_FOUND.getId())
				.description(ExceptionEnum.NOT_FOUND.getDescription())
				.message(ExceptionEnum.NOT_FOUND.getDescription()).build(),
		HttpStatus.NOT_FOUND));
	}
}
