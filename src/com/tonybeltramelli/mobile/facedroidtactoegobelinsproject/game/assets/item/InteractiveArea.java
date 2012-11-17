package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.item;

import java.util.Vector;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntityFactory;
import org.andengine.entity.particle.ParticleSystem;
import org.andengine.entity.particle.emitter.PointParticleEmitter;
import org.andengine.entity.particle.initializer.VelocityParticleInitializer;
import org.andengine.entity.particle.modifier.AlphaParticleModifier;
import org.andengine.entity.particle.modifier.RotationParticleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.value.Player;
import com.tonybeltramelli.mobile.lib.color.UColor;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class InteractiveArea extends Rectangle {
	private int _id;
	private Vector<Float> _color;
	private AItem _child;
	private boolean _isTouch = false;
	private Player _owner;
	//
	protected SimpleBaseGameActivity _activity;
	//
	private final int NUM_PIECES = 16;
	private final int ANIMATION_DURATION = 2;

	public InteractiveArea(int id, int pX, int pY, int pWidth, int pHeight, SimpleBaseGameActivity activity) {
		super(pX, pY, pWidth, pHeight, activity.getVertexBufferObjectManager());
		
		_id = id;
		_activity = activity;
		
		_color = UColor.fromHex(0xd4d4d4);
		setColor(_color.get(0), _color.get(1), _color.get(2), 0.05f);
	}
	
	public void selected(Player player)
	{
		_owner = player;
		_child = player == Player.PLAYER_1 ? new PlayerPawn(getWidth(), _activity) : new AgainstPawn(getWidth(), _activity);
		
		_child.centerInContainer(this);
		_child.fadeIn();
		
		_isTouch = true;
		
		attachChild(_child.getEntity());
	}
	
	public void win()
	{
		PointParticleEmitter particleEmitter = new PointParticleEmitter(getWidth() / 2, getHeight() / 2);
		IEntityFactory<Rectangle> recFact = new IEntityFactory<Rectangle>() {
			@Override
			public Rectangle create(float posX, float posY) {
				Rectangle rect = new Rectangle(posX, posY, 10, 10, _activity.getVertexBufferObjectManager());
				rect.setColor(_child.getColor());
				return rect;
			}
		};
		final ParticleSystem<Rectangle> particleSystem = new ParticleSystem<Rectangle>(recFact, particleEmitter, 500, 500, NUM_PIECES);
		
		particleSystem.addParticleInitializer(new VelocityParticleInitializer<Rectangle>(-100, 100, -100, 100));
		
		particleSystem.addParticleModifier(new AlphaParticleModifier<Rectangle>(0, 0.6f * ANIMATION_DURATION, 1, 0));
		particleSystem.addParticleModifier(new RotationParticleModifier<Rectangle>(0, ANIMATION_DURATION, 0, 360));
		
		attachChild(particleSystem);
		
		registerUpdateHandler(new TimerHandler(ANIMATION_DURATION, new ITimerCallback() {
			@Override
			public void onTimePassed(TimerHandler timerhandler) {
				particleSystem.detachSelf();
				sortChildren();
				unregisterUpdateHandler(timerhandler);
			}
		}));
		
		_child.fadeOut();
	}

	public boolean isTouched()
	{
		return _isTouch;
	}
	
	public Player getOwner()
	{
		return _owner;
	}
	
	public int getId()
	{
		return _id;
	}
}
