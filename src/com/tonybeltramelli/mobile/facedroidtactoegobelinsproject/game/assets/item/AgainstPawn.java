package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.item;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class AgainstPawn extends AItem {

	public AgainstPawn(float sideLength, SimpleBaseGameActivity activity) {
		super(activity);
		
		if(!Assets.useColor)
		{
			_buildEntityWithPicture("against_pawn.png");
		}else{
			_entity = new Rectangle(0, 0, sideLength, sideLength, activity.getVertexBufferObjectManager());
		}
		
		_setColor(0xff3b3b);
	}
}
