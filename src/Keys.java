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

import java.util.ArrayList;
import java.util.HashMap;


public class Keys {
	
	// groups
		// good
		public static CharVillager villager;
		public static ArrayList<CharDetective> detectives;
		public static ArrayList<CharDoctor> doctors;
		public static ArrayList<CharScharping> scharpings;
		// bad
		public static ArrayList<CharMafia> mafia;
		public static ArrayList<CharTerrorist> terrorists;

	
	// player
	public static HashMap<String, Player> playerlist;
	
	// buffer
	public static ArrayList<String> bufferCommand = new ArrayList<String>();
	public static ArrayList<String> bufferNote = new ArrayList<String>();
	public static String bufferHead;
	
	// round informations
	public static int round;
	
	public static int aliveVillager(){
		int alive = 0;
		alive += villager.playeralive();
		return alive;
	}
	
	public static int aliveMafia(){
		int alive = 0;
		for (CharMafia group : mafia){
			alive += group.playeralive();
		}
		return alive;
	}
	
	public static int aliveDetectives(){
		int alive = 0;
		for (CharDetective group : detectives){
			alive += group.playeralive();
		}
		return alive;
	}
	
	public static int aliveDoctors(){
		int alive = 0;
		for (CharDoctor group : doctors){
			alive += group.playeralive();
		}
		return alive;
	}
	
	public static int aliveTerrorists(){
		int alive = 0;
		for (CharTerrorist group : terrorists){
			alive += group.playeralive();
		}
		return alive;
	}
	
	public static int aliveScharpings(){
		int alive = 0;
		for (CharScharping group : scharpings){
			alive += group.playeralive();
		}
		return alive;
	}
	
	public static int aliveGood(){
		int alive = 0;
		
		alive += aliveVillager();
		alive += aliveDetectives();
		alive += aliveDoctors();
		alive += aliveScharpings();
		
		return alive;
	}
	
	public static int aliveBad(){
		int alive = 0;
		
		alive += aliveMafia();
		alive += aliveTerrorists();
		
		return alive;
	}
	
	public static int alivePlayer(){
		int alive = 0;
		
		alive += aliveGood();
		alive += aliveBad();
		
		return alive;
	}
}
