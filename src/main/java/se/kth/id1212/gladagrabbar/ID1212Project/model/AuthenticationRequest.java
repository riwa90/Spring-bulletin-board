package se.kth.id1212.gladagrabbar.ID1212Project.model;

public class AuthenticationRequest {
	
	private String username;
	private String password;
	
	public AuthenticationRequest() {}
	
	public AuthenticationRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
