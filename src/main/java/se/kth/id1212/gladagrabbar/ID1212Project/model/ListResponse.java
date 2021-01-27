package se.kth.id1212.gladagrabbar.ID1212Project.model;

import java.util.List;

public class ListResponse<T> extends ResponseMessage {
	
	private List<T> payload;
	
	public ListResponse() {}
	
	public ListResponse(String message, int status, List<T> payload) {
		super(message, status);
		this.payload = payload;
	}

	public List<T> getPayload() {
		return payload;
	}

	public void setPayload(List<T> payload) {
		this.payload = payload;
	}

}
