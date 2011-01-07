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
	private String name;
	private int character;
		/*	0 = unknown
		 *	1 = villager
		 * 	2 = mafia
		 * 	3 = detective
		 *	4 = doctor
		 */
	private int alive;
	private int number;
	
	private String officialCharacter;

	public Player(String _name){
		
		// create player
		name = _name;
		character = 0;
		alive = 1;
	}
	
	public String name(){
		return name;
	}
	
	public void setNumber(int _num){
		number = _num;
	}
	
	public int getNumber(){
		return number;
	}
	
	public void setCharacter(int _fig){
		character = _fig;
	}
	
	public int getCharacter(){
		return character;
	}
	
	public void setOfficialCharacter(String _fig){
		officialCharacter = _fig;
	}
	
	public String getOfficialCharacter(){
		return officialCharacter;
	}
	
	public int alive(){
		return alive;
	}
	
	public void kill(){
		alive -= 1;
		
		if (alive == 0){
			switch (character){
			case 1: Keys.villager -= 1; break;
			case 2: Keys.mafia -= 1; break;
			case 3: Keys.detective -= 1; break;
			case 4: Keys.doctor -= 1; break;
			}
			
			Keys.killed.add(this);
		}
	}
	
	public void protect(){
		alive += 1;
	}
}
