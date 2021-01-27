package se.kth.id1212.gladagrabbar.ID1212Project.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class PlacardBoard {
	
	@Id
	@Column(name="name")
	private String name;
	@ManyToOne
	@JoinColumn(name="owner")
	private User owner;
	@ManyToMany
	@JoinTable(
			name="placardboardmember",
			joinColumns = { @JoinColumn(name="placardboard_name") },
			inverseJoinColumns = { @JoinColumn(name="username")}
	)
	private List<User> members;
	private Boolean isPublic;
	@OneToMany(mappedBy="placardBoard", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Placard> placards;
	
	public PlacardBoard() {
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@JsonBackReference(value="owner-boards")
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	@JsonIgnore
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
	@JsonManagedReference(value="board-placards")
	public List<Placard> getPlacards() {
		return placards;
	}
	public void setPlacards(List<Placard> placards) {
		this.placards = placards;
	}
	
	public void addMember(User newMember) {
		members.add(newMember);
	}
	public void removeMember(User formerMember) {
		members.removeIf(member -> 
			member.getUsername().equals(formerMember.getUsername())
			);
	}


}
