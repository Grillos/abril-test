package com.abril.test.service;

import java.io.FileOutputStream;
import java.io.IOException;
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
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class SignatureService {
	
	private static final String JPG_1 = "/home/robson/Imagens/abril01.png";
	private static final String JPG_2 = "/home/robson/Imagens/abril02.png";
	private static final String JPG_3 = "/home/robson/Imagens/abril03.png";

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
	
	public void generatePdf(String fileName) {
		try {
			generatePDFFromImage(fileName);
			
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}
	

	private static void generatePDFFromImage(String filename)
			throws IOException, BadElementException, DocumentException {
		Document document = new Document();
		String output = "/tmp/" + filename + ".pdf";
		FileOutputStream fos = new FileOutputStream(output);
		PdfWriter writer = PdfWriter.getInstance(document, fos);
		writer.open();
		document.open();		
		
		Image img1 = Image.getInstance(JPG_1);
		img1.setAlignment(Element.ALIGN_CENTER);
		
		Image img2 = Image.getInstance(JPG_2);
		img2.setAlignment(Element.ALIGN_CENTER);
		
		Image img3 = Image.getInstance(JPG_3);
		img3.setAlignment(Element.ALIGN_CENTER);
		
		document.add(img1);
		document.add(img2);
		document.add(img3);
		document.close();
		writer.close();
	}
		
}
