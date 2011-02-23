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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


class Mafiamanager{
	
	// general
	private static Statistics stat;
	private static ArrayList<String> playernames;
	
	// layout
	private static JFrame mainframe;
	private static Overview overview;
	private static ArrayList<ModulePlayer> playerModules;
	private static SwitchPanel switchpanel;
	
	// menu
	private static JMenuBar menuBar;
	private static JMenu menPlayertogame;
	private static JMenu menDelPlayerDB;
	private static ActionListener actDelPlayer;
	private static ActionListener actPlayerToGame;
	private static ActionListener actDelPlayerGame;
	
	
	
	public static void main(String args[]){
	
		
		
		// generate Statistics
        stat = new Statistics();
		
		// generate Player Modules
		playerModules = new ArrayList<ModulePlayer>();
		
		// generate player names
		playernames = new ArrayList<String>();
		
		// generate frame
		mainframe = new JFrame();
		mainframe.setTitle("Mafiamanager");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// generate overview
		overview = new Overview(playerModules);
		
		// generate switch panel
		switchpanel = new SwitchPanel(false, new ModuleBeforeGame(overview));

		// generate character modules
		// mafia
		switchpanel.addComponent(new ModuleMafia(overview, "1", switchpanel, mainframe));
		switchpanel.addComponent(new ModuleMafia(overview, "2", switchpanel, mainframe));
		switchpanel.addComponent(new ModuleMafia(overview, "3", switchpanel, mainframe));
		switchpanel.addComponent(new ModuleMafia(overview, "4", switchpanel, mainframe));

		
		
        // ACTION: start game
		ActionListener actStartGame = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switchpanel.nextComponent();
				mainframe.pack();
			}
		};
		
        // ACTION: add player to database
		ActionListener actPlayerToDB = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogNewPlayer newplayer = new DialogNewPlayer(mainframe, stat);
				refreshPlayer();
			}
		};
		
        // ACTION: delete player from database
		actDelPlayer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stat.deletePlayer(e.getActionCommand());
				refreshPlayer();
			}
		};

        // ACTION: add player to game
		actPlayerToGame = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playerModules.add(new ModulePlayer(e.getActionCommand()));
				overview.paintPlayer();
				refreshPlayer();
			}
		};
		
        // ACTION: delete player from game
		actDelPlayerGame = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		};
		

		
		// GENERATE MENU
		
		// menu bar
		menuBar = new JMenuBar();
		// menu game
		JMenu menGame = new JMenu(Messages.getString("men.game"));
		// submenu "add player to game"
		menPlayertogame = new JMenu(Messages.getString("men.playertogame"));
		// start game
		menGame.addSeparator();
		JMenuItem menStart = new JMenuItem(Messages.getString("men.start"));
		menStart.addActionListener(actStartGame);
		// menu player
		JMenu menPlayer = new JMenu(Messages.getString("men.player"));
		// submenu "delete player form db"
		menDelPlayerDB = new JMenu(Messages.getString("men.delplayerdb"));
		// add player
		JMenuItem menPlayerToDB = new JMenuItem(Messages.getString("men.playertodb"));
		menPlayerToDB.addActionListener(actPlayerToDB);
		
		menuBar.add(menGame);
			menGame.add(menPlayertogame);
				refreshPlayer();
			menGame.add(menStart);
		menuBar.add(menPlayer);
			menPlayer.add(menPlayerToDB);
			menPlayer.add(menDelPlayerDB);
				
		
		// LAYOUT
		mainframe.setJMenuBar(menuBar);
		mainframe.add(overview, BorderLayout.CENTER);
		mainframe.add(switchpanel, BorderLayout.PAGE_END);
		
		
		
		mainframe.pack();
		mainframe.setVisible(true);
	}
	
	public static void refreshPlayer(){
		
		menPlayertogame.removeAll();
		menDelPlayerDB.removeAll();
		
		playernames.clear();
		playernames = stat.getPlayer();
		
		for (String s : playernames){
			
			boolean make = true;
			for (ModulePlayer mp : playerModules){
				if (s.equals(mp.sName)) { make = false; }
			}
			
			if (make){
				JMenuItem menPlayer = new JMenuItem(s);
				menPlayer.addActionListener(actPlayerToGame);
				menPlayer.setActionCommand(s);
				menPlayertogame.add(menPlayer);
			}
		}
		
		for (String s : playernames){
			JMenuItem menPlayer = new JMenuItem(s);
			menPlayer.addActionListener(actDelPlayer);
			menPlayer.setActionCommand(s);
			menDelPlayerDB.add(menPlayer);
		}
		
		menuBar.revalidate();
	}
	
	public static void quit(){
		stat.shutdown();
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