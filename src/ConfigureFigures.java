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


public class ConfigureFigures {

	private Map<String, Player> playerlist;
	
	public ConfigureFigures(Map<String, Player> _playerlist){
		playerlist = _playerlist;
	}
	
	public void configure(){
		
		System.out.println(Messages.getString("howmuchFigures"));

		Scanner scanner = new Scanner(System.in);
		
		int iPlayer = playerlist.size();
		
		System.out.println(Messages.getString("Mafia")+": ("+Messages.getString("of")+" "+iPlayer+")");
		Keys.mafia = scanner.nextInt();
		iPlayer -= Keys.mafia;
		
		System.out.println("Seelenretter: (von "+iPlayer+")");
		Keys.soulsaver = scanner.nextInt();
		iPlayer -= Keys.soulsaver;
		
		System.out.println("Detektiv: (von "+iPlayer+")");
		Keys.detective = scanner.nextInt();
		iPlayer -= Keys.detective;
		
		Keys.citizen = iPlayer;
		
		Log.newLine(Messages.getString("figures")+":");
		Log.newLine("citizens ("+Keys.citizen+"), mafia ("+Keys.mafia+"), soulsavers ("+Keys.soulsaver+"), detectives ("+Keys.detective+")");

		//TODO Ausgabe wie viele Karten zu verteilen sind
	}
}
