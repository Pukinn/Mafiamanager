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

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private SimpleDateFormat formatter;
	private Date currentTime;
	
	private String pathlog;
	
	public Log(){
		formatter = new SimpleDateFormat("yyyy.MM.dd-HH:mm");
		currentTime = new Date();
	}
	
	public void create(){

		String time = formatter.format(currentTime);
		pathlog = "./Logs/"+time+".txt";
		
		try{
		FileWriter log = new FileWriter(pathlog);
		log.write("Beginning: "+time+"\n");
		log.close();
		}
		catch(IOException e){
		System.err.println(e);
		}
	}
	
	public void newLine(String _s){
		
		try{
			FileWriter log = new FileWriter(pathlog, true);
			log.write(_s+"\n");
			log.close();
			}
			catch(IOException e){
			System.err.println(e);
			}
	}
	
	
}
