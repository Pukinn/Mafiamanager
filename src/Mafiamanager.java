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

import java.util.HashMap;
import java.util.Map;

class Mafiamanager {

	// player
	//public static ArrayList<Player> playerlist;
	
	public static void main(String args[])
	{
		// declare
		Map<String, Player> playerlist = new HashMap<String, Player>();
		
	// BEFORE GAME
		
		// create player
		Createplayer myPlayers = new Createplayer(playerlist);
		myPlayers.namePlayers();
		
		// create log
		Log.create();
		
		// configure figures
		ConfigureFigures myConfigure = new ConfigureFigures(playerlist);
		myConfigure.configure();
		
	// START GAME
		
		Game game = new Game(playerlist);
		
		game.nextNight();
		

		
		
	}
	
}