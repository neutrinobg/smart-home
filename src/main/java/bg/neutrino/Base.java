package bg.neutrino;

import javax.servlet.http.HttpSession;

public class Base {
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
