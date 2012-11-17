package com.tonybeltramelli.mobile.lib.font;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class Font {
	public static void apply(TextView textView, Activity activity, String fontPath) {
		Typeface font = Typeface.createFromAsset(activity.getAssets(), fontPath);  
		textView.setTypeface(font);  
	}
}
