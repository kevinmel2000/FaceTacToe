package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.screen;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.input.touch.TouchEvent;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.R;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Config;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.GameViewportActivity;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.Grid;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.ia.BasicIa;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.value.Player;
import com.tonybeltramelli.mobile.lib.color.UColor;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class GameScene extends Scene {
	private Grid _grid;
	private BasicIa _ia;
	private String _gameType;
	private SimpleBaseGameActivity _activity;
	private Player _currentPlayer;
	private Timer _timer;
	private int _numPlayed = 0;
	private boolean _isTouchable = true;

	public GameScene(String gameType, SimpleBaseGameActivity activity) {
		_gameType = gameType;
		_activity = activity;

		Vector<Float> color = UColor.fromHex(0x5f5f5f);
		setBackground(new Background(color.get(0), color.get(1), color.get(2)));

		_grid = new Grid(activity, this);
		attachChild(_grid.getEntity());
		
		_ia = new BasicIa(activity, _grid);

		_currentPlayer = Math.random() > 0.5 ? Player.PLAYER_1 : Player.PLAYER_2;
		
		_setGameId();
		((GameViewportActivity) _activity).showMessage();
		
		if(_gameType.equals(Config.AGAINST_COMPUTER) && _currentPlayer == Player.PLAYER_2)
		{
			_isTouchable = false;
			_createTimer(new TaskComputerPlay(), Config.IA_DELAY);
		}
	}

	@Override
	public boolean onSceneTouchEvent(TouchEvent pSceneTouchEvent) {
		if(!_isTouchable) return false;
		
		final int targetX = (int) (pSceneTouchEvent.getX());
		final int targetY = (int) (pSceneTouchEvent.getY());

		if (_grid.touchOccuredAt(_currentPlayer, targetX, targetY)) {
			((GameViewportActivity) _activity).vibrate();
			
			_numPlayed ++;
			_validateGrid();
		}

		return true;
	}

	private void _validateGrid()
	{
		if (_grid.isWinner()) {
			_isTouchable = false;
			_setGameId();
			
			_createTimer(new TaskEndGame(), Config.WINNING_DELAY);
		}else{
			if(_numPlayed == (Config.COLUM_NUMBER * Config.LINE_NUMBER))
			{
				Config.GAME_ID = R.string.label_equal;
				
				_createTimer(new TaskEndGame(), Config.EQUAL_DELAY);
			}else{	
				switchPlayer();
				_setGameId();
				
				if(_gameType.equals(Config.AGAINST_COMPUTER) && _currentPlayer == Player.PLAYER_2)
				{
					_isTouchable = false;
					
					_createTimer(new TaskComputerPlay(), Config.IA_DELAY);
				}else{
					_isTouchable = true;
				}
				
				((GameViewportActivity) _activity).showMessage();
			}
		}
	}

	public void switchPlayer()
	{
		_currentPlayer = _currentPlayer == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;
	}
	
	private void _setGameId()
	{
		if(_gameType.equals(Config.AGAINST_COMPUTER))
		{
			Config.GAME_ID = _currentPlayer == Player.PLAYER_1 ? R.string.owner_player : R.string.computer_player;
		}
		
		if(_gameType.equals(Config.AGAINST_HUMAN))
		{
			Config.GAME_ID = _currentPlayer == Player.PLAYER_1 ? R.string.first_player : R.string.second_player;
		}
	}
	
	private void _createTimer(TimerTask task, int delay)
	{
		if (_timer != null) {
			_timer.cancel();
			_timer.purge();
			_timer = null;
		}
		
		_timer = new Timer();
		_timer.schedule(task, delay);
	}

	public void clean() {
		clearTouchAreas();

		_grid.clean();
		_grid = null;

		if (_timer != null) {
			_timer.cancel();
			_timer.purge();
			_timer = null;
		}
	}

	class TaskEndGame extends TimerTask {
		@Override
		public void run() {
			if(_activity instanceof GameViewportActivity) ((GameViewportActivity) _activity).endGame();
		}
	}
	
	class TaskComputerPlay extends TimerTask {
		@Override
		public void run() {
			_ia.play();
			_numPlayed ++;
			
			_validateGrid();
		}
	}
}
