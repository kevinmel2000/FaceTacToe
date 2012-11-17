package com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.ia;

import java.util.Iterator;
import java.util.Vector;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.R;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.config.Config;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.Grid;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.game.assets.item.InteractiveArea;
import com.tonybeltramelli.mobile.facedroidtactoegobelinsproject.value.Player;
import com.tonybeltramelli.mobile.lib.math.UMath;

/**
 * 
 * @author Tony Beltramelli www.tonybeltramelli.com
 *
 */
public class BasicIa {
	private Grid _grid;
	private Vector<InteractiveArea> _areas;
	private int _difficulty = 0;
	//
	private final int _NUM_BEFORE_TO_WIN = 2;

	public BasicIa(Activity activity, Grid grid) {
		_grid = grid;
		_areas = grid.getAreas();
		
		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		String difficultyValue = preferences.getString(activity.getResources().getString(R.string.settings_difficulty_key), "");
		_difficulty = Integer.parseInt(difficultyValue);
	}

	public void play()
	{	
		switch(_difficulty)
		{
			case 1:
				_fillRandomEmptyArea();
				break;
			case 2:
				if(Math.random() < 0.5)
				{
					_fillRandomEmptyArea();
				}else{
					_fillAndStopPlayer();
				}
				break;
			case 3:
				_fillAndStopPlayer();
				break;
		}
	}
	
	private void _fillRandomEmptyArea()
	{
		Vector<Integer> emptyAreas = _getEmptyAreasId();
		int ind = emptyAreas.size() > 1 ? (int) Math.floor(Math.random() * emptyAreas.size()) : 0;
		int areaId = emptyAreas.get(ind);
		_grid.getAreaById(areaId).selected(Player.PLAYER_2);
	}

	private void _fillAndStopPlayer()
	{
		//try to win
		if(_parseAndFill(Player.PLAYER_2)) return;
		
		//try to stop the player
		if(_parseAndFill(Player.PLAYER_1)) return;
		
		_fillRandomEmptyArea();
	}
	
	private Boolean _parseAndFill(Player target)
	{
		int playerOccurrence = 0;
		int i = 0;
		int j = 0;
		int k = 0;
		Vector<Integer> allIndex;
		int indexToFill = 0;
		
		// check lines
		for (i = 0; i < Config.LINE_NUMBER; i++) {
			allIndex = new Vector<Integer>();

			for (j = 0, k = 0; j < Config.COLUM_NUMBER; j++, k += 2) {
				InteractiveArea area = _grid.getAreaById(i + k + 1 + j);

				if (area.isTouched() && area.getOwner() == target) {
					allIndex.add(i + k + 1 + j);
					playerOccurrence++;
				}
			}

			if (playerOccurrence == _NUM_BEFORE_TO_WIN) {
				indexToFill = _getEmptyIndexFromLine(i, allIndex);
				if (!_grid.getAreaById(indexToFill).isTouched()) {
					_grid.getAreaById(indexToFill).selected(Player.PLAYER_2);
					return true;
				}
			}

			playerOccurrence = 0;
			allIndex = null;
		}

		// check columns
		for (i = 0, k = 0; i < Config.LINE_NUMBER; i++, k += 2) {
			allIndex = new Vector<Integer>();
			
			for (j = 0; j < Config.COLUM_NUMBER; j++) {
				InteractiveArea area = _grid.getAreaById(i + k + 1 + j);

				if (area.isTouched() && area.getOwner() == target) {
					allIndex.add(i + k + 1 + j);
					playerOccurrence++;
				}
				
				if (playerOccurrence == _NUM_BEFORE_TO_WIN) {
					indexToFill = _getEmptyIndexFromColumn(i, allIndex);
					if (!_grid.getAreaById(indexToFill).isTouched()) {
						_grid.getAreaById(indexToFill).selected(Player.PLAYER_2);
						return true;
					}
				}
			}
			
			playerOccurrence = 0;
			allIndex = null;
		}
		
		//check diag pos	
		allIndex = new Vector<Integer>();
		
		for (i = 0, k = 0; i < Config.LINE_NUMBER; i++, k += 3)
		{
			InteractiveArea area = _grid.getAreaById(i + k + 1);
					
			if (area.isTouched() && area.getOwner() == target) {
				allIndex.add(i + k + 1);
				playerOccurrence++;
			}
		}
				
		if (playerOccurrence == _NUM_BEFORE_TO_WIN) {
			indexToFill = _getEmptyIndexFromDiag(allIndex);
			if (!_grid.getAreaById(indexToFill).isTouched()) {
				_grid.getAreaById(indexToFill).selected(Player.PLAYER_2);
				return true;
			}
		}
				
		playerOccurrence = 0;
		allIndex = new Vector<Integer>();
		
		//check diag neg
		for (i = 0, k = 0; i < Config.LINE_NUMBER; i++, k ++)
		{
			InteractiveArea area = _grid.getAreaById(i + k + 3);
			
			if (area.isTouched() && area.getOwner() == target) {
				allIndex.add(i + k + 3);
				playerOccurrence++;
			}
		}
		
		if (playerOccurrence == _NUM_BEFORE_TO_WIN) {
			indexToFill = _getEmptyIndexFromDiag(allIndex);
			if (!_grid.getAreaById(indexToFill).isTouched()) {
				_grid.getAreaById(indexToFill).selected(Player.PLAYER_2);
				return true;
			}
		}
		
		playerOccurrence = 0;
		allIndex = null;
		
		return false;
	}

	private Vector<Integer> _getEmptyAreasId()
	{
		Vector<Integer> emptyAreas = new Vector<Integer>();
		
		Iterator<InteractiveArea> areasIterator = _areas.iterator();
		while (areasIterator.hasNext()) {
			InteractiveArea area = areasIterator.next();
			if (!area.isTouched()) {
				emptyAreas.add(area.getId());
			}
		}
		
		return emptyAreas;
	}

	private int _getEmptyIndexFromLine(int lineIndex, Vector<Integer> vector)
	{
		return (12 + (lineIndex * 3)) - UMath.getSumFromVector(vector);
	}
	
	private int _getEmptyIndexFromColumn(int columnIndex, Vector<Integer> vector)
	{
		return (6 + (columnIndex * 9)) - UMath.getSumFromVector(vector);
	}
	
	private int _getEmptyIndexFromDiag(Vector<Integer> vector)
	{
		return 15 - UMath.getSumFromVector(vector );
	}
}
