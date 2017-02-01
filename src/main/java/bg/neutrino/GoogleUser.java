/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

/**
 *
 * @author atonkin
 */
public class GoogleUser extends User {
	private String idTokenString;
	
	private static final HttpTransport transport = new NetHttpTransport();
	private static final JacksonFactory jacksonFactory = new JacksonFactory();

	public String getIdTokenString() {
		return idTokenString;
	}

	public void setIdTokenString(String idTokenString) {
		this.idTokenString = idTokenString;
	}

	public boolean login() {
		DeviceProperties dp = new DeviceProperties();
		String CLIENT_ID = dp.get().getProperty("google.oauth2.api.key");
		
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
				.setAudience(Collections.singletonList(CLIENT_ID))
				// Or, if multiple clients access the backend:
				// .setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2,
				// CLIENT_ID_3))
				.build();

		// (Receive idTokenString by HTTPS POST)
		System.out.println(idTokenString);

		GoogleIdToken idToken = null;
		try {
			idToken = verifier.verify(idTokenString);
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (idToken != null) {
			Payload payload = idToken.getPayload();

			// Print user identifier
			String userId = payload.getSubject();
			System.out.println("User ID: " + userId);

			// Get profile information from payload
			String email = payload.getEmail();
			boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			String name = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			String locale = (String) payload.get("locale");
			String familyName = (String) payload.get("family_name");
			String givenName = (String) payload.get("given_name");

			return true;

		} else {
			System.out.println("Invalid ID token.");
		}

		return false;
	}
}
