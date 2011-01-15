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
	public static CharVillager villager;
	public static ArrayList<CharMafia> mafia;
	public static ArrayList<CharDetective> detectives;
	public static ArrayList<CharDoctor> doctors;
	public static ArrayList<CharTerrorist> terrorists;
	
	// player
	public static HashMap<String, Player> playerlist;
	
	// buffer
	public static ArrayList<String> bufferCommand = new ArrayList<String>();
	public static ArrayList<String> bufferNote = new ArrayList<String>();
	public static String bufferHead;
	
	// round informations
	public static int round;
}
