package com.abril.test.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    
    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(String param) throws IOException {
    	HttpHeaders headers = new HttpHeaders();
        
        service.generatePdf("teste_img_in_pdf_abril");
    	File file = new File("/tmp/teste_img_in_pdf_abril.pdf");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    
    
}
