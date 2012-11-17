package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Config;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.preferences.ApplicationPreferencesActivity;
import com.tonybeltramelli.mobile.lib.font.Font;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class AboutPageActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_about_page);
		
		Button buttonNavigate = (Button) findViewById(R.id.button_navigate);
		buttonNavigate.setOnClickListener(_clickHandler);
		
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadUrl(Config.urlAbout);
		
		Font.apply(buttonNavigate, this, Assets.BEBAS_NEUE);
	}
	
	private OnClickListener _clickHandler = new OnClickListener()
	{
		public void onClick(View v) {
			Intent navigateToUrl = new Intent( Intent.ACTION_VIEW, Uri.parse(Config.urlAbout));
			startActivity(navigateToUrl);
		}
	};
	
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
}
