package se.kth.id1212.gladagrabbar.ID1212Project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.kth.id1212.gladagrabbar.ID1212Project.model.PlacardBoard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.User;
import se.kth.id1212.gladagrabbar.ID1212Project.repositories.PlacardBoardRepository;

@Service
public class PlacardBoardService {
	
	@Autowired
	PlacardBoardRepository placardBoardRepository;
	
	public List<String> getPlacardBoardIds() {
		List<String> placardBoardIds = new ArrayList<>();
		placardBoardRepository.findAllIds().forEach(placardBoardIds::add);
		return placardBoardIds;
	}
	
	public List<PlacardBoard> getPlacardBoardsByUser(User user) {
		List<PlacardBoard> placardBoards = new ArrayList<>();
		placardBoardRepository.findByUser(user).forEach(placardBoards::add);
		return placardBoards;
	}
	
	public PlacardBoard getPlacardBoardById(String id) {
		PlacardBoard placardBoard = null;
		Optional<PlacardBoard> fetchedValue = placardBoardRepository.findById(id);
		if(fetchedValue.isPresent()) {
			placardBoard = fetchedValue.get();
		}
		return placardBoard;
	}
	
	public void deletePlacardBoard(String id) {
		placardBoardRepository.deleteById(id);
	}
	
	public void createPlacardBoard(PlacardBoard placardBoard) {
		placardBoardRepository.save(placardBoard);
	}
	
	public void updatePlacardBoard(PlacardBoard placardBoard) {
		createPlacardBoard(placardBoard);
	}
	
	public boolean placardExists(String id) {
		return placardBoardRepository.existsById(id);
	}
	
	public List<String> getPlacardMembers(String id) {
		List<String> placardBoardMembers = new ArrayList<>();
		placardBoardRepository.findAllMembers(id).forEach(member -> 
			placardBoardMembers.add(member.getUsername()));
		return placardBoardMembers;
	}
	
	public String getBoardOwner(String id) {
		String owner = null;
		Optional<User> fetchedValue = placardBoardRepository.findOwner(id);
		if(fetchedValue.isPresent()) {
			owner = fetchedValue.get().getUsername();
		}
		return owner;
	}
}
