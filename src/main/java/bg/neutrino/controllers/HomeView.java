/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author atonkin
 */
@Controller
public class HomeView {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    String home() {
        return "home/index";
    }

}
