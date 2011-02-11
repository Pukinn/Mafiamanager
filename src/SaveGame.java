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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class SaveGame {
	
	private File player_directory;
	
	public SaveGame(){
		
		// create data directory if not exists
		File data_directory = new File("data");
		if (!data_directory.exists()) { data_directory.mkdir(); }
		
		// create player directory if not exists
		player_directory = new File(data_directory, "player");
		if (!player_directory.exists()) { player_directory.mkdir(); }
		
	}

	public void newPlayer(String _name){
		
		String pathplayer = new File(player_directory, _name).getPath();

		try{
			FileWriter saver = new FileWriter(pathplayer, true);
			saver.write("name:" + "\n" + _name);
			saver.close();
			}
			catch(IOException e){
			System.err.println(e);
			}

		
	}
	
}
