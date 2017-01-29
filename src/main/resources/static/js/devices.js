/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var DEVICES = {};

DEVICES.url = "/api/devices";

DEVICES.timeoutID;

DEVICES.windowsIsFocused = true;
DEVICES.windowsFocusedEventIsSet = false;

DEVICES.init = function() {

};

DEVICES.getList = function() {
	var url = this.url;
	return jQuery.ajax({
		url : url
	});
};

DEVICES.getById = function(id) {
	var url = this.url + "/" + id;
	return jQuery.ajax({
		url : url
	});
};

DEVICES.setWindowFocusedEvent = function(funcCall) {
	if (!this.windowsFocusedEventIsSet) {
		jQuery(window).focus(function() {
			// console.log('welcome (back)');
			DEVICES.windowsIsFocused = true;
			setTimeout(funcCall, 0);
		});

		jQuery(window).blur(function() {
			// console.log('bye bye');
			DEVICES.windowsIsFocused = false;
			clearTimeout(DEVICES.timeoutID);
		});

		this.windowsFocusedEventIsSet = true;
	}
};

DEVICES.homeUpdate = function() {
	var that = this;
	var timeout = 5000;
	var i = 0;
	var n = 0;
	var waitTimeoutId;

	clearTimeout(that.timeoutID);

	waitTimeoutId = setTimeout(function() {
		jQuery('.wait-gif').removeClass('hidden');
	}, timeout);

	this.getList().done(function(data) {
		if (jQuery.isArray(data)) {
			n = data.length;
			jQuery.each(data, function(index, value) {
				// console.info(value);
				if (value.present) {
					i++;
				}
			});
		}
		jQuery('#alertDiv').addClass('hidden');
	}).fail(function(jqXHR, textStatus, errorThrown) {
		jQuery('#alertDiv').text(textStatus + ", " + errorThrown)
				.removeClass('hidden');
		// console.error(textStatus + ", " + errorThrown);
	}).always(function(jqXHR, textStatus) {
		jQuery('.devices-i').text(i);
		jQuery('.devices-n').text(n);
		if (typeof waitTimeoutId !== "undefined" && waitTimeoutId) {
			clearTimeout(waitTimeoutId);
			jQuery('.wait-gif').addClass('hidden');
		}
		if (that.windowsIsFocused) {
			that.timeoutID = setTimeout(jQuery.proxy(that.homeUpdate,
					that), timeout);
		}
		that.setWindowFocusedEvent(jQuery.proxy(that.homeUpdate, that));
	});
};

DEVICES.detailsUpdate = function() {
	var that = this;
	var timeout = 5000;

	this.getList().done(function(data) {
		if (jQuery.isArray(data)) {
			jQuery.each(data, function(index, value) {
				// console.info(value);
				var contextSelector = "div[data-deviceId='" + value.info.deviceId + "']";
				
				// Actions
				jQuery('.device-alias', contextSelector).text(value.info.alias);
				var relayState = ((value.info.relay_state == "1") ? "ON" : "OFF")
				jQuery('.relay-state', contextSelector).text(relayState);
				if (relayState == "ON") {
					jQuery('.btn-switch-on-off', contextSelector)
					.removeClass('btn-default').addClass('btn-success');
				} else {
					jQuery('.btn-switch-on-off', contextSelector)
					.removeClass('btn-success').addClass('btn-default');
				}
				var ledOff = ((value.info.led_off == "0") ? "ON" : "OFF")
				jQuery('.led-off', contextSelector).text(ledOff);
				if (ledOff == "ON") {
					jQuery('.btn-led-on-off', contextSelector)
					.removeClass('btn-default').addClass('btn-success');
				} else {
					jQuery('.btn-led-on-off', contextSelector)
					.removeClass('btn-success').addClass('btn-default');
				}
				// Energy data
				jQuery('.now-power', contextSelector).text(value.energyData.nowPower);
				jQuery('.energy', contextSelector).text(value.energyData.energy);
				jQuery('.now-voltage', contextSelector).text(value.energyData.nowVoltage);
				jQuery('.now-current', contextSelector).text(value.energyData.nowCurrent);
				// Location
				var myLatLng = {
					lat : 0,
					lng : 0
				};
				myLatLng.lat = value.info.latitude;
				myLatLng.lng = value.info.longitude;
				var locText = "Coordinates: [" + myLatLng.lat + ", " + myLatLng.lng + "]";
				jQuery('.lat-lng', contextSelector).text(locText);
			});
		}
		jQuery('#alertDiv').addClass('hidden');
	}).fail(function(jqXHR, textStatus, errorThrown) {
		jQuery('#alertDiv').text(textStatus + ", " + errorThrown)
				.removeClass('hidden');
		// console.error(textStatus + ", " + errorThrown);
	}).always(function(jqXHR, textStatus) {
		if (typeof waitTimeoutId !== "undefined" && waitTimeoutId) {
			clearTimeout(waitTimeoutId);
			jQuery('.wait-gif').addClass('hidden');
		}
		if (that.windowsIsFocused) {
			that.timeoutID = setTimeout(jQuery.proxy(that.detailsUpdate,
					that), timeout);
		}
		that.setWindowFocusedEvent(jQuery.proxy(that.detailsUpdate, that));
	});
};
