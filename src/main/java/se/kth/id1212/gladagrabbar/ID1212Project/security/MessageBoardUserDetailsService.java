package se.kth.id1212.gladagrabbar.ID1212Project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import se.kth.id1212.gladagrabbar.ID1212Project.model.User;
import se.kth.id1212.gladagrabbar.ID1212Project.services.UserService;

@Service
public class MessageBoardUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails user = null;
		user = userService.getUserById(username);
		if(user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return user;
		//return new User("banan", "banan");
	}

}
