/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @author atonkin
 */
public class DeviceList {

	private static HashMap<String, DeviceInterface> hmap = new HashMap<String, DeviceInterface>();

	public static HashMap<String, DeviceInterface> get() throws IOException {
		DeviceInterface device;
		DeviceProperties dp = new DeviceProperties();
		if (hmap.isEmpty()) {
			String[] deviceIPs = dp.get().getProperty("device.list").split("\\|");
			for (String deviceIP : deviceIPs) {
				device = new HS110(deviceIP);
				hmap.put(device.getInfo().get("deviceId"), device);
			}
		}
		return hmap;
	}

	public static ArrayList<DeviceInterface> getAsArrayList() throws IOException {
		DeviceList.get();
		ArrayList<DeviceInterface> deviceList = new ArrayList<DeviceInterface>();

		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			deviceList.add((DeviceInterface) mentry.getValue());
		}

		return deviceList;
	}
}
