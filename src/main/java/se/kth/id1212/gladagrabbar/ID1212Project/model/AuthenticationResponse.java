package se.kth.id1212.gladagrabbar.ID1212Project.model;

public class AuthenticationResponse {
	
	private final String jwtToken;
	
	public AuthenticationResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	
	public String getJwtToken() {
		return jwtToken;
	}
	
}
