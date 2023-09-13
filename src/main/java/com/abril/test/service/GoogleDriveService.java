package com.abril.test.service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

@Service
public class GoogleDriveService {
	
	/**
	   * Application name.
	   */
	  private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	  /**
	   * Global instance of the JSON factory.
	   */
	  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	  /**
	   * Directory to store authorization tokens for this application.
	   */
	  private static final String TOKENS_DIRECTORY_PATH = "tokens";
	
	  /**
	   * Global instance of the scopes required by this quickstart.
	   * If modifying these scopes, delete your previously saved tokens/ folder.
	   */
	  private static final List<String> SCOPES =
	      Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
	  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	  
	  public void listFolder() throws IOException, GeneralSecurityException {
		    // Build a new authorized API client service.
		    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
		        .setApplicationName(APPLICATION_NAME)
		        .build();

		    // lista pastas do drive
		    FileList result = service.files().list()
		        .setPageSize(10)
		        .setQ("mimeType='application/vnd.google-apps.folder'")
		        .setFields("nextPageToken, files(id, name)")
		        .setSpaces("drive")
		        .execute();
		    List<File> files = result.getFiles();
		    if (files == null || files.isEmpty()) {
		      System.out.println("No files found.");
		    } else {
		      System.out.println("Files:");
		      for (File file : files) {
		        System.out.printf("%s (%s)\n", file.getName(), file.getId());
		      }
		    }
		  }
	  
	  /**
	   * Search for specific set of files.
	   *
	   * @return search result list.
	   * @throws IOException if service account credentials file not found.
	 * @throws GeneralSecurityException 
	   */
	  public static List<File> searchFile() throws IOException, GeneralSecurityException {
		// Build a new authorized API client service.
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	        .setApplicationName(APPLICATION_NAME)
	        .build();

	    List<File> files = new ArrayList<File>();

	    String pageToken = null;
	    do {
	      FileList result = service.files().list()
	          .setQ("name contains 'abril01'")
	          .setSpaces("drive")
	          .setPageToken(pageToken)
	          .execute();
	      for (File file : result.getFiles()) {
	        System.out.printf("Found file: %s (%s)\n",
	            file.getName(), file.getId());
	      }

	      files.addAll(result.getFiles());

	      pageToken = result.getNextPageToken();
	    } while (pageToken != null);

	    return files;
	  }
	  
	  /**
	   * Download a Document file in PDF format.
	   *
	   * @param realFileId file ID of any workspace document format file.
	   * @return byte array stream if successful, {@code null} otherwise.
	   * @throws IOException if service account credentials file not found.
	 * @throws GeneralSecurityException 
	   */
	  public static ByteArrayOutputStream downloadFile(String realFileId) throws IOException, GeneralSecurityException {
	        /* Load pre-authorized user credentials from the environment.
	           TODO(developer) - See https://developers.google.com/identity for
	          guides on implementing OAuth2 for your application.*/
		// Build a new authorized API client service.
	    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	        .setApplicationName(APPLICATION_NAME)
	        .build();

	    try {
	      OutputStream outputStream = new ByteArrayOutputStream();

	      service.files().get(realFileId)
	          .executeMediaAndDownloadTo(outputStream);

	      return (ByteArrayOutputStream) outputStream;
	    } catch (GoogleJsonResponseException e) {
	      // TODO(developer) - handle error appropriately
	      System.err.println("Unable to move file: " + e.getDetails());
	      throw e;
	    }
	  }

	  /**
	   * Creates an authorized Credential object.
	   *
	   * @param HTTP_TRANSPORT The network HTTP Transport.
	   * @return An authorized Credential object.
	   * @throws IOException If the credentials.json file cannot be found.
	   */
	  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
	      throws IOException {
	    // Load client secrets.
	    InputStream in = GoogleDriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	    if (in == null) {
	      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
	    }
	    GoogleClientSecrets clientSecrets =
	        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

	    // Build flow and trigger user authorization request.
	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
	        .setAccessType("offline")
	        .build();
	    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
	    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	    //returns an authorized Credential object.
	    return credential;
	  }
	
}
