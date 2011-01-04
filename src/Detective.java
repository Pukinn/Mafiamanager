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


public class Detective {

	private Map<String, Player> playerlist;
	
	public Detective(Map<String, Player> _playerlist){
		playerlist = _playerlist;
	}
	
	public void night(){
		if (Keys.detective > 0){
			OutputToReferee.command("Die Detektive erwachen.");
			
			if (Keys.round == 1){
				OutputToReferee.note("Wer ist Detektiv?");
				
				for (int i = 0; i < Keys.detective; i++){
					setFigureConsole(3);
				}
			}
			
			OutputToReferee.command("Wer soll abgefragt werden?");
			OutputToReferee.note("Eingabe:");
			
			while (true){
				Player player = exisPlayerConsole();
				
				if (player.alive() > 0){

					
					
					
					Log.timestamp();
					Log.addLine("Detectives ask for '"+player.name()+"'"+"\n");
					break;
				}
				else {
					System.err.println("Dieser Spieler ist tot! Anderer Spieler:");
				}
			}
			
			
			
			OutputToReferee.command("Die Detektive schlafen ein.");
		}
	}
	
	public Player exisPlayerConsole(){
		Player player;
		
		Scanner scanner = new Scanner(System.in);
		
		while (true){
			player = playerlist.get(scanner.nextLine());
			
			if (player != null){
				break;
			}
			else {
				System.err.println("Der Spieler existiert nicht, Eingabe wiederholen!");
			}
		}
		
		return player;
	}
	
	public void setFigureConsole(int _fig){

		Scanner scanner = new Scanner(System.in);

		while (true){
			String name = scanner.nextLine();
			Player player = playerlist.get(name);
			
			if (player == null){
				System.err.println("Den Spieler '"+name+"' gibt es nicht!");
				
				try { Thread.sleep(10);}
				catch (InterruptedException e) {}
				
				OutputToReferee.note("Wer ist "+Keys.IntToFigureGer(_fig)+"?");
			}
			else if (player.getFigure() != 0){
				System.err.println("Sie haben '"+name+"' schon festgelegt!");
				
				try { Thread.sleep(10);}
				catch (InterruptedException e) {}
				
				OutputToReferee.note("Wer ist noch "+Keys.IntToFigureGer(_fig)+"?");
			}
			else {
				playerlist.get(name).setFigure(_fig);
				break;
			}
		}
	}
}
