package se.kth.id1212.gladagrabbar.ID1212Project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Placard {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	@ManyToOne
	@JoinColumn(name="username")
	private User user;
	@ManyToOne
	@JoinColumn(name="placardboard_name")
	private PlacardBoard placardBoard;
	@Column(length=600)
	private String text;
	@Column(length=100)
	private String header;
	
	
	public Placard() {
	}
	
	public Placard(User user, String text, String header) {
		this.user = user;
		this.text = text;
		this.header = header;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getUser() {
		return user.getUsername();
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@JsonBackReference(value="board-placards")
	public PlacardBoard getPlacardBoard() {
		return placardBoard;
	}

	public void setPlacardBoard(PlacardBoard placardBoard) {
		this.placardBoard = placardBoard;
	}
	
}
