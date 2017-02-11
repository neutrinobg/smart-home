/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino.controllers;

import bg.neutrino.Base;
import bg.neutrino.DeviceList;
import bg.neutrino.DeviceProperties;
import bg.neutrino.User;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author atonkin
 */
@Controller
public class DevicesView extends Base {

    @RequestMapping(path = "/devices", method = RequestMethod.GET)
    String devices(Model model, HttpSession httpSession) throws IOException {
    	User user = getSessionUser(httpSession);
    	model.addAttribute("user", user);
        model.addAttribute("page", "devices");
        model.addAttribute("devices", DeviceList.getAsArrayList());
        DeviceProperties dp = new DeviceProperties();
        model.addAttribute("googleMapsApiKey", dp.get().getProperty("google.maps.api.key"));
        return "devices/index";
    }

}
