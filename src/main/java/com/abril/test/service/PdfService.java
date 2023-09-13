package com.abril.test.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abril.test.request.PdfRequest;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfService {
	
	@Autowired
	private GoogleDriveService googleDriveService;
	
	public void generatePdfFromImages(List<PdfRequest> request) {
		try {	
			googleDriveService.searchFile();
			//googleDriveService.downloadFile("abril01");
			//googleDriveService.listFolder();
			//generatePDFFromImage(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private static void generatePDFFromImage(List<PdfRequest> request)
			throws IOException, BadElementException, DocumentException {
		Document document = new Document();
		String output = "/tmp/teste_file.pdf";
		FileOutputStream fos = new FileOutputStream(output);
		PdfWriter writer = PdfWriter.getInstance(document, fos);
		writer.open();
		document.open();		
		
		for (PdfRequest r : request) {
			Image img = Image.getInstance((new URL(r.getUrl())));
			img.setAlignment(Element.ALIGN_CENTER);
			document.add(img);
		}
		document.close();
		writer.close();
	}
		
}
