package bg.neutrino.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bg.neutrino.AuthResult;
import bg.neutrino.Base;
import bg.neutrino.GoogleLogin;
import bg.neutrino.User;

@RestController
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
			setSessionUser(httpSession, user);
		} catch (Exception e) {
			result.code = 1;
			result.message = "Not logged in";
		}
		return result;
	}
}
