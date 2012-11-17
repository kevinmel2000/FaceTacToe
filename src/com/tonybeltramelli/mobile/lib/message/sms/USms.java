package com.tonybeltramelli.mobile.lib.message.sms;

import android.app.Activity;
import android.telephony.SmsManager;
import android.widget.Toast;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class USms {

	public static void sendSMS(final Activity activityReference, String phoneNumber, String message) {
		sendSMS(activityReference, phoneNumber, message, "SMS sent", "SMS send failed");
	}
	
	public static void sendSMS(final Activity activityReference, String phoneNumber, String message, String success, String error) {
		try {
			SmsManager smsManager = SmsManager.getDefault();
			smsManager.sendTextMessage(phoneNumber, null, message, null, null);
			Toast.makeText(activityReference.getApplicationContext(), success, Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			Toast.makeText(activityReference.getApplicationContext(), error, Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}
}
