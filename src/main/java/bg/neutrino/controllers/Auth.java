package bg.neutrino.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bg.neutrino.AuthResult;
import bg.neutrino.GoogleUser;

@RestController
public class Auth {

	@RequestMapping(path = "/auth", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	AuthResult authSubmit(@RequestBody String idTokenString) {
		AuthResult result = new AuthResult();
		result.code = 0;
		result.message = "Logged in";
		GoogleUser gu = new GoogleUser();
		gu.setIdTokenString(idTokenString);
		if (!gu.login()) {
			result.code = 1;
			result.message = "Not logged in";
		}
		
		return result;
	}
}
