package com.phoenix.botly.dao;

public class SMS {
    private String wakeCode;
    private String wifiCode;
    private String locationCode;
    private String phoneCallCode;


    public String getWakeCode() {
        return wakeCode;
    }

    public void setWakeCode(String wakeCode) {
        this.wakeCode = wakeCode;
    }


    public String getWifiCode() {
        return wifiCode;
    }

    public void setWifiCode(String wifiCode) {
        this.wifiCode = wifiCode;
    }


    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }


    public String getPhoneCallCode() {
        return phoneCallCode;
    }

    public void setPhoneCallCode(String phoneCallCode) {
        this.phoneCallCode = phoneCallCode;
    }
}
