package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.preferences.ApplicationPreferencesActivity;
import com.tonybeltramelli.mobile.lib.font.Font;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class MainActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        Button buttonPlay = (Button) findViewById(R.id.button_play);
        buttonPlay.setOnClickListener(_clickHandler);
        
        Button buttonMessage = (Button) findViewById(R.id.button_message);
        buttonMessage.setOnClickListener(_clickHandler);
        
        Button buttonAbout = (Button) findViewById(R.id.button_about);
        buttonAbout.setOnClickListener(_clickHandler);
        
        Button buttonPreferences = (Button) findViewById(R.id.button_preferences);
        buttonPreferences.setOnClickListener(_clickHandler);
        
        Font.apply(buttonPlay, this, Assets.BEBAS_NEUE);
        Font.apply(buttonMessage, this, Assets.BEBAS_NEUE);
        Font.apply(buttonAbout, this, Assets.BEBAS_NEUE);
        Font.apply(buttonPreferences, this, Assets.BEBAS_NEUE);
    }
    
    private OnClickListener _clickHandler = new OnClickListener() {
    	@Override
		public void onClick(View v) {
    		
    		Intent changePage = null;
			
    		switch(v.getId())
    		{
	    		case R.id.button_play:
	    			changePage = new Intent(MainActivity.this, GamePageActivity.class);
	    			break;
	    		case R.id.button_message:
	    			changePage = new Intent(MainActivity.this, MessagePageActivity.class);
	    			break;
	    		case R.id.button_about:
	    			changePage = new Intent(MainActivity.this, AboutPageActivity.class);
	    			break;
	    		case R.id.button_preferences:
	    			changePage = new Intent(MainActivity.this, ApplicationPreferencesActivity.class);
	    			break;
    		}
    		
    		if(changePage != null) startActivity(changePage);
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
	
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    }
}
