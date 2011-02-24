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
import javax.swing.JSeparator;


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
	private static JMenu menPlayerfromgame;
	private static JMenu menGrouptogame;
	private static JMenu menGroupfromgame;
	private static JMenuItem menStart;
	private static JMenuItem menEnd;
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
		switchpanel = new SwitchPanel(false);

		// generate character modules

		// mafia
		switchpanel.addComponent(new ModuleMafia(overview, switchpanel, mainframe));
		// day
		switchpanel.addComponent(new ModuleDay(overview, switchpanel, mainframe));

		
		
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
				playerModules.add(new ModulePlayer(e.getActionCommand()));
				overview.paintPlayer();
				refreshMenu();
			}
		};
		
        // ACTION: delete player from game
		actDelPlayerGame = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String comm = e.getActionCommand();

				int cnt = 0;
				for (ModulePlayer mp : playerModules){
					if (mp.sName.equals(comm)){	break; }
					cnt++;
				}
				playerModules.remove(cnt);

				overview.paintPlayer();
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
		
		
	// LAYOUT MENU
		menuBar.add(menGame);
			menGame.add(menPlayertogame);
			menGame.add(menPlayerfromgame);
			menGame.addSeparator();
			menGame.add(menGrouptogame);
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
		
		
		// refresh menu "player to game"
		menPlayertogame.removeAll();
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
		
		
		// refresh menu "delete player from game"
		menPlayerfromgame.removeAll();
		for (ModulePlayer mp : playerModules){
			JMenuItem menPlayer = new JMenuItem(mp.sName);
			menPlayer.addActionListener(actDelPlayerGame);
			menPlayer.setActionCommand(mp.sName);
			menPlayerfromgame.add(menPlayer);
		}
		if (playerModules.size() == 0){
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
		
		if (playerModules.size() < 3){
			JOptionPane.showMessageDialog(mainframe,
					Messages.getString("err.noplayeringame"),
					Messages.getString("err.noplayeringamettl"),
					JOptionPane.ERROR_MESSAGE);
		}
		else {
			
			switchpanel.nextComponent();
			
			// manage menu entries
			menPlayertogame.setEnabled(false);
			menPlayerfromgame.setEnabled(false);
			menStart.setEnabled(false);
			menEnd.setEnabled(true);
			
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
		for (ModulePlayer pm : playerModules){
			pm.reset();
		}
		
		mainframe.pack();
	}
	
	private static void quit(){
		stat.shutdown();
	}

}