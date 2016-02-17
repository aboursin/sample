package sample.springsecurity.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Home controller.
 * @author angelo.boursin
 */
@Controller
@RequestMapping("/")
public class HomeController {

	/**
	 * Default view rendering.
	 * @return Redirection to home view
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String view() {
		return "home";
	}
	
	/**
	 * Web-service secured for role USER.
	 * @return Success message
	 */
	@Secured("ROLE_USER")
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> user(){
		return new ResponseEntity<String>("You're a USER", HttpStatus.OK);
	}
	
	/**
	 * Web-service secured for role ADMIN.
	 * @return Success message
	 */
	@Secured("ROLE_ADMIN")
	@RequestMapping(value="/admin", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> admin(){
		return new ResponseEntity<String>("You're an ADMIN", HttpStatus.OK);
	}
	
}
