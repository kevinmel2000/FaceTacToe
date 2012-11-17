package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.item;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class PlayerPawn extends AItem {

	public PlayerPawn(float sideLength, SimpleBaseGameActivity activity) {
		super(activity);
		
		if(!Assets.useColor)
		{
			if(Assets.bitmapToUse == null)
			{
				_buildEntityWithPicture("player_pawn.png");
			}else{
				_buildEntityWithCustomPicture();
			}
		}else{
			_entity = new Rectangle(0, 0, sideLength, sideLength, activity.getVertexBufferObjectManager());
		}
		
		_setColor(0x43ef47);
	}
}
