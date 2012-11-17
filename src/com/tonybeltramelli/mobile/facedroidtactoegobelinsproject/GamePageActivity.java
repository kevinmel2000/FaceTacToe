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
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Config;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.GameViewportActivity;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.preferences.ApplicationPreferencesActivity;
import com.tonybeltramelli.mobile.lib.font.Font;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class GamePageActivity extends Activity {
	private static int _GAME_CODE = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_game_page);
		
		Button buttonAgainstComputer = (Button) findViewById(R.id.button_against_computer);
		buttonAgainstComputer.setOnClickListener(_clickHandler);

		Button buttonAgainstHuman = (Button) findViewById(R.id.button_against_human);
		buttonAgainstHuman.setOnClickListener(_clickHandler);
		
		Font.apply(buttonAgainstComputer, this, Assets.BEBAS_NEUE);
		Font.apply(buttonAgainstHuman, this, Assets.BEBAS_NEUE);
		
		Config.setScreenSize(getWindowManager().getDefaultDisplay());
	}

	private OnClickListener _clickHandler = new OnClickListener() {
		@Override
		public void onClick(View v) {

			Intent changePage = new Intent(GamePageActivity.this, GameViewportActivity.class);
			Bundle bundle = new Bundle();
			
			switch (v.getId())
			{
				case R.id.button_against_computer:
					bundle.putString(Config.GAME_TYPE, Config.AGAINST_COMPUTER);
					break;
				case R.id.button_against_human:
					bundle.putString(Config.GAME_TYPE, Config.AGAINST_HUMAN);
					break;
			}
			
			changePage.putExtras(bundle);
			startActivityForResult(changePage, _GAME_CODE);
		}
	};
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(data.getBooleanExtra(GameViewportActivity.CALLBACK_VALUE, false))
		{
			startActivity(new Intent(GamePageActivity.this, EndGamePageActivity.class));
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
