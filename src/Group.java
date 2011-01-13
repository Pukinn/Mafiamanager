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

public class Group {

	private String group;
	private String groupname;
	private int groupsize;
	private boolean alldead;
	
	public String group(){ return group; }
	public String groupname(){ return groupname; }
	public boolean alldead(){ return alldead; }
	public int groupsize(){ return groupsize; }
	
	public Group(String _group, String _name, int _size){
		group = _group;
		groupname = _name;
		groupsize = _size;
		alldead = false;
	}
	
	public void kill(){
		groupsize--;
		
		if (groupsize == 0){
			alldead = true;
		}
	}
}
