package se.kth.id1212.gladagrabbar.ID1212Project.model;

import java.util.List;

public class UserPayload {

	private String username;
	private List<PlacardBoard> boardsOwned;
	private List<PlacardBoard> boardsWhereIsMember;
	private List<Placard> placards;
	
	public UserPayload() {}

	public UserPayload(String username, List<Placard> placards, List<PlacardBoard> boardsOwned,
			List<PlacardBoard> boardsWhereIsMember) {
		this.username = username;
		this.placards = placards;
		this.boardsOwned = boardsOwned;
		this.boardsWhereIsMember = boardsWhereIsMember;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Placard> getPlacards() {
		return placards;
	}

	public void setPlacards(List<Placard> placards) {
		this.placards = placards;
	}

	public List<PlacardBoard> getBoardsOwned() {
		return boardsOwned;
	}

	public void setBoardsOwned(List<PlacardBoard> boardsOwned) {
		this.boardsOwned = boardsOwned;
	}

	public List<PlacardBoard> getBoardsWhereIsMember() {
		return boardsWhereIsMember;
	}

	public void setBoardsWhereIsMember(List<PlacardBoard> boardsWhereIsMember) {
		this.boardsWhereIsMember = boardsWhereIsMember;
	}
	
}
