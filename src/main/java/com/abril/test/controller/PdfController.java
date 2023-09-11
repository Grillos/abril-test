package com.abril.test.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abril.test.request.PdfRequest;
import com.abril.test.service.PdfService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/v1/pdfs")
@Api(tags = "Pdf Controller")
public class PdfController {

	@Autowired
	private PdfService service;
    
    
    @RequestMapping(path = "/generate", method = RequestMethod.POST)
    public ResponseEntity<Resource> generatePdfFromImages(@RequestBody @Valid List<PdfRequest> request) throws IOException {
    	HttpHeaders headers = new HttpHeaders();
        
        service.generatePdfFromImages(request);
    	File file = new File("/tmp/teste_file.pdf");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }
    
    
}
