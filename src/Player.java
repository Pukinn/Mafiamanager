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
	public String name;
	public int number;
	
	// Character
	public CharVillager villager;
	public CharMafia mafia;
	public CharDetective detective;
	public CharDoctor doctor;
	public CharTerrorist terrorist;
	
	// states
	public boolean alive;
	public boolean isprotected;

	public Player(String _name){
		
		// create player
		name = _name;
		alive = true;
	}
	
	public String type(){
		String type = "undefined";
		
		int cnt = 0;
		if (villager != null){ type = "villager"; cnt++; }
		if (mafia != null){ type = "mafia"; cnt++; }
		if (detective != null){ type = "detective"; cnt++; }
		if (doctor != null){ type = "doctor"; cnt++; }
		if (terrorist != null){ type = "terrorist"; cnt++; }
		
		if (cnt > 1){ System.err.println(Messages.getString("err.doublecharacter") + " " + name); }
		
		return type;
	}
}
