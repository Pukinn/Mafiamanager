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
	private boolean alive;
	
	public String name;
	public int character;
		/*	0 = unknown
		 *	1 = villager
		 * 	2 = mafia
		 * 	3 = detective
		 *	4 = doctor
		 *	5 = terrorist
		 */
	public boolean isprotected;
	public int number;
	
	public String officialCharacter;

	public Player(String _name){
		
		// create player
		name = _name;
		character = 0;
		alive = true;
	}
	
	public void kill(){
		alive = false;
		
		switch (character){
		case 1: Keys.villager--; break;
		case 2: Keys.mafia--; break;
		case 3: Keys.detective--; break;
		case 4: Keys.doctor--; break;
	//	case 5: Keys.terrorist--; break;
		}
	}
	
	public boolean alive(){ return alive; }
}
