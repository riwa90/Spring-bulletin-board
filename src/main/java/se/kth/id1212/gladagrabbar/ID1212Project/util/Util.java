package se.kth.id1212.gladagrabbar.ID1212Project.util;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.kth.id1212.gladagrabbar.ID1212Project.model.Placard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.PlacardBoard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.User;
import se.kth.id1212.gladagrabbar.ID1212Project.services.PlacardBoardService;
import se.kth.id1212.gladagrabbar.ID1212Project.services.PlacardService;
import se.kth.id1212.gladagrabbar.ID1212Project.services.UserService;

@Service
public class Util {
	
	@Autowired
	PlacardBoardService boardService;
	@Autowired
	PlacardService placardService;
	@Autowired
	UserService userService;
	
	public boolean userExists(String id) {
		return userService.userExists(id);
	}
	
	public boolean canDeletePlacard(Placard placard, String username) {
		PlacardBoard board = placardService.getBoardFromPlacard(placard.getId());
		return placard.getUser().equals(username) || board.getOwner().getUsername().equals(username);
	}
	
	public boolean canReadPlacard(Placard placard, String username) {
		PlacardBoard board = placardService.getBoardFromPlacard(placard.getId());
		return isMember(board.getName(), username);
	}

	public boolean isMember(String boardId, String userId) {
		PlacardBoard board = boardService.getPlacardBoardById(boardId);
		if(board.getOwner().getUsername().equals(userId)) {
			return true;
		}
		
		List<User> matchingUsers = board.getMembers();
		matchingUsers.removeIf(member -> !member.getUsername().equals(userId));
		return !matchingUsers.isEmpty();
	}
	
	public boolean boardExists(String boardId) {
		return boardService.placardExists(boardId);
	}
	
}
