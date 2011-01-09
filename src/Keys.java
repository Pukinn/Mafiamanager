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

public class Keys {

	// figures
	public static int villager;
	public static int mafia;
	public static int detective;
	public static int doctor;
	
	public static String IntToFigure(final int _num){
		
		String strFigure = "err";
		
		switch (_num){
		case 0: strFigure = Messages.getString("unknown"); break;
		case 1: strFigure = Messages.getString("villager"); break;
		case 2: strFigure = Messages.getString("mafia"); break;
		case 3: strFigure = Messages.getString("detective"); break;
		case 4: strFigure = Messages.getString("doctor"); break;
		}
		
		return strFigure;
	}
}
