package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Config;
import com.tonybeltramelli.mobile.lib.font.Font;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class EndGamePageActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_endgame_page);
		
		Button buttonPlayAgain = (Button) findViewById(R.id.button_play_again);
		buttonPlayAgain.setOnClickListener(_onClickHandler);
		
		Button buttonBackHome = (Button) findViewById(R.id.button_back_home);
		buttonBackHome.setOnClickListener(_onClickHandler);
		
		TextView textView = (TextView) findViewById(R.id.end_game_label);
		
		int winnerId = Config.GAME_ID;
		String sentence = winnerId != R.string.label_equal ?
				getResources().getString(winnerId) + " " + getResources().getString(R.string.label_win):
				getResources().getString(winnerId);
		textView.setText(sentence);
		
		Font.apply(buttonPlayAgain, this, Assets.BEBAS_NEUE);
		Font.apply(buttonBackHome, this, Assets.BEBAS_NEUE);
	}
	
	private OnClickListener _onClickHandler = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent changePage = null;
			
			switch (v.getId()) {
				case R.id.button_play_again:
					changePage = new Intent(EndGamePageActivity.this, GamePageActivity.class);
					break;
				case R.id.button_back_home:
					changePage = new Intent(EndGamePageActivity.this, MainActivity.class);
					break;
			}
			
			if(changePage != null)
			{
				changePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(changePage);
			}
		}
	};
}
