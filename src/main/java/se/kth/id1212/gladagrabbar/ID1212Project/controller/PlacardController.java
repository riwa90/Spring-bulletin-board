package se.kth.id1212.gladagrabbar.ID1212Project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.kth.id1212.gladagrabbar.ID1212Project.model.Placard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.PlacardBoard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.Response;
import se.kth.id1212.gladagrabbar.ID1212Project.model.ResponseMessage;
import se.kth.id1212.gladagrabbar.ID1212Project.model.User;
import se.kth.id1212.gladagrabbar.ID1212Project.services.PlacardService;
import se.kth.id1212.gladagrabbar.ID1212Project.util.Util;

@RestController
@RequestMapping("/api/placards")
public class PlacardController implements ResponseController{
	
	@Autowired
	private PlacardService placardService;
	@Autowired
	private Util util;
	
	/********* Endpoint "/placardboard/{boardId}" ***************/
	//TODO: kOlla att man är medlem, kod finns redan i boardcontroller. lägg till util-klass och ta ut kod?
	@RequestMapping(value="/placardboard/{boardId}", method=RequestMethod.POST)
	public ResponseEntity<ResponseMessage> addPlacard(@RequestBody Placard placard, 
			@RequestAttribute(value="username") String username, @PathVariable String boardId) {
		
		if(!isValidPlacard(placard)) {
			return generateResponse("no text or header field given", HttpStatus.BAD_REQUEST);
		} else if (!isWithinSizeLimit(placard)) {
			return generateResponse("text limit 500 char, header 90 char", HttpStatus.BAD_REQUEST);
		} else if(!util.boardExists(boardId)) {
			return generateResponse(boardId, HttpStatus.NOT_FOUND);
		} else if(!util.isMember(boardId,  username)) {
			return generateResponse("not member of board " + boardId, HttpStatus.FORBIDDEN);
		}
		
		placard.setUser(createUserWithUsername(username));
		placard.setPlacardBoard(createPlacardBoardWithId(boardId));
		placardService.addPlacard(placard);
		return generateResponse("Placard in " + boardId, HttpStatus.CREATED);
	}
	
	/********* Endpoint "/{id}" ***************/
	//TODO: Samma som ovan, kolla att man är medlem/ägare så att man får se detta
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getPlacard(@PathVariable int id, @RequestAttribute String username) {
	
		Placard placard = getPlacardById(id);
		if(placard == null) {
			return generateResponse(String.valueOf(id), HttpStatus.NOT_FOUND);
		} else if(!util.canReadPlacard(placard, username)) {
			return generateResponse(String.valueOf(id), HttpStatus.FORBIDDEN);
		}
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new Response<Placard>("Placard found with id: " + id, HttpStatus.OK.value(), placard));
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updatePlacard(@PathVariable int id, 
			@RequestAttribute(value="username") String username, @RequestBody Placard placard) {
		
		Placard placardToUpdate = getPlacardById(id);
		if(placardToUpdate == null) {
			return generateResponse(String.valueOf(id), HttpStatus.NOT_FOUND);
		} else if(!placardToUpdate.getUser().equals(username)) {
			return generateResponse(String.valueOf(id), HttpStatus.FORBIDDEN);
		}
		
		if(placard.getHeader() != null) {
			placardToUpdate.setHeader(placard.getHeader());
		}
		if(placard.getText() != null) {
			placardToUpdate.setText(placard.getText());
		}
		placardService.updatePlacard(placardToUpdate);
		return generateResponse(String.valueOf(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> deletePlacard(@PathVariable int id, @RequestAttribute(value="username") String username) {
		
		Placard placardToDelete = getPlacardById(id);
		if(placardToDelete == null) {
			return generateResponse(String.valueOf(id), HttpStatus.NOT_FOUND);
			//TODO: Kolla ifall man äger boardet också
		} else if(!util.canDeletePlacard(placardToDelete, username)) {
			return generateResponse(String.valueOf(id), HttpStatus.FORBIDDEN);
		} 
		
		placardService.deletePlacard(id);
		return generateResponse(String.valueOf(id), HttpStatus.OK);
	}
	
	/********* Privates ***************/
	
	private Placard getPlacardById(int id) {
		return placardService.getPlacardById(id);
	}
	
	private User createUserWithUsername(String username) {
		User user = new User();
		user.setUsername(username);
		return user;
	}
	
	private PlacardBoard createPlacardBoardWithId(String id) {
		PlacardBoard placardBoard = new PlacardBoard();
		placardBoard.setName(id);
		return placardBoard;
	}
	
	private boolean isValidPlacard(Placard placard) {
		return placard.getHeader() != null && placard.getText() != null;
	}
	
	private boolean isWithinSizeLimit(Placard placard) {
		return placard.getHeader().length() < 90 && placard.getText().length() < 500;
	}
}
