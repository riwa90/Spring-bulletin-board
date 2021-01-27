package se.kth.id1212.gladagrabbar.ID1212Project.model;

public class Response<T> extends ResponseMessage {

	private T payload;
	
	public Response() {}
	
	public Response(String message, int status, T payload) {
		super(message, status);
		this.payload = payload;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

}
