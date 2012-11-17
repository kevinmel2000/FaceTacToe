package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.preferences;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.R;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;
import com.tonybeltramelli.mobile.lib.bitmap.UBitmap;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class CustomPictureActivity extends Activity {
	private final int _RESULT_IMAGE_FROM_CAMERA = 1;
	private final int _RESULT_IMAGE_FROM_LIBRARY = 2;
	private final int _RESULT_CROP = 3;
	//
	private SharedPreferences _preferences;
	private ImageView _imageView;
	private Uri _capturedUri;
	private boolean _isSetted = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_custom_picture);
		
		Button buttonFromCamera = (Button) findViewById(R.id.button_from_camera);
		buttonFromCamera.setOnClickListener(_clickHandler);

		Button buttonFromLibrary = (Button) findViewById(R.id.button_from_library);
		buttonFromLibrary.setOnClickListener(_clickHandler);
		
		Button buttonOk = (Button) findViewById(R.id.button_ok);
		buttonOk.setOnClickListener(_clickHandler);
		
		_imageView = (ImageView) findViewById(R.id.customImageContainer);
		
		_preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String path = _preferences.getString(getResources().getString(R.string.settings_image_key), "");
		
		Bitmap bitmap = null;
		
		if(path != "")
		{
			bitmap = UBitmap.getBitmapOnSDCardFromPath(this, path);
		
			if(bitmap == null) bitmap = UBitmap.getBitmapFromDrawableResource(this, R.drawable.player_pawn);
		}else{
			bitmap = UBitmap.getBitmapFromDrawableResource(this, R.drawable.player_pawn);
		}
		
		_imageView.setImageBitmap(bitmap);
		Assets.bitmapToUse = UBitmap.resizeBitmap(bitmap, 100, 100);
	}

	private OnClickListener _clickHandler = new OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.button_from_camera:
					_selectPictureFromCamera();
					break;
				case R.id.button_from_library:
					_selectPictureFromLibrary();
					break;
				case R.id.button_ok:
					_back();
					finish();
					break;
			}
		}
	};

	protected void _selectPictureFromCamera() { 
		Intent callCamera = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		
		File photo = new File(Environment.getExternalStorageDirectory(), Assets.IMAGE_NAME);
		callCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
		_capturedUri = Uri.fromFile(photo);
		
		startActivityForResult(callCamera, _RESULT_IMAGE_FROM_CAMERA);
	}

	private void _selectPictureFromLibrary() {
		Intent callLibrary = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(callLibrary, _RESULT_IMAGE_FROM_LIBRARY);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_OK)
		{
			switch(requestCode)
			{
				case _RESULT_IMAGE_FROM_CAMERA :
					UBitmap.cropFromUri(this, _capturedUri, _RESULT_CROP);
					break;
				case _RESULT_IMAGE_FROM_LIBRARY :
					UBitmap.cropFromUri(this, data.getData(), _RESULT_CROP);
					break;
				case _RESULT_CROP :
					Bitmap bitmap = (Bitmap) data.getExtras().get("data");
					
					if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
					{
						try {
							_capturedUri = UBitmap.saveBitmapAndGetUri(this, bitmap);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
					_displayAndSave(bitmap);
					break;
			}
		}
	}
	
	private void _back()
	{
		if(_isSetted)
		{
			setResult(RESULT_OK);
		}else{
			setResult(RESULT_CANCELED);
		}
	}
	
	@Override
	public void onBackPressed() {
		_back();
		
		super.onBackPressed();
	}
	
	private void _displayAndSave(Bitmap bitmap)
	{
		_imageView.setImageBitmap(bitmap);
		
		String raw = _capturedUri.toString();
		String validUri = raw.replace("file:///", "");
				
		SharedPreferences.Editor editor = _preferences.edit();
		editor.putString(getResources().getString(R.string.settings_image_key), validUri);
		editor.commit();
		
		Assets.bitmapToUse = UBitmap.resizeBitmap(bitmap, 100, 100);
		
		_isSetted = true;
	}
}
