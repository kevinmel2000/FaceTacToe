package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config;

import android.view.Display;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class Config {
	public static final String urlAbout = "http://mathworld.wolfram.com/Tic-Tac-Toe.html";
	//
	public static int STAGE_WIDTH;
	public static int STAGE_HEIGHT;
	//
	public static int SIDE_LENGTH;
	//
	public static final String GAME_TYPE = "game_type";
	//
	public static final String AGAINST_COMPUTER = "against_computer";
	public static final String AGAINST_HUMAN = "against_human";
	//
	public static final int WINNING_DELAY = 3000;
	public static final int EQUAL_DELAY = 1000;
	public static final int IA_DELAY = 2000;
	//
	public static final int DURATION_VIBRATION = 200;
	//
	public static final int LINE_NUMBER = 3;
	public static final int COLUM_NUMBER = 3;
	//
	public static int GAME_ID;
	//
	public static void setScreenSize(Display screen)
	{
		STAGE_WIDTH = screen.getWidth();
		STAGE_HEIGHT = screen.getHeight();
		
		_setSideLength();
	}
	
	private static void _setSideLength()
	{
		int sideLength = Config.STAGE_WIDTH < Config.STAGE_HEIGHT ? Config.STAGE_WIDTH / 3 : Config.STAGE_HEIGHT / 3;
		
		SIDE_LENGTH = sideLength - ((sideLength / 100) * 10);
	}
}
