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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Controller extends JPanel implements ActionListener{

	private static final long serialVersionUID = 5299138914080396570L;

	// general
	private SortedMap<String, Player> playerlist;
	private Board board;
	
	// phases
	private boolean phasedoctor;
	private boolean phasemafia;
	private boolean phasedetective;
	
	// gui
	private GridBagConstraints con;
	private GridBagConstraints conPlayer;
	
	private JFrame frame;
		private JPanel panelPlayers;
			private ArrayList<JPanel> panelXplayer;
				private ArrayList<JLabel> labelXplayer;
		private JPanel panelInteract;
			private JButton buttonStartnight;
	
	public Controller(SortedMap<String, Player> _playerlist, Board _board, JFrame _frame){
		playerlist = _playerlist;
		board = _board;
		frame = _frame;
		setLayout(new GridLayout(0,1));
		
		Keys.round = 1;
		
		con = new GridBagConstraints();
		conPlayer = new GridBagConstraints();
		conPlayer.gridx = 0;
		conPlayer.gridy = GridBagConstraints.RELATIVE;
		conPlayer.anchor = GridBagConstraints.CENTER;
	}
	
	public void player(){
		panelPlayers = new JPanel(new GridBagLayout());
		add(panelPlayers);
		
		con.insets = new Insets(0,10,0,10);
		
		panelXplayer = new ArrayList<JPanel>();
		labelXplayer = new ArrayList<JLabel>();
		
		redrawPlayer(playerlist);
		System.out.println("redraw: "+playerlist.size());
	}
	
	public void redrawPlayer(SortedMap<String, Player> playerlist){
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
				int num = playerlist.get(player).number;
				
				if (i == num){
					panelXplayer.add(new JPanel(new GridBagLayout()));
					JPanel curPanel = panelXplayer.get(num-1);
					
					con.gridy = 0;
					con.gridx = GridBagConstraints.RELATIVE;
					panelPlayers.add(curPanel, con);
					
					labelXplayer.add(new JLabel(playerlist.get(player).name));
					curPanel.add(labelXplayer.get(num-1), conPlayer);
					break;
				}
			}
		}
	}
	
	private void addButtons(){
		for (JLabel curLabel : labelXplayer){
			JButton buttonSet = new JButton(Messages.getString("gui.set"));
			buttonSet.addActionListener(this);
			buttonSet.setActionCommand(curLabel.getText());
			curLabel.getParent().add(buttonSet, conPlayer);
			
			frame.pack();
		}
	}
	
	private void delButtons(){
		for (JPanel curPanel : panelXplayer){
			curPanel.getComponent(1).setVisible(false);
		}
	}

	public void interact(){
		panelInteract = new JPanel(new GridLayout(1,0));
		add(panelInteract);
		
		buttonStartnight = new JButton(Messages.getString("gui.startnight"));
		buttonStartnight.addActionListener(this);
		buttonStartnight.setActionCommand("startnight");
		panelInteract.add(buttonStartnight);
	}
	
	private void night(){
		board.head(" ");
		board.head(Messages.getString("board.n.night")+" "+Keys.round);
		board.command(Messages.getString("board.c.allsleep"));
		
		Log.newLine("");
		Log.timestamp();
		Log.addLine(Messages.getString("board.n.night")+" "+Keys.round);
		Log.newLine("");
		
		// TASKLIST
		doctor();

		
	}
	
	private void day(){
		
		Keys.round++;
	}
	
	private void doctor(){ if (Keys.doctor > 0){
		phasedoctor = true;
		
		// first night
		if (Keys.round == 1){
			addButtons();
		}
	}
	}
	
	private void mafia(){ if (Keys.mafia > 0){
		phasemafia = true;
		
		// first night
		if (Keys.round == 1){
			addButtons();
		}
		
	}
	}
	
	private void detective(){ if (Keys.detective > 0){
		phasedetective = true;
		
		// first night
		if (Keys.round == 1){
			addButtons();
		}
		
	}	
	}
	
	public void actionPerformed(ActionEvent event) {
		
		int doctors = 0;
		int mafia = 0;
		int detectives = 0;
		
		if (event.getActionCommand().equals("startnight")){
			night();
			buttonStartnight.setVisible(false);
			frame.pack();
		}
		else {
			Player player = playerlist.get(event.getActionCommand());
			
			if (phasedoctor){
			player.character = 4;
			((JButton) event.getSource()).setEnabled(false);
			doctors++;
			
				if (doctors == Keys.doctor){
					delButtons();
					phasedoctor = false;
				}
			}
			else if (phasemafia){
				player.character = 2;
				((JButton) event.getSource()).setEnabled(false);
				mafia++;
				
					if (mafia == Keys.doctor){
						delButtons();
						phasemafia = false;
					}
			}
			else if (phasedetective){
				player.character = 3;
				((JButton) event.getSource()).setEnabled(false);
				detectives++;
				
					if (detectives == Keys.doctor){
						delButtons();
						phasedetective = false;
					}
			}
		}
	}

}
