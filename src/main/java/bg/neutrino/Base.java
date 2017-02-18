package bg.neutrino;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

public class Base {
	@Autowired
	public HttpSession httpSession;
	
	public User getSessionUser(HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user == null) {
			user = new User();			
		}
		return user;
	}
	
	public void setSessionUser(HttpSession httpSession, User user) {
		httpSession.setAttribute("user", user);
	}
}
