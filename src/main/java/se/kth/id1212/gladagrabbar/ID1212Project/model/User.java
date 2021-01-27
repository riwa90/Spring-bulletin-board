package se.kth.id1212.gladagrabbar.ID1212Project.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="account")
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="name")
	private String username;
	private String password;
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Placard> placards;
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<PlacardBoard> placardBoards;
	@ManyToMany(mappedBy="members", cascade=CascadeType.ALL)
	private List<PlacardBoard> placardBoardMemberships;
	
	public User() {
		
	}
	
	public User(String name, String password) {
		this.username = name;
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return new ArrayList<>();
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	//@JsonManagedReference(value="user-placards")
	@JsonIgnore
	public List<Placard> getPlacards() {
		return placards;
	}

	public void setPlacards(List<Placard> placards) {
		this.placards = placards;

	}

	@JsonManagedReference(value="owner-boards")
	public List<PlacardBoard> getPlacardBoards() {
		return placardBoards;
	}

	public void setPlacardBoards(List<PlacardBoard> placardBoards) {
		this.placardBoards = placardBoards;
	}

	public List<PlacardBoard> getPlacardBoardMemberships() {
		return placardBoardMemberships;
	}

	public void setPlacardBoardMemberships(List<PlacardBoard> placardBoardMemberships) {
		this.placardBoardMemberships = placardBoardMemberships;
	}
	
	public void addPlacardBoardMembership(PlacardBoard placardBoard) {
		placardBoardMemberships.add(placardBoard);
	}
	
}
