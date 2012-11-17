package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets;

import java.util.Iterator;
import java.util.Vector;

import org.andengine.entity.Entity;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Config;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.item.AItem;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.item.InteractiveArea;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.value.Player;
import com.tonybeltramelli.mobile.lib.color.UColor;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class Grid extends AItem {
	private Scene _scene;
	private Vector<InteractiveArea> _areas;
	private int _numAreas = 0;
	//
	private final int _NUM_TO_WIN = 3;

	public Grid(SimpleBaseGameActivity activity, Scene scene) {
		super(activity);

		_scene = scene;
		_entity = new Entity();

		final int DEC_X = ((Config.STAGE_WIDTH < Config.STAGE_HEIGHT ? Config.STAGE_WIDTH : Config.STAGE_HEIGHT) / 2) - Config.SIDE_LENGTH - Config.SIDE_LENGTH / 2;
		final int DEC_Y = ((Config.STAGE_WIDTH > Config.STAGE_HEIGHT ? Config.STAGE_WIDTH : Config.STAGE_HEIGHT) / 2) - Config.SIDE_LENGTH - Config.SIDE_LENGTH / 2;

		_createLineAt(DEC_X + Config.SIDE_LENGTH, DEC_Y, DEC_X + Config.SIDE_LENGTH, DEC_Y + Config.SIDE_LENGTH * 3);
		_createLineAt(DEC_X + Config.SIDE_LENGTH * 2, DEC_Y, DEC_X + Config.SIDE_LENGTH * 2, DEC_Y + Config.SIDE_LENGTH * 3);

		_createLineAt(DEC_X, DEC_Y + Config.SIDE_LENGTH, DEC_X + Config.SIDE_LENGTH * 3, DEC_Y + Config.SIDE_LENGTH);
		_createLineAt(DEC_X, DEC_Y + Config.SIDE_LENGTH * 2, DEC_X + Config.SIDE_LENGTH * 3, DEC_Y + Config.SIDE_LENGTH * 2);

		final int DEC_TARGETS = (Config.SIDE_LENGTH / 100) * 10;

		_areas = new Vector<InteractiveArea>(Config.LINE_NUMBER * Config.COLUM_NUMBER);

		for (int i = 0; i < Config.LINE_NUMBER; i++) {
			for (int j = 0; j < Config.COLUM_NUMBER; j++) {
				_createTarget(DEC_X + Config.SIDE_LENGTH * i + DEC_TARGETS, DEC_Y + Config.SIDE_LENGTH * j + DEC_TARGETS, Config.SIDE_LENGTH - DEC_TARGETS * 2);
			}
		}
	}

	private void _createLineAt(int x1, int y1, int x2, int y2) {
		Line line = new Line(x1, y1, x2, y2, _activity.getVertexBufferObjectManager());
		line.setLineWidth(5);
		Vector<Float> color = UColor.fromHex(0x292929);
		line.setColor(color.get(0), color.get(1), color.get(2));

		_entity.attachChild(line);
	}

	private void _createTarget(int x, int y, int sideLength) {
		_numAreas++;

		InteractiveArea target = new InteractiveArea(_numAreas, x, y, sideLength, sideLength, _activity);

		_entity.attachChild(target);
		_scene.registerTouchArea(target);

		_areas.add(target);
	}

	public boolean touchOccuredAt(Player player, int xTarget, int yTarget) {
		Iterator<InteractiveArea> areasIterator = _areas.iterator();
		while (areasIterator.hasNext()) {
			InteractiveArea area = areasIterator.next();

			if (!area.isTouched()
					&& (area.getX() < xTarget && area.getX() + area.getWidth() > xTarget)
					&& (area.getY() < yTarget && area.getY() + area.getHeight() > yTarget))
			{
				area.selected(player);
				return true;
			}
		}

		return false;
	}

	public boolean isWinner() {
		int player1Num = 0;
		int player2Num = 0;
		int i = 0;
		int j = 0;
		int k = 0;
		Vector<InteractiveArea> winners = new Vector<InteractiveArea>();
		
		//check lines
		for (i = 0; i < Config.LINE_NUMBER; i++)
		{
			for (j = 0, k = 0; j < Config.COLUM_NUMBER; j++, k += 2) {
				InteractiveArea area = getAreaById(i + k + 1 + j);
				
				if (area.isTouched()) {
					winners.add(area);
					if (area.getOwner() == Player.PLAYER_1)
						player1Num++;
					else
						player2Num++;
				}
			}
			
			if (player1Num == _NUM_TO_WIN || player2Num == _NUM_TO_WIN) {
				_showWinners(winners);
				return true;
			}
			
			player1Num = 0;
			player2Num = 0;
			winners = null;
			winners = new Vector<InteractiveArea>();
		}
		
		//check columns
		for (i = 0, k = 0; i < Config.LINE_NUMBER; i++, k += 2)
		{
			for (j = 0; j < Config.COLUM_NUMBER; j++) {
				InteractiveArea area = getAreaById(i + k + 1 + j);
				
				if (area.isTouched()) {
					winners.add(area);
					if (area.getOwner() == Player.PLAYER_1)
						player1Num++;
					else
						player2Num++;
				}
			}
			
			if (player1Num == _NUM_TO_WIN || player2Num == _NUM_TO_WIN) {
				_showWinners(winners);
				return true;
			}
			
			player1Num = 0;
			player2Num = 0;
			winners = null;
			winners = new Vector<InteractiveArea>();
		}
		
		//check diag pos
		for (i = 0, k = 0; i < Config.LINE_NUMBER; i++, k += 3)
		{
			InteractiveArea area = getAreaById(i + k + 1);
			
			if (area.isTouched()) {
				winners.add(area);
				if (area.getOwner() == Player.PLAYER_1)
					player1Num++;
				else
					player2Num++;
			}
		}
		
		if (player1Num == _NUM_TO_WIN || player2Num == _NUM_TO_WIN) {
			_showWinners(winners);
			return true;
		}
		
		player1Num = 0;
		player2Num = 0;
		winners = null;
		winners = new Vector<InteractiveArea>();
		
		//check diag neg
		for (i = 0, k = 0; i < Config.LINE_NUMBER; i++, k ++)
		{
			InteractiveArea area = getAreaById(i + k + 3);
			
			if (area.isTouched()) {
				winners.add(area);
				if (area.getOwner() == Player.PLAYER_1)
					player1Num++;
				else
					player2Num++;
			}
		}
		
		if (player1Num == _NUM_TO_WIN || player2Num == _NUM_TO_WIN) {
			_showWinners(winners);
			return true;
		}
		
		player1Num = 0;
		player2Num = 0;
		winners = null;
		winners = new Vector<InteractiveArea>();

		return false;
	}

	private void _showWinners(Vector<InteractiveArea> winners)
	{
		Iterator<InteractiveArea> areasIterator = winners.iterator();
		while (areasIterator.hasNext()) {
			InteractiveArea area = areasIterator.next();
			area.win();
		}
	}
	
	public InteractiveArea getAreaById(int id) {
		Iterator<InteractiveArea> areasIterator = _areas.iterator();
		while (areasIterator.hasNext()) {
			InteractiveArea area = areasIterator.next();
			if (area.getId() == id) {
				return area;
			}
		}

		return null;
	}

	public Vector<InteractiveArea> getAreas()
	{
		return _areas;
	}
	
	public void clean() {
		_entity = null;
		_areas = null;
	}
}
