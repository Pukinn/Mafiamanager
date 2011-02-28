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

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Overview extends JPanel{

	private static final long serialVersionUID = -6525310492322809141L;
	
	public ArrayList<ModulePlayer> playerModules;
	private ActionListener moduleAL;
	
	public Overview(ActionListener _moduleAL){
		playerModules = new ArrayList<ModulePlayer>();
		moduleAL = _moduleAL;
		
		// Layout
		setPreferredSize(new Dimension(1000,400));
		
		paintPlayer();
	}
	
	public void paintPlayer(){
		removeAll();
		
		for (ModulePlayer mp : playerModules){
			add(mp);
		}
		
		repaint();
	}
	
	public void addPlayer(String _name){
		playerModules.add(new ModulePlayer(_name, moduleAL));
	}
	
	public void removePlayer(String _name){
		
		int cnt = 0;
		for (ModulePlayer mp : playerModules){
			if (mp.sName.equals(_name)){ break; }
			cnt++;
		}
		playerModules.remove(cnt);

		paintPlayer();
	}
	
	public ArrayList<String> getPlayerList(){
		ArrayList<String> player = new ArrayList<String>();
		
		for (ModulePlayer mp : playerModules){
			player.add(mp.sName);
		}
		
		return player;
	}
	
	public void setAllEnabeled(boolean _bool){
		for (ModulePlayer mp : playerModules){
			mp.setNameButton(_bool);
		}
	}
	
	public void resetGame(){
		for (ModulePlayer mp : playerModules){
			mp.reset();
		}
	}
	
	public ArrayList<String> getMarked(){
		ArrayList<String> marked = new ArrayList<String>();
		
		for (ModulePlayer mp : playerModules){
			if (mp.isMarked()) { marked.add(mp.sName); }
		}
		
		return marked;
	}
	
	public ModulePlayer getPlayer(String _player){
		ModulePlayer player = null;
		
		for (ModulePlayer mp : playerModules){
			if (mp.sName.equals(_player)) {
				player = mp;
				break;
			}
			
		}
		
		return player;
	}
	
	public void resetRound(){
		for (ModulePlayer mp : playerModules){
			mp.mark(false);
		}
	}
	
	public void refreshPlayer(){
		for (ModulePlayer mp : playerModules){
			mp.refresh();
		}
	}
	
	
}
