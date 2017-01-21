/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino.api.controllers;

import bg.neutrino.DeviceInterface;
import bg.neutrino.DeviceList;
import java.io.IOException;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author atonkin
 */
@RestController
public class Devices {

    @RequestMapping(path = "/api/devices", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ArrayList<DeviceInterface> getDevices() throws IOException, InterruptedException {
        //Thread.sleep(7000);
        return DeviceList.getAsArrayList();
    }

    @RequestMapping(path = "/api/devices/{deviceId}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    DeviceInterface getDeviceById(@PathVariable String deviceId) throws IOException {
        return DeviceList.get().get(deviceId);
    }
}
