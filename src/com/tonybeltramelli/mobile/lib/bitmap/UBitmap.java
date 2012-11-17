package com.tonybeltramelli.mobile.lib.bitmap;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class UBitmap {
	public static Bitmap getBitmapOnSDCardFromPath(Activity activity, String path)
	{
		Bitmap bitmap = null;
		
		try {
			bitmap = BitmapFactory.decodeFile(path);
		} catch (Exception e) {
			Log.e("External storage error", e.getMessage());
		}
		
		try {
			File filePath = activity.getFileStreamPath(path);
			FileInputStream fi = new FileInputStream(filePath);
			bitmap = BitmapFactory.decodeStream(fi);
		} catch (Exception ex) {
			Log.e("Internal storage error", ex.getMessage());
		}
	
		return bitmap;
	}

	public static Bitmap getBitmapOnSDCardFromUri(Activity activity, Uri uri)
	{
		String[] filePathColumn = { MediaStore.Images.Media.DATA };

		Cursor cursor = activity.getContentResolver().query(uri, filePathColumn, null, null, null);
		cursor.moveToFirst();

		int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		String picturePath = cursor.getString(columnIndex);
		cursor.close();
		return BitmapFactory.decodeFile(picturePath);
	}

	public static Bitmap getBitmapFromDrawableResource(Activity activity, int drawableId)
	{
		return BitmapFactory.decodeResource(activity.getResources(), drawableId);
	}
	
	public static Bitmap resizeBitmap(Bitmap source, int newWidth, int newHeight)
	{
		int width = source.getWidth();
		int height = source.getHeight();
		
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		
		final Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		
		return Bitmap.createBitmap(source, 0, 0, width, height, matrix, false);
	}
	
	public static Uri saveBitmapAndGetUri(Activity activity, Bitmap bitmap) throws IOException
	{
		File sdCardFile = Environment.getExternalStorageDirectory();
		if (sdCardFile.canWrite() == true)
		{
			File extDir = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
			extDir.mkdirs();
	        File imageFile = new File(extDir, Assets.IMAGE_NAME);
	        FileOutputStream fileStream = new FileOutputStream(imageFile);
	        bitmap.compress(CompressFormat.PNG, 100, fileStream);
	        fileStream.close();
	          
	       return (Uri.fromFile(imageFile));
		}
		return null;
	}
	
	public static void cropFromUri(Activity activity, Uri uri, int code)
	{
		try {
			Intent cropIntent = new Intent("com.android.camera.action.CROP");
			cropIntent.setDataAndType(uri, "image/*");
			cropIntent.putExtra("crop", "true");
			cropIntent.putExtra("aspectX", 1);
			cropIntent.putExtra("aspectY", 1);
			cropIntent.putExtra("outputX", 256);
			cropIntent.putExtra("outputY", 256);
			cropIntent.putExtra("return-data", true);
			activity.startActivityForResult(cropIntent, code);
		} catch (ActivityNotFoundException anfe) {
			String errorMessage = "Error - your device doesn't support the crop action.";
			Toast toast = Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}
}
