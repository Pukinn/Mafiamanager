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


public class CharMafia {
	
	// general
	public String type;
	public String name;
	public ArrayList<Player> player;
	
	// to generate
	public int size;
	
	public CharMafia(int _size, String _name){
		
		// handover
		size = _size;
		name = _name;
		
		// initialize
		type = "mafia";
		player = new ArrayList<Player>();
	}
	
	public void night(
			// handover
			JFrame _parentframe){
		
		if (Keys.round == 1 || playeralive() > 0){
			Keys.bufferCommand.add(Messages.getString("night.mafia.awake") + " " + name);
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
					Messages.getString("night.mafia.who"),
					"onlyunknown");
			Keys.bufferCommand.clear();
			Keys.bufferNote.clear();
			
			ArrayList<String> mafias = dialog.getPlayer();
			for (String mafia : mafias){
				Keys.playerlist.get(mafia).mafia = this;
				player.add(Keys.playerlist.get(mafia));
			}
		}
		
		// every night
		if (playeralive() > 0 || Keys.round == 1){
			
			DialogSet actmafia = new DialogSet(
					Keys.playerlist,
					_parentframe,
					1,
					Keys.bufferHead,
					Keys.bufferCommand,
					Messages.getString("night.mafia.act"),
					"nodead");
			Keys.bufferCommand.clear();
			Keys.bufferNote.clear();
			
			Player player = Keys.playerlist.get(actmafia.getPlayer().get(0));
			
			// action
			if (!player.isprotected){
				if (player.type().equals("scharping")){
					player.dieround = Keys.round + 1;
				} else {
					player.dieround = Keys.round;
				}
			}
			
			// set buffer
			Keys.bufferCommand.add(Messages.getString("night.mafia.sleep"));
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
