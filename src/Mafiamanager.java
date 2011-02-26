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
import javax.swing.JOptionPane;


class Mafiamanager{
	
	// general
	private static Statistics stat;
	private static ArrayList<String> playernames;
	
	// layout
	private static JFrame mainframe;
	private static Overview overview;
	private static ActionListener actPlayerNameButton;
	private static SwitchPanel switchpanel;
	
	// menu
	private static JMenuBar menuBar;
	private static JMenu menPlayertogame;
	private static JMenu menPlayerfromgame;
	private static JMenu menGrouptogame;
	private static JMenu menGroupfromgame;
	private static JMenuItem menStart;
	private static JMenuItem menEnd;
	private static JMenu menDelPlayerDB;
	private static ActionListener actDelPlayer;
	private static ActionListener actPlayerToGame;
	private static ActionListener actDelPlayerGame;
	private static ActionListener actGroupToGame;
	
	// groups
	private static JMenuItem menMafia;
	private static ArrayList<String> mafiagroups;
	
	//game values
	private static GameValues gamevalues;
	
	
	public static void main(String args[]){
	
		// declare game values
		gamevalues = new GameValues();
		
		// declare lists
	//	playerModules = new ArrayList<ModulePlayer>();
		playernames = new ArrayList<String>();
		mafiagroups = new ArrayList<String>();
		
		// generate statistics
        stat = new Statistics();
		
		// generate frame
		mainframe = new JFrame();
		mainframe.setTitle("Mafiamanager");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// generate switch panel
		switchpanel = new SwitchPanel(false);
		
		
		// ACTION: module player; namebutton pressed
		actPlayerNameButton = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> marked = overview.getMarked();
				((ModuleCharacter)switchpanel.getActComponent()).playerPressed(marked);
			}
		};
		
		// generate overview
		overview = new Overview(actPlayerNameButton);
		
        // ACTION: start game
		ActionListener actStartEnd = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("start")){
					startGame();	
				}
				else {
					endGame();
				}
			}
		};
		
        // ACTION: add player to database
		ActionListener actPlayerToDB = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogNewPlayer newplayer = new DialogNewPlayer(mainframe, stat);
				refreshMenu();
			}
		};
		
        // ACTION: delete player from database
		actDelPlayer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stat.deletePlayer(e.getActionCommand());
				refreshMenu();
			}
		};

        // ACTION: add player to game
		actPlayerToGame = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				overview.addPlayer(e.getActionCommand());
				overview.paintPlayer();
				refreshMenu();
			}
		};
		
		
        // ACTION: delete player from game
		actDelPlayerGame = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				overview.removePlayer(e.getActionCommand());
				refreshMenu();
			}
		};
		
        // ACTION: add group to game
		actGroupToGame = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String comm = e.getActionCommand();
				
				if (comm.equals("mafia")){
					DialogNewGroup newgroup = new DialogNewGroup(mainframe, mafiagroups);
				}
				
				refreshMenu();
			}
		};
		

		
	// GENERATE MENU
		
		// menu bar
		menuBar = new JMenuBar();
		// menu game
		JMenu menGame = new JMenu(Messages.getString("men.game"));
		// submenu "add player to game"
		menPlayertogame = new JMenu(Messages.getString("men.playertogame"));
		// submenu "remove player from game"
		menPlayerfromgame = new JMenu(Messages.getString("men.playerfromgame"));
		// submenu "add group to game"
		menGrouptogame = new JMenu(Messages.getString("men.grouptogame"));
		// submenu "remove group from game"
		menGroupfromgame = new JMenu(Messages.getString("men.groupfromgame"));
		// start game
		menStart = new JMenuItem(Messages.getString("men.start"));
		menStart.addActionListener(actStartEnd);
		menStart.setActionCommand("start");
		// end game
		menEnd = new JMenuItem(Messages.getString("men.end"));
		menEnd.setEnabled(false);
		menEnd.addActionListener(actStartEnd);
		menEnd.setActionCommand("end");
		// menu player
		JMenu menPlayer = new JMenu(Messages.getString("men.player"));
		// submenu "delete player form db"
		menDelPlayerDB = new JMenu(Messages.getString("men.delplayerdb"));
		// add player
		JMenuItem menPlayerToDB = new JMenuItem(Messages.getString("men.playertodb"));
		menPlayerToDB.addActionListener(actPlayerToDB);
		
		// GROUPS
		// mafia
		menMafia = new JMenuItem(Messages.getString("men.gr.mafia"));
		menMafia.addActionListener(actGroupToGame);
		menMafia.setActionCommand("mafia");
		
	// LAYOUT MENU
		menuBar.add(menGame);
			menGame.add(menPlayertogame);
			menGame.add(menPlayerfromgame);
			menGame.addSeparator();
			menGame.add(menGrouptogame);
				menGrouptogame.add(menMafia);
			menGame.add(menGroupfromgame);
			menGame.addSeparator();
			menGame.add(menStart);
			menGame.add(menEnd);
		menuBar.add(menPlayer);
			menPlayer.add(menPlayerToDB);
			menPlayer.add(menDelPlayerDB);
			
		refreshMenu();
				
		
	// LAYOUT FRAME
		mainframe.setJMenuBar(menuBar);
		mainframe.add(overview, BorderLayout.CENTER);
		mainframe.add(switchpanel, BorderLayout.PAGE_END);
		
		mainframe.pack();
		mainframe.setVisible(true);
	}
	
	public static void refreshMenu(){
		playernames.clear();
		playernames = stat.getPlayer();
		ArrayList<String> playerlist = overview.getPlayerList();
		
		// refresh menu "player to game"
		menPlayertogame.removeAll();
		for (String s : playernames){
			
			boolean make = true;
			for (String name : playerlist){
				if (s.equals(name)) { make = false; }
			}
			
			if (make){
				JMenuItem menPlayer = new JMenuItem(s);
				menPlayer.addActionListener(actPlayerToGame);
				menPlayer.setActionCommand(s);
				menPlayertogame.add(menPlayer);
			}
		}
		
		// refresh menu "delete player from game"
		menPlayerfromgame.removeAll();
		for (String s : playerlist){
			JMenuItem menPlayer = new JMenuItem(s);
			menPlayer.addActionListener(actDelPlayerGame);
			menPlayer.setActionCommand(s);
			menPlayerfromgame.add(menPlayer);
		}
		if (playerlist.size() == 0){
			menPlayerfromgame.setEnabled(false);
		}
		else {
			menPlayerfromgame.setEnabled(true);
		}
		
		
		// refresh menu "delete player from db"
		menDelPlayerDB.removeAll();
		for (String s : playernames){
			JMenuItem menPlayer = new JMenuItem(s);
			menPlayer.addActionListener(actDelPlayer);
			menPlayer.setActionCommand(s);
			menDelPlayerDB.add(menPlayer);
		}
		
		
		menuBar.revalidate();
	}
	
	private static void startGame(){
		
		if (overview.getPlayerList().size() < 3){
			JOptionPane.showMessageDialog(mainframe,
					Messages.getString("err.noplayeringame"),
					Messages.getString("err.noplayeringamettl"),
					JOptionPane.ERROR_MESSAGE);
		}
		else {
			
			// MANAGE MENU ENTRIES
			menPlayertogame.setEnabled(false);
			menPlayerfromgame.setEnabled(false);
			menStart.setEnabled(false);
			menEnd.setEnabled(true);
			
			// SET MODULE LIST
			// mafia
			for (String s : mafiagroups){
				ModuleMafia mm = new ModuleMafia(overview, switchpanel, mainframe, s, gamevalues);
				switchpanel.addComponent(mm);
			}
			// day
			ModuleDay md = new ModuleDay(overview, switchpanel, mainframe, gamevalues);
			switchpanel.addComponent(md);
			
			// set game running
			gamevalues.running = true;
			overview.setAllEnabeled(true);
			
			((ModuleCharacter)switchpanel.getCompFromList(0)).call();
			switchpanel.nextComponent();
			mainframe.pack();
			
		}
	}
	
	private static void endGame(){
		switchpanel.showDefault();
		
		// manage menu entries
		menPlayertogame.setEnabled(true);
		menPlayerfromgame.setEnabled(true);
		menStart.setEnabled(true);
		menEnd.setEnabled(false);
		
		
		// reset player
		overview.resetGame();
		
		// reset game values
		gamevalues.reset();
		
		
		mainframe.pack();
	}
	
	private static void quit(){
		stat.shutdown();
	}

}

// GAME VALUES
class GameValues{
	public int round;		// round of game
	public boolean running;	// game is running
	
	public GameValues(){
		reset();
	}
	
	public void reset(){
		round = 0;
		running = false;
	}
}