package bg.neutrino.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.neutrino.DeviceProperties;

@Controller
public class LoginView {
	@RequestMapping(path = "/login", method = RequestMethod.GET)
	String home(Model model) {
		DeviceProperties dp = new DeviceProperties();
		model.addAttribute("googleOAuth2ApiKey", dp.get().getProperty("google.oauth2.api.key"));
		return "login/index";
	}
}
