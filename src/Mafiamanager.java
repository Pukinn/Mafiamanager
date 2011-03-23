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
import java.util.Random;

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
	private static JMenuItem menSettings;
	private static ActionListener actDelPlayer;
	private static ActionListener actPlayerToGame;
	private static ActionListener actDelPlayerGame;
	private static ActionListener actGroupToGame;
	
	// groups
	private static JMenuItem menMafia;
	private static ArrayList<SwitchedModule> groups;
	
	//game values
	private static GameValues gamevalues;
	

	/**
	 * 
	 * MAIN PROGRAMM
	 * 
	 */
	
// MAIN
	public static void main(String args[]){
	
		// declare game values
		gamevalues = new GameValues();
		
		// declare lists
		playernames = new ArrayList<String>();
		groups = new ArrayList<SwitchedModule>();
		
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
				((SwitchedModule)switchpanel.getActComponent()).playerPressed(marked);
			}
		};
		
		// generate overview
		overview = new Overview(actPlayerNameButton);
		
        // ACTION: general menu
		ActionListener actGeneral = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("start")){
					startGame();	
				}
				else if (e.getActionCommand().equals("end")) {
					endGame();
				}
				else if (e.getActionCommand().equals("settings")){
					DialogSettings ds = new DialogSettings(mainframe, gamevalues);
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
				
				DialogNewGroup newgroup = new DialogNewGroup(
						mainframe,
						groups,
						overview.playerModules.size(),
						comm);
				
				
				if (comm.equals("mafia")){
					groups.add(new SwitchedModuleMafia(
							overview,
							gamevalues,
							switchpanel,
							mainframe,
							newgroup.groupSize,
							newgroup.groupName));
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
		menStart.addActionListener(actGeneral);
		menStart.setActionCommand("start");
		
		// end game
		menEnd = new JMenuItem(Messages.getString("men.end"));
		menEnd.setEnabled(false);
		menEnd.addActionListener(actGeneral);
		menEnd.setActionCommand("end");
		
		// settings
		menSettings = new JMenuItem(Messages.getString("men.settings"));
		menSettings.addActionListener(actGeneral);
		menSettings.setActionCommand("settings");
		
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
			menGame.add(menStart);
			menGame.add(menEnd);
			menGame.add(menSettings);
			menGame.addSeparator();
			menGame.add(menPlayertogame);
			menGame.add(menPlayerfromgame);
			menGame.addSeparator();
			menGame.add(menGrouptogame);
				menGrouptogame.add(menMafia);
			menGame.add(menGroupfromgame);

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
	
// REFRESH MENU
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
	
// START GAME
	private static void startGame(){
		
		// error message if not enough player
		if (overview.getPlayerList().size() < 6){
			JOptionPane.showMessageDialog(mainframe,
					Messages.getString("err.noplayeringame"),
					Messages.getString("err.noplayeringamettl"),
					JOptionPane.ERROR_MESSAGE);
		}
		// start game
		else {
			
			// MANAGE MENU ENTRIES
			menPlayertogame.setEnabled(false);
			menPlayerfromgame.setEnabled(false);
			menStart.setEnabled(false);
			menEnd.setEnabled(true);
			
			// set module list
			setModuleList();
			
			// set game running
			gamevalues.running = true;
			overview.setAllEnabeled(true);
			
			// call first component
			switchpanel.nextComponent();
			((SwitchedModule)switchpanel.getActComponent()).call();
			
			mainframe.pack();
			
		}
	}
	
// SET MODULE LIST
	private static void setModuleList(){
		
		// add modules to switchpanel in the order of the tasklist
		for (String actType : gamevalues.TASKLIST){
			
			// search for modules with same grouptype as actual task
			for (SwitchedModule actGroup : groups){
				if (actGroup.groupType.equals(actType)){
					
					// add to switchpanel if not already in
					if (!switchpanel.isComponentIn(actGroup)){
						switchpanel.addComponent(actGroup);
					}
				}
			}
		}
		
		// get left player
		int orderdPlayer = 0;
		for (SwitchedModule sm : groups){
			orderdPlayer += sm.groupSize;
		}
		int leftPlayer = overview.playerModules.size() - orderdPlayer;
		
		// add day module
		switchpanel.addComponent(new SwitchedModuleDay(
				overview,
				gamevalues,
				switchpanel,
				mainframe,
				leftPlayer,
				Messages.getString("mod.name")));
		
		// SET GROUPS FOR PLAYER (AUTOMATIC SELECTION)
		if (gamevalues.selectionMode){
			
			// copy player names into temporary list
			ArrayList<String> playerToSort = new ArrayList<String>();
			for (ModulePlayer mp : overview.playerModules){
				playerToSort.add(mp.sName);
			}
			
			// set random groups for the player
			Random rnd = new Random();
			for (SwitchedModule actGroup : groups){
				
				for (int i=0; i<actGroup.groupSize; ++i){
					int rndIndex = rnd.nextInt(playerToSort.size());					// set random index
					overview.getPlayer(playerToSort.get(rndIndex)).smGroup = actGroup;	// set group to random player module
					playerToSort.remove(rndIndex);										// remove player from temporary list
				}
			}
		} 
	}
	
// END GAME
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
		
		// reset switchpanel
		switchpanel.resetList();
		
		
		mainframe.pack();
	}
	
// QUIT PROGRAMM
	private static void quit(){
		stat.shutdown();
	}

}

/**
 * 
 * GAME VALUES
 * 
 */

class GameValues{
	// game rulesets
	public boolean selectionMode;				// true = by cards; false = by program
	
	// running game
	public int round;							// round of game
	public boolean running;						// game is running
	public ArrayList<KillAt> dieingPlayer;		// dieing player
	public ArrayList<String> protectedPlayer;	// can not die
	
	// statics
	public static final String[] TASKLIST = {"mafia"};
	
	public GameValues(){
		dieingPlayer = new ArrayList<KillAt>();
		protectedPlayer = new ArrayList<String>();
		
		// game rulesets
		selectionMode = false;
		
		reset();
	}
	
	// add player to killing list
	public void kill(String _name, int _round, String _deathmanner){
		
		// add deathmanner to list if player will be killed again
		boolean makeNew = true;
		for (KillAt killed : dieingPlayer){
			if (killed.name.equals(_name) && killed.round == _round){
				killed.deathmanners.add(_deathmanner);
				makeNew = false;
				break;
			}
		}
		
		// add player to killing list if not in jet
		if (makeNew){
			dieingPlayer.add(new KillAt(_name, _round, _deathmanner));
		}
	}
	
	// reset game values for running game
	public void reset(){
		
		// game rulesets
		selectionMode = true;
		
		// running game
		round = 1;
		running = false;
		dieingPlayer.clear();
		protectedPlayer.clear();
	}
}


class KillAt{
	public String name;
	public int round;
	public ArrayList<String> deathmanners;
	
	public KillAt(String _name, int _round, String _deathmanner){
		name = _name;
		round = _round;
		deathmanners = new ArrayList<String>();
		deathmanners.add(_deathmanner);
	}
}