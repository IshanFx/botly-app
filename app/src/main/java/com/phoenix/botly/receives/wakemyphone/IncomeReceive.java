package com.phoenix.botly.receives.wakemyphone;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.phoenix.botly.AppSession;
import com.phoenix.botly.BuildConfig;
import com.phoenix.botly.dao.SMS;

import androidx.core.content.ContextCompat;

public class IncomeReceive extends BroadcastReceiver {

    //SmsManager smsManager = SmsManager.getDefault();
    private String TAG  = this.getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
       Bundle bundle =  intent.getExtras();
        AppSession preferenceHandler = new AppSession(context);
        SMS sms = preferenceHandler.getCode();
        Log.i("SmsReceiver", "Code: "+sms.getPhoneCallCode());

        if(bundle!=null){
            final Object[] pdusObj = (Object[]) bundle.get("pdus");

            for (int i = 0; i < pdusObj.length; i++) {

                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                String senderNum = phoneNumber;
                String message = currentMessage.getDisplayMessageBody();

                Log.i("SmsReceiver", "senderNum: "+ senderNum + "; message: " + message);

                if(message.equals(sms.getWakeCode())) {
                    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING), AudioManager.FLAG_ALLOW_RINGER_MODES|AudioManager.FLAG_PLAY_SOUND);
                }

                if(message.equals(sms.getWifiCode())){
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                    wifiManager.setWifiEnabled(true);
                }

                if(message.equals(sms.getLocationCode())){
                    if(preferenceHandler.getLocation() != null) {
                        Location location = preferenceHandler.getLocation();
                        SmsManager smsManager = SmsManager.getDefault();
                        String messageSms = "http://maps.google.com/maps?z=12&t=m&q=loc:"+location.getLatitude()+"+"+location.getLongitude();
                        if (ContextCompat.checkSelfPermission(context,
                                Manifest.permission.SEND_SMS)
                                == PackageManager.PERMISSION_GRANTED) {
                            smsManager.sendTextMessage(senderNum, null, messageSms, null, null);
                        }
                    }
                }

                if("paid".equals(BuildConfig.FLAVOR)) {
                    if (message.equals(sms.getPhoneCallCode())) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + senderNum));
                        if (ContextCompat.checkSelfPermission(context,
                                Manifest.permission.CALL_PHONE)
                                == PackageManager.PERMISSION_GRANTED) {
                            context.startActivity(callIntent);
                        }

                    }
                }
                // Show alert
                /*int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(mContext, "senderNum: "+ senderNum + ", message: " + message, duration);
                toast.show();*/

            } // end for loop
        }
    }
}
