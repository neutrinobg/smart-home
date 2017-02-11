/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import bg.neutrino.Base;
import bg.neutrino.User;

/**
 *
 * @author atonkin
 */
@Controller
public class HomeView extends Base{

    @RequestMapping(path = "/", method = RequestMethod.GET)
    String home(Model model, HttpSession httpSession) {
    	User user = getSessionUser(httpSession);
    	//System.out.println(httpSession.getId());
    	model.addAttribute("user", user);
        return "home/index";
    }

}
