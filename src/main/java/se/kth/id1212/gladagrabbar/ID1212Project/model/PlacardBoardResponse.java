package se.kth.id1212.gladagrabbar.ID1212Project.model;

import java.util.List;

public class PlacardBoardResponse {

	private String name;
	private List<User> members;
	private Boolean isPublic;
	private List<Placard> placards;
	private String owner;
	
	public PlacardBoardResponse() {
		
	}
	
	public PlacardBoardResponse(PlacardBoard board, String owner) {
		this.name = board.getName();
		this.members = board.getMembers();
		this.isPublic = board.getIsPublic();
		this.placards = board.getPlacards();
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public List<Placard> getPlacards() {
		return placards;
	}

	public void setPlacards(List<Placard> placards) {
		this.placards = placards;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

}
