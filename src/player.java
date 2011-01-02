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

public class player {
	
	// player values
	private String name;
	private int figure;
	private boolean alive;
	private boolean isprotected;
	
	//
	private boolean flag_daynight;

	public player(String _name, int _figure){
		
		// create player
		name = _name;
		figure = _figure;
		alive = true;
		isprotected = false;
		
		//
		flag_daynight = false;
	}
	
	public void nextRound(){
		if (!flag_daynight){
		// night
			
			
		
			// invert flag
			flag_daynight = !flag_daynight;
		}
		else {
		// day
			
			
			// invert flag
			flag_daynight = !flag_daynight;
		}
	}
}
