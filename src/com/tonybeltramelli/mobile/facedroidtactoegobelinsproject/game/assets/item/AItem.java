package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.item;

import java.util.Vector;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.shape.RectangularShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Assets;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.BitmapTextureAtlasSource;
import com.tonybeltramelli.mobile.lib.color.UColor;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public abstract class AItem {
	protected SimpleBaseGameActivity _activity;
	protected Entity _entity;
	
	public AItem(SimpleBaseGameActivity activity) {
		_activity = activity;
	}
	
	protected void _buildEntityWithPicture(String pictureName)
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		final BitmapTextureAtlas bitmapTextureAtlas  = new BitmapTextureAtlas(_activity.getTextureManager(), 128, 128, TextureOptions.BILINEAR);
		final ITextureRegion textureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bitmapTextureAtlas, _activity, pictureName, 0, 0);
		bitmapTextureAtlas.load();
		
		_entity = new Sprite(0, 0, textureRegion, _activity.getVertexBufferObjectManager());
	}
	
	protected void _buildEntityWithCustomPicture()
	{
		final BitmapTextureAtlasSource source = new BitmapTextureAtlasSource(Assets.bitmapToUse);
		final BitmapTextureAtlas texture = new BitmapTextureAtlas(_activity.getTextureManager(), 128, 128);
		texture.addTextureAtlasSource(source, 0, 0);
		texture.load();
		final TextureRegion textureRegion = (TextureRegion) TextureRegionFactory.createFromSource(texture, source, 0, 0);
		
		_entity = new Sprite(0, 0, textureRegion, _activity.getVertexBufferObjectManager());
	}
	
	protected void _setColor(int hexValue)
	{
		if(_entity == null) return;
		
		Vector<Float> color = UColor.fromHex(hexValue);
		
		_entity.setColor(color.get(0), color.get(1), color.get(2));
	}
	
	public void centerInContainer(RectangularShape container)
	{
		_entity.setX(container.getWidth() / 2 - getWidth() / 2);
		_entity.setY(container.getHeight() / 2 - getHeight() / 2);
	}
	
	public void fadeIn()
	{
		_alphaModifier(0.0f, 1.0f);
	}
	
	public void fadeOut()
	{
		_alphaModifier(0.0f, 1.0f);
	}
	
	private void _alphaModifier(float from, float to)
	{
		_entity.clearEntityModifiers();
		
		final IEntityModifier am = new AlphaModifier(1, from, to);
		am.setAutoUnregisterWhenFinished(true);
		
		_entity.registerEntityModifier(am);
	}
	
	public Entity getEntity()
	{
		return _entity;
	}
	
	public float getWidth()
	{
		if(_entity instanceof RectangularShape)
		{
			return ((RectangularShape) _entity).getWidth();
		}
		
		return 0;
	}
	
	public float getHeight()
	{
		if(_entity instanceof RectangularShape)
		{
			return ((RectangularShape) _entity).getHeight();
		}
		
		return 0;
	}
	
	public Color getColor()
	{
		return _entity.getColor();
	}
}
