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

DEVICES.init = function () {

};

DEVICES.getList = function () {
    var url = this.url;
    return jQuery.ajax({
        url: url
    });
};

DEVICES.getById = function (id) {
    var url = this.url + "/" + id;
    return jQuery.ajax({
        url: url
    });
};

DEVICES.setWindowFocusedEvent = function() {
    if (!this.windowsFocusedEventIsSet) {
        jQuery(window).focus(function () {
            //console.log('welcome (back)');
            DEVICES.windowsIsFocused = true;
            DEVICES.homeUpdate();
        });

        jQuery(window).blur(function () {
            //console.log('bye bye');
            DEVICES.windowsIsFocused = false;
            clearTimeout(DEVICES.timeoutID);
        });
        
        this.windowsFocusedEventIsSet = true;
    }
};

DEVICES.homeUpdate = function () {
    var that = this;
    var timeout = 5000;
    var i = 0;
    var n = 0;
    var waitTimeoutId;

    clearTimeout(that.timeoutID);
    
    waitTimeoutId = setTimeout(function() {
        jQuery('.wait-gif').removeClass('hidden');
    }, timeout);
    
    this.getList().done(function (data) {
        if (jQuery.isArray(data)) {
            n = data.length;
            jQuery.each(data, function (index, value) {
                //console.info(value);
                if (value.present) {
                    i++;
                }
            });
        }
        jQuery('#alertDiv').addClass('hidden');
    }).fail(function (jqXHR, textStatus, errorThrown) {
        jQuery('#alertDiv').text(textStatus + ", " + errorThrown).removeClass('hidden');
        //console.error(textStatus + ", " + errorThrown);
    }).always(function (jqXHR, textStatus) {
        jQuery('.devices-i').text(i);
        jQuery('.devices-n').text(n);
        if (typeof waitTimeoutId !== "undefined" && waitTimeoutId) {
            clearTimeout(waitTimeoutId);
            jQuery('.wait-gif').addClass('hidden');
        }
        if (that.windowsIsFocused) {
            that.timeoutID = setTimeout(jQuery.proxy(that.homeUpdate, that), timeout);
        }
        that.setWindowFocusedEvent();
    });
};

DEVICES.detailsUpdate = function () {
    var that = this;
    var timeout = 5000;

};
