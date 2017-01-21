/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino;

import java.io.IOException;
import java.util.Map;

/**
 *
 * @author atonkin
 */
public interface DeviceInterface {

    public Map<String, String> getInfo() throws IOException;
}
