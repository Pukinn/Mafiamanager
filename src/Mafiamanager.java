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
import java.util.SortedMap;
import java.util.TreeMap;

import javax.swing.JFrame;

class Mafiamanager{
	
	// general
	private static SortedMap<String, Player> playerlist;
	
	// GUI
	private static GridBagConstraints conFrame;
	
	private static JFrame mainframe;
		private static Controller panelController;
		private static Board board;
	
	public static void main(String args[])
	{
		// declare
		playerlist = new TreeMap<String, Player>();

		// set default player
		for (int i=1; i<=5; i++){
			String player = Messages.getString("gui.player")+" "+i;
			playerlist.put(player, new Player(player));
			playerlist.get(player).number = i;
		}
		

		// GUI
		mainframe = new JFrame();
		board = new Board(mainframe);
		panelController = new Controller(playerlist, board, mainframe);
		
		// frame
		mainframe.setLayout(new GridBagLayout());
		conFrame = new GridBagConstraints();
		
		mainframe.setTitle("Mafiamanager");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// controler
		conFrame.gridx = 0;
		conFrame.gridy = 0;
		mainframe.add(panelController, conFrame);
		panelController.player();
		
		// board
		conFrame.gridy = 1;
		conFrame.anchor = GridBagConstraints.FIRST_LINE_START;

		mainframe.add(board, conFrame);
		
		mainframe.pack();
		mainframe.setVisible(true);
		
	// BEFORE GAME
		// create player
		DialogPlayer myPlayers = new DialogPlayer(playerlist, mainframe, board);
		panelController.redrawPlayer(playerlist);
		mainframe.pack();
		
		// create figures
		DialogCharacters myCharacters = new DialogCharacters(playerlist, mainframe, board);
		
		panelController.start();
		
		mainframe.pack();
	}
}
