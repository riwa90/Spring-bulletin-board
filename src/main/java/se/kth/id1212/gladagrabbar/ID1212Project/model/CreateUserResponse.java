package se.kth.id1212.gladagrabbar.ID1212Project.model;

public class CreateUserResponse extends ResponseMessage {
	
	private String username;
	private String token;
	
	public CreateUserResponse() {
		super();
	}
	
	public CreateUserResponse(String message, int status, String username, String token) {
		super(message, status);
		this.username = username;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
