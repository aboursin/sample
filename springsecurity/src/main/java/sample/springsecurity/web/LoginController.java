package sample.springsecurity.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import sample.springsecurity.configuration.SecurityConfiguration;

/**
 * Login controller.
 * @author angelo.boursin
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	
	/**
	 * Default view rendering.
	 * @param error Error marker set by {@link SecurityConfiguration}
	 * @param logout Logout marker set by {@link SecurityConfiguration}
	 * @return Redirection to login view
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String view(ModelMap model, @RequestParam(required = false) String error, @RequestParam(required = false) String logout) {
		
		if (error != null) {
            model.addAttribute("error", "Invalid Credentials provided.");
        }
        if (logout != null) {
            model.addAttribute("message", "Logged out successfully.");
        }
        
		return "login";
	}

}
