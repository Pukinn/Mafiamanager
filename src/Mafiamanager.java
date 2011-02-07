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
**/

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


class Mafiamanager{
	
	
	public static void main(String args[]){
	
		// generate Player Modules
		ArrayList<ModulePlayer> alPlayerModules = new ArrayList<ModulePlayer>();
		
		// generate new Player
		ArrayList<String> alPlayerNames = new ArrayList<String>();
		for (int i=0; i<5; ++i){
			alPlayerNames.add("Player "+i);
		}

		// generate default Players
		for (int i=1; i<=5; ++i){
			alPlayerModules.add(new ModulePlayer("Player " + i));
		}
		
		// generate frame
		JFrame mainframe = new JFrame();
		mainframe.setTitle("Mafiamanager");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// generate menu
		// menu bar
		JMenuBar menuBar = new JMenuBar();
			// menu game
			JMenu menGame = new JMenu(Messages.getString("men.game"));
			menuBar.add(menGame);	
				// submenu add player
				JMenu menPlayertogame = new JMenu(Messages.getString("men.playertogame"));
				menGame.add(menPlayertogame);
					// players
					for (String s : alPlayerNames){
						JMenuItem menPlayer = new JMenuItem(s);
						menPlayertogame.add(menPlayer);
					}
				// start game
				menGame.addSeparator();
				JMenuItem menStart = new JMenuItem(Messages.getString("men.start"));
				menGame.add(menStart);

			// menu 
				
		// generate overview
		Overview overview = new Overview(alPlayerModules);
		
		
		// LAYOUT
		mainframe.setJMenuBar(menuBar);
		mainframe.add(overview, BorderLayout.CENTER);
		
		mainframe.pack();
		mainframe.setVisible(true);
	}
}


/*
import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollBar;

class Mafiamanager{
	
	// GUI	
	private static JFrame mainframe;
		private static Controller panelController;
		private static Board board;
	
	public static void main(String args[])
	{
		// declare
		Keys.playerlist = new HashMap<String, Player>();

		// set default player
		for (int i=1; i<=5; i++){
			String player = Messages.getString("confp.player")+" "+i;
			Keys.playerlist.put(player, new Player(player));
			Keys.playerlist.get(player).number = i;
		}
		

	// GUI
		mainframe = new JFrame();
		
		board = new Board();
		panelController = new Controller(board, mainframe);
		
		// frame
		mainframe.setLayout(new BorderLayout());
		
		mainframe.setTitle("Mafiamanager");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// controller
		mainframe.add(panelController, BorderLayout.PAGE_START);
		panelController.player();
		
		// board
		mainframe.add(board, BorderLayout.CENTER);
		
		pack();
		mainframe.setVisible(true);
		
	// BEFORE GAME
		// create player
		DialogPlayer myPlayers = new DialogPlayer(mainframe, board);
		panelController.redrawPlayer();
		pack();
		
		
		// create figures
		DialogCharacters myCharacters = new DialogCharacters(mainframe, board);
		
		
		panelController.start();
	}

	
	private static void pack(){
		mainframe.pack();
		JScrollBar verBar = board.getVerticalScrollBar();
		verBar.setValue(verBar.getMaximum());
	}
}
*/