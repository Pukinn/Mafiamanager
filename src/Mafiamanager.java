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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

class Mafiamanager {
	
	// general
	private static SortedMap<String, Player> playerlist;
	
	// GUI
	private static GridBagConstraints con;
	
	private static JFrame mainframe;
		private static JPanel panelPlayers;
			private static ArrayList<JPanel> panelXplayer;
				private static ArrayList<JLabel> labelXplayer;
	
	public static void main(String args[])
	{
		// declare
		playerlist = new TreeMap<String, Player>();

		// set default player
		for (int i=1; i<=5; i++){
			String player = Messages.getString("gui.player")+" "+i;
			playerlist.put(player, new Player(player));
			playerlist.get(player).setNumber(i);
		}
		
		
		
		// GUI
		
		// frame
		mainframe = new JFrame();
		mainframe.setLayout(new GridBagLayout());
		con = new GridBagConstraints();
		
		mainframe.setTitle("Mafiamanager");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// panel players
		panelPlayers = new JPanel(new GridBagLayout());
		con.gridx = 0;
		con.gridy = 0;
		mainframe.add(panelPlayers, con);
		
		panelXplayer = new ArrayList<JPanel>();
		labelXplayer = new ArrayList<JLabel>();
		
		drawPlayer();
		
		mainframe.setVisible(true);
		
	// BEFORE GAME
		
		
		
		// create player
		DialogPlayer myPlayers = new DialogPlayer(mainframe);
		playerlist.clear();
		playerlist = myPlayers.getPlayer();
		
		drawPlayer();
		
		DialogCharacters myFigures = new DialogCharacters(playerlist, mainframe);
	}
	
	public static void drawPlayer(){
		
		for (JPanel curPanel : panelXplayer){
			curPanel.setVisible(false);
			curPanel = null;
		}
		for (JLabel curLabel : labelXplayer){
			curLabel.setVisible(false);
			curLabel = null;
		}
		
		panelXplayer.clear();
		labelXplayer.clear();
		
		Set<String> playerset = playerlist.keySet();
		int size = playerlist.size();
		
		for (int i=1; i<=size; i++){
			for (String player : playerset){
				int num = playerlist.get(player).getNumber();
				
				if (i == num){
					panelXplayer.add(new JPanel(new GridLayout(0,1)));
					JPanel curPanel = panelXplayer.get(num-1);
					
					con.gridy = 0;
					con.gridx = num-1;
					panelPlayers.add(curPanel, con);
					
					labelXplayer.add(new JLabel(playerlist.get(player).name()));
					curPanel.add(labelXplayer.get(num-1));
					break;
				}
			}
		}
		
		mainframe.pack();
	}
	
}
