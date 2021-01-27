package se.kth.id1212.gladagrabbar.ID1212Project.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import se.kth.id1212.gladagrabbar.ID1212Project.model.CreateUserResponse;
import se.kth.id1212.gladagrabbar.ID1212Project.model.ListResponse;
import se.kth.id1212.gladagrabbar.ID1212Project.model.Response;
import se.kth.id1212.gladagrabbar.ID1212Project.model.ResponseMessage;
import se.kth.id1212.gladagrabbar.ID1212Project.model.User;
import se.kth.id1212.gladagrabbar.ID1212Project.model.UserPayload;
import se.kth.id1212.gladagrabbar.ID1212Project.services.AuthenticationService;
import se.kth.id1212.gladagrabbar.ID1212Project.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController implements ResponseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<ListResponse<String>> getUsers() {
		ArrayList<String> usernames = new ArrayList<>();
		userService.getAllUsers().forEach(user -> usernames.add(user.getUsername()));

		return ResponseEntity.status(HttpStatus.OK)
				.body(new ListResponse<String>(HttpStatus.OK.toString(), HttpStatus.OK.value(), usernames));
	}
	
	@RequestMapping(value="/users/{name}", method=RequestMethod.GET)
	public ResponseEntity<?> getUser(@PathVariable String name) {
		
		User user = userService.getUserById(name);
		if (user == null) {
			return generateResponse(name, HttpStatus.NOT_FOUND);
		}
		
		HttpStatus statusOk = HttpStatus.OK;
		UserPayload payload = new UserPayload(user.getUsername(), user.getPlacards(), user.getPlacardBoards(), 
				user.getPlacardBoardMemberships());
		return ResponseEntity.status(statusOk).body(new Response<UserPayload>(statusOk.toString(), statusOk.value(), payload));
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User newUser) {
		
		if (!isValidCred(newUser.getUsername(), newUser.getPassword())) {
			return  generateResponse(generateErrorMsg(newUser.getUsername(), newUser.getPassword()), HttpStatus.BAD_REQUEST);
		}
		
		if (userService.getUserById(newUser.getUsername()) != null) {
			/* big tech companies use status 200 in this case. 
			 see https://stackoverflow.com/questions/9269040/which-http-response-code-for-this-email-is-already-registered
			 */
			return generateResponse("Username not available", HttpStatus.OK);
		}
	
		userService.addUser(newUser);
		String jwtToken = authenticationService.authenticate(newUser.getUsername(), newUser.getPassword());
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new CreateUserResponse("New user created", HttpStatus.CREATED.value(), newUser.getUsername(), jwtToken));
	}
	
	@RequestMapping(value="/users/{name}", method=RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> deleteUser(@PathVariable String name, @RequestAttribute(value = "username") String authenticatedUser) {
		
		if (!authenticatedUser.equals(name)) {
			// code 400 because don't want to tell that the username exists for security reasons
			return generateResponse("Bad Request", HttpStatus.BAD_REQUEST);
		}
		
		userService.deleteUser(name);
		return generateResponse(name, HttpStatus.OK);
	}
	
	private String generateErrorMsg(String username, String password) {
		if(!haveCred(username, password)) {
			return "no username or password field given";
		} else if(!isValidUsername(username)) {
			return "Username must be be between 5-20 characters.";
		} else if(!isValidPassword(password)) {
			return "Password must be be between 5-20 characters.";
		}
		return "Error with parameters";
	}
	
	private boolean isValidCred(String username, String password) {
		return haveCred(username, password) && isValidUsername(username) && isValidPassword(password);
	}
	
	private boolean haveCred(String username, String password) {
		return username != null && password != null;
	}
	
	private boolean isValidUsername(String username) {
		return username.length() > 4 && username.length() < 21;
	}
	
	private boolean isValidPassword(String password) {
		return password.length() > 4 && password.length() < 21;
	}
}
