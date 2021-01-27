package se.kth.id1212.gladagrabbar.ID1212Project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import se.kth.id1212.gladagrabbar.ID1212Project.security.JwtUtil;
import se.kth.id1212.gladagrabbar.ID1212Project.security.MessageBoardUserDetailsService;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MessageBoardUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	public String authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		String jwtToken = jwtUtil.generateToken(userDetails);
		return jwtToken;
	}
}
