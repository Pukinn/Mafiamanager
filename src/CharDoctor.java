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


public class CharDoctor {
	
	// general
	public String type;
	public String name;
	public ArrayList<Player> player;
	public String protectedPlayer;
	
	// to generate
	public int size;
	
	public CharDoctor(int _size, String _name){
		
		// handover
		size = _size;
		name = _name;
		
		// initialize
		type = "doctor";
	}
	
	public void night(
			// handover
			JFrame _parentframe){
		
		// ACTIONS
		// first night
		if (Keys.round == 1){
			DialogSet getdoctors = new DialogSet(
					Keys.playerlist,
					_parentframe,
					size,
					Keys.bufferHead,
					Keys.bufferCommand,
					Messages.getString("gui.whosdoctor"),
					"onlyunknown");
			Keys.bufferCommand.clear();
			Keys.bufferNote.clear();
		
			ArrayList<String> doctors = getdoctors.getPlayer();
			for (String doctor : doctors){
				Keys.playerlist.get(doctor).doctor = this;
			}
		}
		
		// every night
		if (playeralive() > 0){
			Keys.bufferCommand.add(Messages.getString("night.doctor.awake") + " " + name);
			
			DialogSet actdoctor = new DialogSet(
					Keys.playerlist,
					_parentframe,
					1,
					Keys.bufferHead,
					Keys.bufferCommand,
					Messages.getString("gui.actdoctor"),
					"nodead");
			Player player = Keys.playerlist.get(actdoctor.getPlayer().get(0));
			Keys.bufferCommand.clear();
			Keys.bufferNote.clear();
			
			// action
			player.isprotected = true;
			protectedPlayer = player.name;
			
			// set buffer
			Keys.bufferCommand.add(Messages.getString("night.doctor.sleep"));
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
