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


public class SwitchedModuleMafia extends SwitchedModule {

	// parent components
	private SwitchPanel parent;
	private JFrame mainframe;
	private Overview overview;
	
	// general
	private ModulePlayer markedPlayer;
	private GameValues gamevalues;
	
	public SwitchedModuleMafia(
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
				Messages.getString("mod.mafia") + ": " + _groupName,
				"mafia",
				_groupSize,
				_groupName
				);
		
		parent  = _parent;
		mainframe = _mainframe;
		overview = _overview;
		gamevalues = _gamevalues;
		
		this.addCommand(Messages.getString("mod.mafia.awake"));
		this.addNote(Messages.getString("mod.mafia.kill"));
	}
	
// ACTIONS
	// calling
	void calling(){
		overview.resetRound();
		this.buttonEnabled(false);
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
		
		// add player to killing list
		gamevalues.kill(markedPlayer.sName, gamevalues.round, "default");
		
		// switch to next component
		parent.nextComponent();
		((SwitchedModule)parent.getActComponent()).call();
		
		mainframe.pack();
	}
}
