package se.kth.id1212.gladagrabbar.ID1212Project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.kth.id1212.gladagrabbar.ID1212Project.model.ListResponse;
import se.kth.id1212.gladagrabbar.ID1212Project.model.PlacardBoard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.PlacardBoardResponse;
import se.kth.id1212.gladagrabbar.ID1212Project.model.Response;
import se.kth.id1212.gladagrabbar.ID1212Project.model.ResponseMessage;
import se.kth.id1212.gladagrabbar.ID1212Project.model.User;
import se.kth.id1212.gladagrabbar.ID1212Project.services.PlacardBoardService;
import se.kth.id1212.gladagrabbar.ID1212Project.util.Util;

@RestController
@RequestMapping("/api/placardboards")
public class PlacardBoardController implements ResponseController {

	@Autowired
	private PlacardBoardService placardBoardService;
	@Autowired
	private Util util;
	
	/********* Endpoint "/" ***************/
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<ListResponse<String>> getPlacardBoardIds() {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ListResponse<String>("OK", HttpStatus.OK.value(), placardBoardService.getPlacardBoardIds()));
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<ResponseMessage> createPlacardBoard(@RequestBody PlacardBoard placardBoard, 
			@RequestAttribute String username) {
		
		if(!validInput(placardBoard)) {
			return generateResponse(generateErrorMsg(placardBoard), HttpStatus.BAD_REQUEST);
		} else if(placardBoardService.placardExists(placardBoard.getName())) {
			return generateResponse(placardBoard.getName(), HttpStatus.CONFLICT);
		}
		
		User owner = new User();
		owner.setUsername(username);
		placardBoard.setOwner(owner);
		placardBoardService.createPlacardBoard(placardBoard);
		return generateResponse(placardBoard.getName(), HttpStatus.CREATED);
	}
	
	/********* Endpoint "/{id}" ***************/
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> getPlacardBoard(@PathVariable String id, @RequestAttribute String username) {
		PlacardBoard placardBoard = getPlacardBoardById(id);
		if(placardBoard == null) {
			return generateResponse(id, HttpStatus.NOT_FOUND);
		} else if(!placardBoard.getIsPublic() && !userIsMember(placardBoard, username)) {
			return generateResponse(id, HttpStatus.FORBIDDEN);
		}
		
		String owner = placardBoardService.getBoardOwner(id);
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new Response<PlacardBoardResponse>("OK", HttpStatus.OK.value(), 
						new PlacardBoardResponse(placardBoard, owner)));
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> deletePlacardBoard(@PathVariable String id, 
			@RequestAttribute(value="username") String username) {
		PlacardBoard placardBoardToDelete = getPlacardBoardById(id);
		if(placardBoardToDelete == null) {
			return generateResponse(id, HttpStatus.NOT_FOUND);
		} else if(!userIsOwner(username, placardBoardToDelete)) {
			return generateResponse(id, HttpStatus.FORBIDDEN);
		} else {
			placardBoardService.deletePlacardBoard(id);
			return generateResponse(id, HttpStatus.OK);
		}
	}
	
	/********* Endpoint "/{id}/members" ***************/
	
	@RequestMapping(value="/{id}/members", method=RequestMethod.GET)
	public ResponseEntity<?> getMembers(@PathVariable String id) {
		if(!placardBoardService.placardExists(id)) {
			return generateResponse(id, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(new ListResponse<String>("OK", HttpStatus.OK.value(), 
						placardBoardService.getPlacardMembers(id)));
	}
	
	@RequestMapping(value="/{id}/members", method=RequestMethod.POST)
	public ResponseEntity<ResponseMessage> addMember(@PathVariable String id, 
			@RequestBody User userToAdd, @RequestAttribute String username) {
		if(userToAdd.getUsername() == null) {
			return generateResponse("no username", HttpStatus.BAD_REQUEST);
		} else if (!util.userExists(userToAdd.getUsername())){
			return generateResponse(userToAdd.getUsername(), HttpStatus.NOT_FOUND);
		} else if(!placardBoardService.placardExists(id)) {
			return generateResponse(id, HttpStatus.NOT_FOUND);
		}
		
		PlacardBoard placardBoard = placardBoardService.getPlacardBoardById(id);
		if(!placardBoard.getOwner().getUsername().equals(username)) {
			return generateResponse(id, HttpStatus.FORBIDDEN);
		} else if(userIsMember(placardBoard, userToAdd.getUsername())){
			return generateResponse(userToAdd.getUsername(), HttpStatus.CONFLICT);
		}
		
		populateMembers(placardBoard);		
		placardBoard.addMember(userToAdd);
		placardBoardService.updatePlacardBoard(placardBoard);
		return generateResponse(id, HttpStatus.OK);
	}
	
	/********* Endpoint "/{id}/members/{memberid}" ***************/
	
	@RequestMapping(value="/{id}/members/{memberid}", method=RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> removeMember(@PathVariable String id, 
			@PathVariable String memberid, @RequestAttribute String username) {
		if(!placardBoardService.placardExists(id)) {
			return generateResponse(id, HttpStatus.NOT_FOUND);
		}
		
		PlacardBoard placardBoard = placardBoardService.getPlacardBoardById(id);
		if(!userIsOwner(username, placardBoard) && !memberid.equals(username)) {
			return generateResponse(id + " or " + memberid, HttpStatus.FORBIDDEN);
		} else {
			placardBoard.getMembers().removeIf(member -> member.getUsername().equals(memberid));
			placardBoardService.updatePlacardBoard(placardBoard);
			return generateResponse(id, HttpStatus.OK);
		}
	}
	
	/********* Privates ***************/
	
	private PlacardBoard getPlacardBoardById(String id) {
		return placardBoardService.getPlacardBoardById(id);
	}
	
	private void populateMembers(PlacardBoard board) {
		List<User> users = new ArrayList<>();
		placardBoardService
			.getPlacardMembers(board.getName())
			.forEach(userId -> users.add(createUser(userId)));
		board.setMembers(users);
	}
	
	private User createUser(String username) {
		User user = new User();
		user.setUsername(username);
		return user;
	}
	
	private boolean userIsMember(PlacardBoard board, String username) {
		if(board.getOwner().getUsername().equals(username)) {
			return true;
		}
		
		List<User> matchingUsers = board.getMembers();
		matchingUsers.removeIf(member -> !member.getUsername().equals(username));
		return !matchingUsers.isEmpty();
		
	}
	
	private boolean validInput(PlacardBoard board) {
		return hasCred(board) && !nameContainsSpace(board);
	}
	
	private String generateErrorMsg(PlacardBoard board) {
		if(!hasCred(board)) {
			return "fields name and isPublic needed";
		} else {
			return "boardnames can't contain spaces";
		}
	}
	
	private boolean hasCred(PlacardBoard board) {
		return board.getIsPublic() != null && board.getName() != null;
	}
	
	private boolean nameContainsSpace(PlacardBoard board) {
		return board.getName().contains(" ");
	}
	
	private boolean userIsOwner(String user, PlacardBoard board) {
		return board.getOwner().getUsername().equals(user);
	}
	
}
