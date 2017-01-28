/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino.controllers;

import bg.neutrino.DeviceList;
import bg.neutrino.DeviceProperties;

import java.io.IOException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author atonkin
 */
@Controller
public class DevicesView {

    @RequestMapping(path = "/devices", method = RequestMethod.GET)
    String devices(Model model) throws IOException {
        model.addAttribute("page", "devices");
        model.addAttribute("devices", DeviceList.getAsArrayList());
        DeviceProperties dp = new DeviceProperties();
        model.addAttribute("googleMapsApiKey", dp.get().getProperty("google.maps.api.key"));
        return "devices/index";
    }

}
