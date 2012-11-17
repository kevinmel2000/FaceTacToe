package com.tonybeltramelli.mobile.lib.message.email;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class UEmail {

	public static void sendEmail(Activity activityReference, String emailAddress, String subject, String message) {
		sendEmail(activityReference, emailAddress, subject, message, "Email send failed", "Email not valid", "Choose an email client");
	}
	
	public static void sendEmail(Activity activityReference, String emailAddress, String subject, String message, String error, String notValidEmail, String chooseMessage) {
		if(isValidEmail(emailAddress))
		{
			try {
				Intent email = new Intent(Intent.ACTION_SEND);
				email.putExtra(Intent.EXTRA_EMAIL, new String[] { emailAddress });
				email.putExtra(Intent.EXTRA_SUBJECT, subject);
				email.putExtra(Intent.EXTRA_TEXT, message);
				email.setType("message/rfc822");
				activityReference.startActivity(Intent.createChooser(email, chooseMessage));
			} catch (Exception e) {
				Toast.makeText(activityReference.getApplicationContext(), error, Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}else{
			Toast.makeText(activityReference.getBaseContext(), notValidEmail, Toast.LENGTH_LONG).show();
		}
	}

	public static boolean isValidEmail(String email) {
		return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}
}
