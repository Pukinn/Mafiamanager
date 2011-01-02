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
	private boolean alive;
	private boolean isprotected;

	public Player(String _name){
		
		// create player
		figure = 0;
		name = _name;
		alive = true;
		isprotected = false;
	}
	
	public void kill(){
		if (alive && !isprotected){
			alive = false;
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getFigure(){
		String strFigure = "err";
		
		switch (figure){
		case 0: strFigure = "unknown"; break;
		case 1: strFigure = "citizen"; break;
		case 2: strFigure = "mafia"; break;
		case 3: strFigure = "detective"; break;
		case 4: strFigure = "soulsaver"; break;
		}
		
		return strFigure;
	}
}
