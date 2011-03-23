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

import javax.swing.JFrame;


public class SwitchedModuleDay extends SwitchedModule {

	// parent components
	private SwitchPanel parent;
	private JFrame mainframe;
	private Overview overview;
	
	// general
	private ModulePlayer markedPlayer;
	private GameValues gamevalues;
	
	public SwitchedModuleDay(
			Overview _overview,
			GameValues _gamevalues,
			SwitchPanel _parent,
			JFrame _mainframe,
			int _groupSize,
			String _groupName
			){
		
		super(
				_overview,
				_gamevalues,
				Messages.getString("mod.day"),
				"day",
				_groupSize,
				_groupName
				);
		
		parent  = _parent;
		mainframe = _mainframe;
		overview = _overview;
		gamevalues = _gamevalues;
		
		this.addCommand(Messages.getString("mod.day.awake"));
		this.addNote(Messages.getString("mod.day.lynch"));
	}
	
// ACTIONS
	// calling
	void calling(){
		overview.resetRound();
		this.buttonEnabled(false);
		
		this.logicAfterNight();
		
		overview.refreshPlayer();
	}

	// player pressed
	void playerPressed(ArrayList<String> marked) {
		if (marked.size() == 1){
			this.buttonEnabled(true);
			markedPlayer = overview.getPlayer(marked.get(0));
		}
		else {
			this.buttonEnabled(false);
			markedPlayer = null;
		}
	}
	
	// accept
	void acceptAction() {
		
		this.logicAfterDay();
		
		// switch to next component
		parent.nextComponent();
		((SwitchedModule)parent.getActComponent()).call();
		
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
