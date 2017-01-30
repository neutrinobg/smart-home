package bg.neutrino;

public class DeviceInfo {
	private int relay_state;
	private int led_off;

	public int getRelay_state() {
		return relay_state;
	}

	public void setRelay_state(int relay_state) {
		this.relay_state = relay_state;
	}

	public int getLed_off() {
		return led_off;
	}

	public void setLed_off(int led_off) {
		this.led_off = led_off;
	}
}
