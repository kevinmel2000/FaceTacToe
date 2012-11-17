package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.preferences.ApplicationPreferencesActivity;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.value.Sender;
import com.tonybeltramelli.mobile.lib.font.Font;
import com.tonybeltramelli.mobile.lib.message.email.UEmail;
import com.tonybeltramelli.mobile.lib.message.sms.USms;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class MessagePageActivity extends Activity {
	private Sender _senderType;
	private Button _buttonEmail;
	private Button _buttonSms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_message_page);

		_buttonEmail = (Button) findViewById(R.id.button_email_view);
		_buttonEmail.setOnClickListener(_onClickHandler);

		_buttonSms = (Button) findViewById(R.id.button_sms_view);
		_buttonSms.setOnClickListener(_onClickHandler);
		
		_buttonEmail.setEnabled(false);
		_senderType = Sender.SEND_EMAIL;
		
		Button buttonSend = (Button) findViewById(R.id.button_send);
		buttonSend.setOnClickListener(_onClickHandler);
		
		Font.apply(_buttonEmail, this, Assets.BEBAS_NEUE);
		Font.apply(_buttonSms, this, Assets.BEBAS_NEUE);
		Font.apply(buttonSend, this, Assets.BEBAS_NEUE);
	}

	private OnClickListener _onClickHandler = new OnClickListener() {
		@Override
		public void onClick(View v) {

			ViewSwitcher viewSwitcher = (ViewSwitcher) findViewById(R.id.message_view_switcher);

			switch (v.getId()) {
			case R.id.button_email_view:
				viewSwitcher.showNext();
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_inactive));
				
				_buttonSms.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
				
				_buttonSms.setEnabled(true);
				_buttonEmail.setEnabled(false);
				
				_senderType = Sender.SEND_EMAIL;
				break;
			case R.id.button_sms_view:
				viewSwitcher.showPrevious();
				v.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_inactive));
				
				_buttonEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_custom));
				
				_buttonEmail.setEnabled(true);
				_buttonSms.setEnabled(false);
				
				_senderType = Sender.SEND_SMS;
				break;
			case R.id.button_send:
				if (_senderType == Sender.SEND_EMAIL) {
					_sendEmail();
				} else if(_senderType == Sender.SEND_SMS){
					_sendSms();
				}
				break;
			}
		}
	};

	private void _sendEmail() {
		final String emailAdress = String.valueOf(((TextView) findViewById(R.id.email_textfield)).getText());
		
		final String subject = getResources().getString(R.string.email_subject);
		final String message = String.valueOf(((TextView) findViewById(R.id.message_content)).getText());
		
		if(!message.isEmpty())
		{
			UEmail.sendEmail(this, emailAdress, subject, message, 
					getResources().getString(R.string.error_email),
					getResources().getString(R.string.error_email_not_valid),
					getResources().getString(R.string.choose_message));
		}else{
			Toast.makeText(getBaseContext(), R.string.error_message_content, Toast.LENGTH_SHORT).show();
		}
	}

	private void _sendSms() {
		final String phoneNumber = String.valueOf(((TextView) findViewById(R.id.phone_textfield)).getText());
		final String message = String.valueOf(((TextView) findViewById(R.id.message_content)).getText());
		
		if(!message.isEmpty())
		{
			USms.sendSMS(this, phoneNumber, message, 
					getResources().getString(R.string.success_sms),
					getResources().getString(R.string.error_sms));
		}else{
			Toast.makeText(getBaseContext(), R.string.error_message_content, Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent openPreferences = new Intent(this, ApplicationPreferencesActivity.class);
		startActivity(openPreferences);
		
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		_senderType = null;
		_buttonEmail = null;
		_buttonSms = null;
		
		super.onDestroy();
	}
}