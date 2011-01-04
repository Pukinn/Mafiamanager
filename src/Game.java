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

import java.util.Map;
import java.util.Scanner;


public class Game {

	private Map<String, Player> playerlist;

	
	public Game(Map<String, Player> _playerlist){
		Keys.round = 1;
		playerlist = _playerlist;
	}
	
	public void nextNight(){
		OutputToReferee.command("Alle schlafen ein.");
		
		Log.newLine("");
		Log.newLine("");
		Log.timestamp();
		Log.addLine("Round "+Keys.round+" (Night):"+"\n");
		
	// TASKLIST
		Soulsaver soulsaver = new Soulsaver(playerlist);
		soulsaver.night();
		
		Mafia mafia = new Mafia(playerlist);
		mafia.night();
		
		Detective detective = new Detective(playerlist);
		detective.night();	
		
		
		OutputToReferee.command("Alle wachen auf.");
		

	}
	
	public void nextDay(){
		Log.newLine("");
		Log.timestamp();
		Log.addLine("Round "+Keys.round+" (Day):"+"\n");
		
		
		Keys.round++;
	}
	
	
}
