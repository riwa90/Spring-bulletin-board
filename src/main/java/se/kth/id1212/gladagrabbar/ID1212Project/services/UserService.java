package se.kth.id1212.gladagrabbar.ID1212Project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import se.kth.id1212.gladagrabbar.ID1212Project.model.User;
import se.kth.id1212.gladagrabbar.ID1212Project.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}
	
	
	public User getUserById(String id) {
		Optional<User> userOptional = userRepository.findById(id);
		User fetchedUser = null;
		if(userOptional.isPresent()) {
			fetchedUser = userOptional.get();
		}
		return fetchedUser;
	}
	
	public void addUser(User user) {
		userRepository.save(user);
	}
	
	public void deleteUser(String id) {
		userRepository.deleteById(id);
	}
	
	public boolean userExists(String id) {
		return userRepository.existsById(id);
	}
}
