package se.kth.id1212.gladagrabbar.ID1212Project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.kth.id1212.gladagrabbar.ID1212Project.repositories.PlacardRepository;
import se.kth.id1212.gladagrabbar.ID1212Project.model.Placard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.PlacardBoard;
import se.kth.id1212.gladagrabbar.ID1212Project.model.User;

@Service
public class PlacardService {
	
	@Autowired
	private PlacardRepository placardRepository;
	
	//Kan komma att användas senare
	public List<Placard> getPlacardsByUser(User user) {
		List<Placard> placards = new ArrayList<>();
		placardRepository.findByUser(user).forEach(placards::add);
		return placards;
	}
	
	public Placard getPlacardById(int id) {
		Placard placard = null;
		Optional<Placard> fetched = placardRepository.findById(Integer.valueOf(id));
		if(fetched.isPresent()) {
			placard = fetched.get();
		}
		return placard;
	}
	
	public void addPlacard(Placard placard) {
		placardRepository.save(placard);
	}
	
	public void updatePlacard(Placard placard) {
		addPlacard(placard);
	}
	
	public void deletePlacard(int id) {
		placardRepository.deleteById(Integer.valueOf(id));
	}
	
	public PlacardBoard getBoardFromPlacard(int id) {
		return placardRepository.findBoard(id);
	}

}
