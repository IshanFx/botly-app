package com.phoenix.botly;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import com.phoenix.botly.dao.SMS;

public class AppSession {
    SharedPreferences preferences;
    Context context;
    SharedPreferences.Editor editor;


    public AppSession(Context context) {
        this.context = context;
    }

    public void updateNewSmsWakeUpCode(SMS sms) {
        preferences = context.getSharedPreferences("SMS_WAKEUP", context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("WAKE_CODE", sms.getWakeCode());
        editor.commit();
    }

    public SMS getCode() {
        SMS sms = new SMS();
        preferences = context.getSharedPreferences("SMS_WAKEUP", context.MODE_PRIVATE);
        sms.setWakeCode(preferences.getString("WAKE_CODE", null));
        sms.setWifiCode(preferences.getString("WIFI_CODE", null));
        sms.setLocationCode(preferences.getString("LOCATION_CODE", null));
        sms.setPhoneCallCode(preferences.getString("PHONE_CALL_CODE", null));
        return sms;
    }

    public void updateWifiCode(SMS sms) {
        preferences = context.getSharedPreferences("SMS_WAKEUP", context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("WIFI_CODE", sms.getWifiCode());
        editor.commit();
    }

    public void updateLocationCode(SMS sms) {
        preferences = context.getSharedPreferences("SMS_WAKEUP", context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("LOCATION_CODE", sms.getLocationCode());
        editor.commit();
    }

    public void updateLocationCache(Location location) {
        preferences = context.getSharedPreferences("LOCATION", context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("LATITUDE", String.valueOf(location.getLatitude()));
        editor.putString("LONGITUDE", String.valueOf(location.getLongitude()));
        editor.commit();
    }

    public void updatePhoneCallCode(SMS sms) {
        preferences = context.getSharedPreferences("SMS_WAKEUP", context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("PHONE_CALL_CODE", sms.getPhoneCallCode());
        editor.commit();
    }

    public Location getLocation() {
        preferences = context.getSharedPreferences("LOCATION", context.MODE_PRIVATE);
        Location location = new Location("gps");
        String latitute = preferences.getString("LATITUDE", null);
        String longitude = preferences.getString("LONGITUDE", null);
        if (latitute != null && longitude != null) {
            location.setLatitude(Double.parseDouble(latitute));
            location.setLongitude(Double.parseDouble(longitude));
            return location;
        }
        return null;
    }
}