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

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Overview extends JPanel{

	private static final long serialVersionUID = -6525310492322809141L;
	ArrayList<ModulePlayer> alPlayerModules;
	
	public Overview(ArrayList<ModulePlayer> _modulesPlayers){
		alPlayerModules = _modulesPlayers;
		
		// Layout
		setPreferredSize(new Dimension(1000,400));
		
		paintPlayer();
	}
	
	public void paintPlayer(){
		for (ModulePlayer mp : alPlayerModules){
			add(mp);
		}
		
		revalidate();
	}
}
