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


public class CharTerrorist {
	
	// general
	public String type;
	public String name;
	public ArrayList<Player> player;
	
	// to generate
	public int size;
	
	public CharTerrorist(int _size, String _name){
		
		// handover
		size = _size;
		name = _name;
		
		// initialize
		type = "terrorist";
		player = new ArrayList<Player>();

	}
	
	public void night(
			// handover
			JFrame _parentframe){
		
		if (Keys.round == 1 || playeralive() > 0){
			Keys.bufferCommand.add(Messages.getString("night.terrorist.awake") + " " + name);
		}
		
		// ACTIONS
		// first night
		if (Keys.round == 1){
			DialogSet dialog = new DialogSet(
					Keys.playerlist,
					_parentframe,
					size,
					Keys.bufferHead,
					Keys.bufferCommand,
					Messages.getString("night.terrorist.who"),
					"onlyunknown");
			Keys.bufferCommand.clear();
			Keys.bufferNote.clear();
			
			ArrayList<String> terrorists = dialog.getPlayer();
			for (String terrorist : terrorists){
				Keys.playerlist.get(terrorist).terrorist = this;
				player.add(Keys.playerlist.get(terrorist));
			}
			
			// set buffer
			Keys.bufferCommand.add(Messages.getString("night.terrorist.sleep"));
		}
	}
	
	// returns how much players are alive
	public int playeralive(){
		int alive = 0;
		
		for (Player p : player){
			if (p.alive) { alive++; }
		}
		
		return alive;
	}
}
