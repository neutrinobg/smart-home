package bg.neutrino;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * TP-Link HS100
 *
 * @author Oliver Kleditzsch
 * @copyright Copyright (c) 2016, Oliver Kleditzsch
 */
public class HS100 implements DeviceInterface {

    public static final String COMMAND_SWITCH_ON = "{\"system\":{\"set_relay_state\":{\"state\":1}}}}";
    public static final String COMMAND_SWITCH_OFF = "{\"system\":{\"set_relay_state\":{\"state\":0}}}}";
    public static final String COMMAND_NIGHT_MODE_ON = "{\"system\":{\"set_led_off\":{\"off\":1}}}}";
    public static final String COMMAND_NIGHT_MODE_OFF = "{\"system\":{\"set_led_off\":{\"off\":0}}}}";
    public static final String COMMAND_INFO = "{\"system\":{\"get_sysinfo\":null}}";

    /**
     * Status an
     */
    public static final int STATE_ON = 1;

    /**
     * Status aus
     */
    public static final int STATE_OFF = 2;

    /**
     * IP Adresse der Steckdose
     */
    private String ip;

    /**
     * Port
     */
    private int port = 9999;

    /**
     * Present
     */
    private boolean present = false;

    /**
     * @param ip IP Adresse
     */
    public HS100(String ip) {
        this.ip = ip;
    }

    /**
     * @param ip IP Adresse
     * @param port TCP Port Nummer
     */
    public HS100(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * gibt die IP Adresse der Steckdose zurück
     *
     * @return IP Adresse
     */
    public String getIp() {
        return ip;
    }

    /**
     * setzt die IP Adresse der Steckdose
     *
     * @param ip IP Adresse
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * gibt den Port der Steckdose zurück
     *
     * @return Port
     */
    public int getPort() {
        return port;
    }

    /**
     * setzt den Port der Steckdose
     *
     * @param port Port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * prüft ob die Steckdose im Netzwerk erreichbar ist
     *
     * @return Erreichbarkeit
     */
    public boolean isPresent() {

        return isPresent(500);
    }

    /**
     * prüft ob die Steckdose im Netzwerk erreichbar ist
     *
     * @param timeout Timeout (Wartezeit in ms)
     * @return Erreichbarkeit
     */
    public boolean isPresent(int timeout) {

        try {
            InetAddress ip = InetAddress.getByName(getIp());
            present = ip.isReachable(timeout) && !"".equals(sendCommand(COMMAND_INFO));
        } catch (IOException ex) {
            present = false;
        }
        return present;
    }

    /**
     * sendet einen Einschaltbefehl
     *
     * @return true bei Erfolg
     */
    public boolean switchOn() throws IOException {

        String jsonData = sendCommand(COMMAND_SWITCH_ON);
        if (jsonData.length() > 0) {

            JsonObject jo = new JsonParser().parse(jsonData).getAsJsonObject();
            int errorCode = jo.get("system").getAsJsonObject().get("set_relay_state").getAsJsonObject().get("err_code").getAsInt();
            return errorCode == 0;
        }
        return false;
    }

    /**
     * sendet einen Ausschaltbefehl
     *
     * @return true bei Erfolg
     */
    public boolean switchOff() throws IOException {

        String jsonData = sendCommand(COMMAND_SWITCH_OFF);
        if (jsonData.length() > 0) {

            JsonObject jo = new JsonParser().parse(jsonData).getAsJsonObject();
            int errorCode = jo.get("system").getAsJsonObject().get("set_relay_state").getAsJsonObject().get("err_code").getAsInt();
            return errorCode == 0;
        }
        return false;
    }
    
    public boolean nightModeOn() throws IOException {
        String jsonData = sendCommand(COMMAND_NIGHT_MODE_ON);
        if (jsonData.length() > 0) {

            JsonObject jo = new JsonParser().parse(jsonData).getAsJsonObject();
            int errorCode = jo.get("system").getAsJsonObject().get("set_led_off").getAsJsonObject().get("err_code").getAsInt();
            return errorCode == 0;
        }
        return false;
    }
    
    public boolean nightModeOff() throws IOException {
        String jsonData = sendCommand(COMMAND_NIGHT_MODE_OFF);
        if (jsonData.length() > 0) {

            JsonObject jo = new JsonParser().parse(jsonData).getAsJsonObject();
            int errorCode = jo.get("system").getAsJsonObject().get("set_led_off").getAsJsonObject().get("err_code").getAsInt();
            return errorCode == 0;
        }
        return false;
    }

    /**
     * fragt den aktuellen Status der Steckodse ab
     *
     * @return STATE_ON oder STATE_OFF
     */
    public int readState() throws IOException {

        String jsonData = sendCommand(COMMAND_INFO);
        if (jsonData.length() > 0) {

            JsonObject jo = new JsonParser().parse(jsonData).getAsJsonObject();
            int state = jo.get("system").getAsJsonObject().get("get_sysinfo").getAsJsonObject().get("relay_state").getAsInt();
            return state == 1 ? STATE_ON : STATE_OFF;
        }
        return STATE_OFF;
    }

    /**
     * gibt eine Map mit den Systeminformationen der Steckdose zurück
     *
     * @return Map mit Systeminformationen
     */
    public Map<String, String> getInfo() throws IOException {

        Map<String, String> result = new HashMap<String, String>();
        String jsonData = sendCommand(COMMAND_INFO);
        if (jsonData.length() > 0) {

            JsonObject jo = new JsonParser().parse(jsonData).getAsJsonObject();
            JsonObject systemInfo = jo.get("system").getAsJsonObject().get("get_sysinfo").getAsJsonObject();
            for (Map.Entry<String, JsonElement> entry : systemInfo.entrySet()) {

                result.put(entry.getKey(), entry.getValue().getAsString());
            }
        }
        return result;
    }

    /**
     * sendet eine Befel an die Steckdose
     *
     * @param command Befehl
     * @return Json STring mit dem Antwortobjekt
     * @throws IOException
     */
    protected String sendCommand(String command) throws IOException {

        Socket socket = new Socket(ip, port);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(encryptWithHeader(command));

        String data = "";

        try {
            InputStream inputStream = socket.getInputStream();
            data = decrypt(inputStream);
            inputStream.close();
        } catch (Exception e) {
            present = false;
        } finally {
            outputStream.close();
            socket.close();
        }

        return data;
    }

    private String decrypt(InputStream inputStream) throws IOException {

        int in;
        int key = 0x2B;
        int nextKey;
        StringBuilder sb = new StringBuilder();
        while ((in = inputStream.read()) != -1) {

            nextKey = in;
            in = in ^ key;
            key = nextKey;
            sb.append((char) in);
        }
        return "{" + sb.toString().substring(5);
    }

    private int[] encrypt(String command) {

        int[] buffer = new int[command.length()];
        int key = 0xAB;
        for (int i = 0; i < command.length(); i++) {

            buffer[i] = command.charAt(i) ^ key;
            key = buffer[i];
        }
        return buffer;
    }

    private byte[] encryptWithHeader(String command) {

        int[] data = encrypt(command);
        byte[] bufferHeader = ByteBuffer.allocate(4).putInt(command.length()).array();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bufferHeader.length + data.length).put(bufferHeader);
        for (int in : data) {

            byteBuffer.put((byte) in);
        }
        return byteBuffer.array();
    }
}
