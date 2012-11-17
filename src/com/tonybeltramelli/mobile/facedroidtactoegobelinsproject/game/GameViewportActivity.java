package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.R;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Config;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.screen.GameScene;
import com.tonybeltramelli.mobile.lib.bitmap.UBitmap;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class GameViewportActivity extends SimpleBaseGameActivity {
	private Camera _camera;
	private GameScene _gameScene;
	private String _gameType;
	private Toast _toast;
	//
	public static final String CALLBACK_VALUE = "callback_value";

	@Override
	public EngineOptions onCreateEngineOptions() {
		_camera = new Camera(0, 0, Config.STAGE_WIDTH, Config.STAGE_HEIGHT);

		Bundle bundle = this.getIntent().getExtras();
		_gameType = bundle.get(Config.GAME_TYPE).toString();

		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(Config.STAGE_WIDTH, Config.STAGE_HEIGHT), _camera);
	}

	public void showMessage() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				CharSequence message = getResources().getString(Config.GAME_ID) + " " + getResources().getString(R.string.to_play);
				if(_toast != null) _toast.cancel();
				_toast = Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT);
				_toast.show();
			}
		});
	}

	public void endGame() {
		Intent back = new Intent();
		back.putExtra(CALLBACK_VALUE, true);
		
		setResult(RESULT_OK, back);
		finish();
	}
	
	public void vibrate()
	{
		((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(Config.DURATION_VIBRATION);
	}
	
	@Override
	protected void onCreateResources() {
		if(Assets.bitmapToUse != null) return;
			
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String path = preferences.getString(getResources().getString(R.string.settings_image_key), "");
		Boolean useDefault = preferences.getBoolean(getResources().getString(R.string.settings_default_key), false);
		
		Assets.useColor = preferences.getBoolean(getResources().getString(R.string.settings_color_key), false);
		
		Bitmap bitmap = null;
		
		if(path != "" && !useDefault)
		{
			bitmap = UBitmap.getBitmapOnSDCardFromPath(this, path);
		}else{
			bitmap = UBitmap.getBitmapFromDrawableResource(this, R.drawable.player_pawn);
		}
		
		Assets.bitmapToUse = UBitmap.resizeBitmap(bitmap, 100, 100);
	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());

		_gameScene = new GameScene(_gameType, this);

		return _gameScene;
	}

	private void _clean()
	{
		if(_gameScene != null)
		{
			_gameScene.clearChildScene();
			_gameScene.clean();
			_gameScene = null;
		}
		
		_toast = null;
		_camera = null;
	}

	@Override
	public void onBackPressed() {
		_clean();

		Intent back = new Intent();
		back.putExtra(CALLBACK_VALUE, false);
		
		setResult(RESULT_CANCELED, back);

		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		_clean();
		
		super.onDestroy();
	}
}
