package com.abril.test.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
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
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class SignatureService {
	
	private static final String JPG = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLec6j_BxT0wk8wuVC-uBGanFUNe4oz98B0DcYO5ZWdHr1KUo7kZQ2U2SFZqpSFzv7CJQ&usqp=CAU";
	
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
	
	public void generatePdf() {
		try {
			generatePDFFromImage(JPG, "jpg");
			
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}
	

	private static void generatePDFFromImage(String filename, String extension)
			throws IOException, BadElementException, DocumentException {
		Document document = new Document();
		String input = filename + "." + extension;
		String output = "/tmp/" + extension + ".pdf";
		FileOutputStream fos = new FileOutputStream(output);
		PdfWriter writer = PdfWriter.getInstance(document, fos);
		writer.open();
		document.open();
		document.add(Image.getInstance((new URL(input))));
		document.close();
		writer.close();
	}
		
}
