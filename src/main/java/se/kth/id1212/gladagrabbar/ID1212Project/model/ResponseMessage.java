package se.kth.id1212.gladagrabbar.ID1212Project.model;

public class ResponseMessage {

	private int status;
	private String message;
	
	public ResponseMessage() {}
	
	public ResponseMessage(String message) {
		this.message = message;
	}
	
	public ResponseMessage(String message, int status) {
		this.message = message;
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
