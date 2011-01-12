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

public class Player {
	
	// player values
	public String name;
	public String character;
	public int number;
	
	// states
	private boolean alive;
	public boolean isprotected;

	public Player(String _name){
		
		// create player
		name = _name;
		character = "undefined";
		alive = true;
	}
	
	public void kill(){
		alive = false;
		
		if (character.equals("villager")) { Keys.villager--; }
		else if (character.equals("mafia")) { Keys.mafia--; }
		else if (character.equals("detective")) { Keys.detective--; }
		else if (character.equals("doctor")) { Keys.doctor--; }
		else if (character.equals("terrorist")) { Keys.terrorist--; }
	}
	
	public boolean alive(){ return alive; }
}
