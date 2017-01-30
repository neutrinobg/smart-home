/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino.api.controllers;

import bg.neutrino.DeviceInfo;
import bg.neutrino.DeviceInterface;
import bg.neutrino.DeviceList;
import bg.neutrino.HS110;

import java.io.IOException;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    @RequestMapping(path = "/api/devices/{deviceId}", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    DeviceInterface getDeviceById(@PathVariable String deviceId, @RequestBody DeviceInfo deviceInfo) throws IOException {
    	if (DeviceList.get().get(deviceId) instanceof HS110) {
    		HS110 device = (HS110)DeviceList.get().get(deviceId);
			if (deviceInfo.getRelay_state() == 1) {
				device.switchOn();
			} else {
				device.switchOff();
			}
			if (deviceInfo.getLed_off() == 0) {
				device.nightModeOff();
			} else {
				device.nightModeOn();
			}
			return device;
    	}
    	return DeviceList.get().get(deviceId);
    }
}
