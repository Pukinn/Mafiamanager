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

import javax.swing.JButton;
import javax.swing.JFrame;


public class ModuleMafia extends ModuleCharacter {

	private SwitchPanel parent;
	private JFrame mainframe;
	
	public ModuleMafia(Overview _overview, String _title, SwitchPanel _parent, JFrame _mainframe){
		super(_overview, _title);
		
		parent  = _parent;
		mainframe = _mainframe;
		
		
		addCommand(Messages.getString("mod.mafia.awake"));
		addNote(Messages.getString("mod.mafia.kill"));
	}

	
	void acceptAction() {
		parent.nextComponent();
		mainframe.pack();
	}
}
