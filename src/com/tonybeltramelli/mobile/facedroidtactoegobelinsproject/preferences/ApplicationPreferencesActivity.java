package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.R;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class ApplicationPreferencesActivity extends PreferenceActivity {
	private final int _IMAGE_SETTED = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preferences);
		
		Preference buttonCustomPicture = (Preference) findPreference(getResources().getString(R.string.settings_button_image_key));
		buttonCustomPicture.setOnPreferenceClickListener(_onPreferenceClickHandler);
	}
	
	OnPreferenceClickListener _onPreferenceClickHandler = new OnPreferenceClickListener() {
		@Override
		public boolean onPreferenceClick(Preference preference) {
			Intent changePage = new Intent(ApplicationPreferencesActivity.this, CustomPictureActivity.class);
			startActivityForResult(changePage, _IMAGE_SETTED);
			return true;
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		CheckBoxPreference checkBoxPicture = (CheckBoxPreference) findPreference(getResources().getString(R.string.settings_default_key));
		CheckBoxPreference checkBoxColor = (CheckBoxPreference) findPreference(getResources().getString(R.string.settings_color_key));
		
		
		if(requestCode == _IMAGE_SETTED)
		{
			switch(resultCode)
			{
				case RESULT_OK:
					checkBoxPicture.setChecked(false);
					checkBoxColor.setChecked(false);
					break;
				case RESULT_CANCELED:
					checkBoxPicture.setChecked(true);
					checkBoxColor.setChecked(!checkBoxPicture.isChecked());
					break;
			}
		}
	};
}
