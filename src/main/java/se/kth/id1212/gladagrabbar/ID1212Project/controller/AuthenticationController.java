package se.kth.id1212.gladagrabbar.ID1212Project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.kth.id1212.gladagrabbar.ID1212Project.model.AuthenticationRequest;
import se.kth.id1212.gladagrabbar.ID1212Project.model.AuthenticationResponse;
import se.kth.id1212.gladagrabbar.ID1212Project.model.ResponseMessage;
import se.kth.id1212.gladagrabbar.ID1212Project.services.AuthenticationService;

@RestController
@RequestMapping("/api/")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) throws Exception {
		String username = request.getUsername();
		String password = request.getPassword();
		
		try {
			String jwtToken = authenticationService.authenticate(username, password);
			return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
			
		} catch (BadCredentialsException | UsernameNotFoundException e) {
			return ResponseEntity.status(401).body(new ResponseMessage("Bad credentials"));
		}
	}
}
