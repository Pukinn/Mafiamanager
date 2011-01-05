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
	private int figure;
		/*	0 = unknown
		 *	1 = citizen
		 * 	2 = mafia
		 * 	3 = detective
		 *	4 = soulsaver
		 */
	private int alive;
	private int number;
	
	private String officialFigure;

	public Player(String _name){
		
		// create player
		name = _name;
		figure = 0;
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
	
	public void setFigure(int _fig){
		figure = _fig;
	}
	
	public int getFigure(){
		return figure;
	}
	
	public void setOfficialFigure(String _fig){
		officialFigure = _fig;
	}
	
	public String getOfficialFigure(){
		return officialFigure;
	}
	
	public int alive(){
		return alive;
	}
	
	public void kill(){
		alive -= 1;
		
		if (alive == 0){
			switch (figure){
			case 1: Keys.citizen -= 1; break;
			case 2: Keys.mafia -= 1; break;
			case 3: Keys.detective -= 1; break;
			case 4: Keys.soulsaver -= 1; break;
			}
			
			Keys.killed.add(this);
		}
	}
	
	public void protect(){
		alive += 1;
	}
}
