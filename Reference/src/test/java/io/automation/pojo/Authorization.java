package io.automation.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Authorization {

	private String email;
	private String password;
	private boolean returnSecureToken;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isReturnSecureToken() {
		return returnSecureToken;
	}

	public void setReturnSecureToken(boolean returnSecureToken) {
		this.returnSecureToken = returnSecureToken;
	}

	public String getauthPayLoad(String username, String password) {
		Authorization auth = new Authorization();
		auth.setEmail(username);
		auth.setPassword(password);
		auth.setReturnSecureToken(true);

		ObjectMapper obj = new ObjectMapper();
		try {
			return obj.writeValueAsString(auth);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return "";
		}

	}
}
