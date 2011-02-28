/*
	Mafiamanager - a tool to support the referee of the parlor game "Mafia"
    Copyright (C) 2011  Thomas Högner

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;


public class ModuleDay extends ModuleCharacter {

	// parent values
	private SwitchPanel parent;
	private JFrame mainframe;
	private GameValues gamevalues;
	private Overview overview;
	
	//general
	private ModulePlayer markedPlayer;
	
	public ModuleDay(Overview _overview,
			SwitchPanel _parent,
			JFrame _mainframe,
			GameValues _gamevalues){
		
		super(_overview, Messages.getString("mod.day"), _gamevalues);
		
		parent  = _parent;
		mainframe = _mainframe;
		gamevalues = _gamevalues;
		overview = _overview;
		
		addCommand(Messages.getString("mod.day.awake"));
		addNote(Messages.getString("mod.day.lynch"));
	}
	
// ACTIONS
	// calling
	void calling(){
		overview.resetRound();
		buttonEnabled(false);
		
		logicAfterNight();
		
		overview.refreshPlayer();
	}

	// player pressed
	void playerPressed(ArrayList<String> marked) {
		if (marked.size() == 1){
			buttonEnabled(true);
			markedPlayer = overview.getPlayer(marked.get(0));
		}
		else {
			buttonEnabled(false);
			markedPlayer = null;
		}
	}
	
	// accept
	void acceptAction() {
		
		logicAfterDay();
		
		// switch to next component
		parent.nextComponent();
		((ModuleCharacter)parent.getActComponent()).call();
		
		mainframe.pack();
	}
	
	private void logicAfterNight(){
		
		// kill player on list
		for (KillAt ka : gamevalues.dieingPlayer){
			if (ka.round == gamevalues.round) {
				overview.getPlayer(ka.name).iLifevalue = 0;
			}
		}
	}
	
	private void logicAfterDay(){
		// count up round
		gamevalues.round++;
		
		// set lynched player dead
		markedPlayer.iLifevalue = 0;
		
		// refresch player panels
		overview.refreshPlayer();
	}
}
