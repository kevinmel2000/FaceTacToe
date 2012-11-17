package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;
import com.tonybeltramelli.mobile.lib.bitmap.UBitmap;
import com.tonybeltramelli.mobile.lib.event.ITaskEvent;
import com.tonybeltramelli.mobile.lib.event.LoaderEvent;
import com.tonybeltramelli.mobile.lib.loader.ViewLoader;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class SplashScreenActivity extends Activity {
	private ViewLoader _viewLoader;
	private Handler _handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_splash_screen);
		
		_viewLoader = new ViewLoader();
		
		LoaderEvent loader = new LoaderEvent(new ITaskEvent() {
			
			@Override
			public void onStart() {
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashScreenActivity.this);
				String path = preferences.getString(getResources().getString(R.string.settings_image_key), "");
				Boolean useDefault = preferences.getBoolean(getResources().getString(R.string.settings_default_key), false);
				
				Assets.useColor = preferences.getBoolean(getResources().getString(R.string.settings_color_key), false);
				
				Bitmap bitmap = null;
				
				if(path != "" && !useDefault)
				{
					bitmap = UBitmap.getBitmapOnSDCardFromPath(SplashScreenActivity.this, path);
				}else{
					bitmap = UBitmap.getBitmapFromDrawableResource(SplashScreenActivity.this, R.drawable.player_pawn);
				}
				
				Assets.bitmapToUse = UBitmap.resizeBitmap(bitmap, 100, 100);
			}
			
			@Override
			public void onProgress() {
			}
			
			@Override
			public void onError(Throwable error) {
				
			}
			
			@Override
			public void onComplete() {
				_handler = new Handler();
				_handler.postDelayed(new Runnable() {
		            @Override
		            public void run() {
		            	finish();
		                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
		                startActivity(intent);  
		            }
		        }, 1000);
			}
		});
		
		_viewLoader.build(getString(R.string.loader_title), getString(R.string.loader_message), this, loader, false);
		_viewLoader.execute();
	}
	
	@Override
	protected void onDestroy() {
		_viewLoader = null;
		_handler = null;
		
		super.onDestroy();
	}
}
