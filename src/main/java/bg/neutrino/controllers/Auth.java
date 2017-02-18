package bg.neutrino.controllers;

import javax.servlet.http.HttpSession;

import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import bg.neutrino.AuthResult;
import bg.neutrino.Base;
import bg.neutrino.GoogleLogin;
import bg.neutrino.User;

@Controller
public class Auth extends Base {

	@RequestMapping(path = "/authGoogle", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	AuthResult authSubmit(@RequestBody String idTokenString, HttpSession httpSession) {
		AuthResult result = new AuthResult();
		result.code = 0;
		result.message = "Logged in";
		GoogleLogin gl = new GoogleLogin();
		gl.setIdTokenString(idTokenString);
		try {
			User user = gl.login();
			if (user.getId().equals("102233979970751918885")) {
				setSessionUser(httpSession, user);
			} else {
				throw new InvalidCredentialsException();
			}
		} catch (Exception e) {
			result.code = 1;
			result.message = "Not logged in";
		}
		return result;
	}
	
	@RequestMapping(path = "/logout", method = RequestMethod.GET)
	String logout(Model model, HttpSession httpSession) {
		User user = new User();
		setSessionUser(httpSession, user);
		model.addAttribute("user", user);
        return "home/index";
	}
}
