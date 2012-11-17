package com.tonybeltramelli.mobile.lib.color;

import java.util.Vector;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class UColor {
	
	public static Vector<Float> fromHex(final int hexValue)
	{
		final Vector<Float> result = new Vector<Float>(3);
		
		final float pRed = (hexValue & 0xFF0000) >> 16;
		final float pGreen = (hexValue & 0xFF00) >> 8;
		final float pBlue = (hexValue & 0xFF);
		
		result.add(0, pRed/255);
		result.add(1, pGreen/255);
		result.add(2, pBlue/255);
		
		return result;
	}
	
	public static int getRandomHexColor()
	{
		return (int) Math.round(Math.random() * 0xFFFFFF);
	}
}
