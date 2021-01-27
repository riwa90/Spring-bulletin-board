package se.kth.id1212.gladagrabbar.ID1212Project.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import se.kth.id1212.gladagrabbar.ID1212Project.model.ResponseMessage;

public interface ResponseController {
	
	default ResponseEntity<ResponseMessage> generateResponse(String id, 
			HttpStatus status) {

		String message = null;
		switch (status) {
			case FORBIDDEN:
				message = "Not authorized to access resource: ";
				break;
			case NOT_FOUND:
				message = "Resource not found: ";
				break;
			case CONFLICT:
				message = "Resource already exists: ";
				break;
			case BAD_REQUEST:
				message = "Incorrect request for resource: ";
				break;
			case OK:
				message = "Request successfully processed for resource: ";
				break;
			case CREATED:
				message = "Created resource: ";
				break;
			default:
				message = "Unknown httpstatus for resource: ";
				break;
		}
		message = message + id;
		return ResponseEntity.status(status).body(new ResponseMessage(message, status.value()));
		
	}

}
