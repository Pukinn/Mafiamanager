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
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Controller extends JPanel{

	private static final long serialVersionUID = 5299138914080396570L;

	// general
	private Board board;
	// TODO logging the game
	
	// roundsaves
	private ArrayList<String> protectedPlayer;
	private ArrayList<String> diedPlayer;
	
	// gui
	private GridBagConstraints con;
	private GridBagConstraints conPlayer;
	
	private JFrame frame;
		private JPanel panelPlayers;
			private ArrayList<JPanel> panelXplayer;
				private ArrayList<JLabel> labelXplayer;
	
				
				
	public Controller(Board _board, JFrame _frame){
		
		// initialize
		board = _board;
		frame = _frame;
		Keys.round = 1;
		protectedPlayer = new ArrayList<String>();
		diedPlayer = new ArrayList<String>();
		
		// gui
		setLayout(new GridLayout(0,1));		
		con = new GridBagConstraints();
		conPlayer = new GridBagConstraints();
		conPlayer.gridx = 0;
		conPlayer.gridy = GridBagConstraints.RELATIVE;
		conPlayer.anchor = GridBagConstraints.CENTER;
	}

// OVERVIEW
	
	// state overview
	// TODO: will be a overview of the lifestate, waking/sleeping and all relevant informations
	public void player(){
		panelPlayers = new JPanel(new GridBagLayout());
		add(panelPlayers);
		
		con.insets = new Insets(0,10,0,10);
		
		panelXplayer = new ArrayList<JPanel>();
		labelXplayer = new ArrayList<JLabel>();
		
		redrawPlayer();
	}
	
	// redraw state overview
	public void redrawPlayer(){
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
		
		Set<String> playerset = Keys.playerlist.keySet();
		int size = Keys.playerlist.size();
		
		for (int i=1; i<=size; i++){
			for (String playerStr : playerset){
				int num = Keys.playerlist.get(playerStr).number;
				
				if (i == num){
					panelXplayer.add(new JPanel(new GridBagLayout()));
					JPanel curPanel = panelXplayer.get(num-1);
					
					con.gridy = 0;
					con.gridx = GridBagConstraints.RELATIVE;
					panelPlayers.add(curPanel, con);
					
					labelXplayer.add(new JLabel(playerStr));
					curPanel.add(labelXplayer.get(num-1), conPlayer);
					break;
				}
			}
		}
	}

// GAME LOGIC
	
	// start game
	public void start(){
		ArrayList<String> command = new ArrayList<String>();
		ArrayList<String> dealout = new ArrayList<String>();
		
		command.add(Messages.getString("deal.note"));
		
		// villager
		dealout.add(Messages.getString("conf.villager.group") + " " + Keys.villager.startsize);
		
		// mafia
		for (CharMafia mafia : Keys.mafia){
			String mess = Messages.getString("conf.mafia") + " ";
			mess += mafia.name + ": ";
			mess += mafia.size;
			dealout.add(mess);
		}
		
		// detectives
		for (CharDetective detective : Keys.detectives){
			String mess = Messages.getString("conf.detective") + " ";
			mess += detective.name + ": ";
			mess += detective.size;
			dealout.add(mess);
		}
		
		// doctors
		for (CharDoctor doctor : Keys.doctors){
			String mess = Messages.getString("conf.doctor") + " ";
			mess += doctor.name + ": ";
			mess += doctor.size;
			dealout.add(mess);
		}
		
		// terrorists
		for (CharTerrorist terrorist : Keys.terrorists){
			String mess = Messages.getString("conf.terrorist") + " ";
			mess += terrorist.name + ": ";
			mess += terrorist.size;
			dealout.add(mess);
		}
		
		// scharpings
		for (CharScharping scharping : Keys.scharpings){
			String mess = Messages.getString("conf.scharping") + " ";
			mess += scharping.name + ": ";
			mess += scharping.size;
			dealout.add(mess);
		}

		
		DialogCommand diaDealout = new DialogCommand(
				frame,
				Messages.getString("deal.head"),
				command,
				dealout);
		
		night();
	}
	
	// night actions
	private void night(){
		Keys.bufferHead = Messages.getString("night")+" "+Keys.round;
		Keys.bufferCommand.add(Messages.getString("night.sleep"));
		
	// TASKLIST
		// doctors
		for (CharDoctor doctor : Keys.doctors){
			doctor.night(frame);
			protectedPlayer.add(doctor.protectedPlayer);
		}
		// scharpings
		for (CharScharping scharping : Keys.scharpings){
			scharping.night(frame);
		}
		// terrorists
		for (CharTerrorist terrorist : Keys.terrorists){
			terrorist.night(frame);
		}
		// mafia
		for (CharMafia mafia : Keys.mafia){
			mafia.night(frame);
			
			String killed = mafia.killedPlayer;
			if (!killed.equals("none")) { 
				
				Player player = Keys.playerlist.get(killed);
				if (player.type().equals("scharping")){
					player.dieround = Keys.round + 1;
				} else {
					diedPlayer.add(killed);
				}
			}
		}
		// detectives
		for (CharDetective detective : Keys.detectives){
			detective.night(frame);
		}


		
		// after night
		// deprotect player
		for (String player : protectedPlayer){
			Keys.playerlist.get(player).isprotected = false;
		}
		
		// check for active scharpings
		for (CharScharping scharping : Keys.scharpings){
			for (Player player : scharping.player){
				
				if (player.dieround == Keys.round){ diedPlayer.add(player.name); }
			}
		}
		
		// set undefined 
		Set<String> playerset = Keys.playerlist.keySet();
		for (String strPlayer : playerset){
			Player player = Keys.playerlist.get(strPlayer);
			
			if (player.type().equals("undefined")){
				Keys.villager.player.add(player);
				player.villager = Keys.villager;
			}
		}
		
		
		day();
	}
	
	// day actions
	private void day(){
		Keys.bufferHead = Messages.getString("day")+" "+Keys.round;
		Keys.bufferCommand.add(Messages.getString("day.awake"));
		
		if (diedPlayer.size() == 0){
			Keys.bufferCommand.add(Messages.getString("day.nodied"));
		}
		else {
			Keys.bufferCommand.add(Messages.getString("day.isdied"));
			
			for (String player : diedPlayer){
				Keys.playerlist.get(player).alive = false;
				Keys.bufferNote.add(player);
			}
		}
		diedPlayer.clear();
		
		DialogCommand day = new DialogCommand(
				frame,
				Keys.bufferHead,
				Keys.bufferCommand,
				Keys.bufferNote);
		Keys.bufferCommand.clear();
		Keys.bufferNote.clear();


		
		while(true){
			checkwin();
			
			DialogDay lynch = new DialogDay(
					frame,
					1,
					Keys.bufferHead,
					Keys.bufferCommand,
					Messages.getString("day.lynch")
					);
			Keys.bufferCommand.clear();
			Keys.bufferNote.clear();
			
			if (lynch.terrkilled){
				continue;
			}
			
			Player player = Keys.playerlist.get(lynch.getPlayer().get(0));
			player.alive = false;
			
			// output
			Keys.bufferNote.add("'"+player.name+"' "+Messages.getString("day.lynched"));
			
			break;
		}
		
		DialogCommand lynced = new DialogCommand(
				frame,
				Keys.bufferHead,
				Keys.bufferCommand,
				Keys.bufferNote);
		Keys.bufferCommand.clear();
		Keys.bufferNote.clear();
		
		checkwin();
		
		Keys.round++;

		
		night();
	}
	
	// check if one party have won and exit game
	private void checkwin(){
		
		String winner = "";
		
		for (CharMafia mafia : Keys.mafia){			
			if (Keys.alivePlayer()-mafia.playeralive() == 0){
				winner = Messages.getString("deal.mafia") + " " + mafia.name;
			}
		}
		
		if (Keys.aliveBad() == 0){
			winner = Messages.getString("villager");
		}
			
		if (!winner.equals("")){
			
			Keys.bufferCommand.add(Messages.getString("win.wintext"));
			Keys.bufferNote.add(winner);
			DialogCommand win = new DialogCommand(
					frame,
					Messages.getString("win.endgame"),
					Keys.bufferCommand,
					Keys.bufferNote);
			
			System.exit(0);
		}
	}
}
