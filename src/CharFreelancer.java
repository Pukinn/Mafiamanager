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
**/

import java.util.ArrayList;

import javax.swing.JFrame;


public class CharFreelancer {
	
	// general
	public String type;
	public String name;
	public ArrayList<Player> player;
	
	// to generate
	public int size;

	public CharFreelancer(int _size, String _name){
		
		// handover
		size = _size;
		name = _name;
		
		// initialize
		type = "freelancer";
		player = new ArrayList<Player>();
	}
	
	public void night(JFrame _parentframe){
		
		if (Keys.round == 1 || playeralive() > 0){
			Keys.bufferCommand.add(Messages.getString("night.freelancer.awake") + " " + name);
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
					Messages.getString("night.freelancer.who"),
					"onlyunknown");
			Keys.bufferCommand.clear();
			Keys.bufferNote.clear();
			
			ArrayList<String> freelancers = dialog.getPlayer();
			for (String freelancer : freelancers){
				Keys.playerlist.get(freelancer).freelancer = this;
				player.add(Keys.playerlist.get(freelancer));
			}
		}
		
		// search killed players for this freelancer team
		for (Player p : player){
			if (p.dieround == Keys.round){
				
				// activate the freelancer
				p.activated = true;
				
				// set the freelancer alive
				p.dieround = 0;
			}
		}
		
		
		// every night
		if (playeralive() > 0 || Keys.round == 1){
			boolean activated = false;
			
			for (Player p : player)
			{
				if (p.activated)
				{
					activated = true;
					break;
				}
			}

			if (activated)
			{
				Keys.bufferCommand.add(Messages.getString("night.freelancer.activated"));
			}
			else
			{
				Keys.bufferCommand.add(Messages.getString("night.freelancer.not_activated"));
			}
			
			// set buffer
			Keys.bufferCommand.add(Messages.getString("night.freelancer.sleep"));
			
			
			DialogCommand command = new DialogCommand(
					_parentframe,
					Keys.bufferHead,
					Keys.bufferCommand,
					Keys.bufferNote);
			Keys.bufferCommand.clear();
			Keys.bufferNote.clear();
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
