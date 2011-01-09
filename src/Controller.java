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
			for (String playerStr : playerset){
				int num = playerlist.get(playerStr).number;
				
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
		mafia();
		detective();
		
		if (Keys.round == 1){
			Set<String> playerset = playerlist.keySet();
			for (String playerStr : playerset){
				if (playerlist.get(playerStr).character == 0){
					playerlist.get(playerStr).character = 1;
				}
			}

		}
	}
	
	private void day(){
		
		Keys.round++;
	}
	
	private void doctor(){ if (Keys.doctor > 0){
		// first night
		if (Keys.round == 1){
			DialogSet dialog = new DialogSet(playerlist, frame, Keys.doctor, Messages.getString("gui.whosdoctor"));
			ArrayList<String> doctors = dialog.getPlayer();
			for (String doctor : doctors){
				playerlist.get(doctor).character = 4;
			}
		}
	}
	}
	
	private void mafia(){ if (Keys.mafia > 0){
		// first night
		if (Keys.round == 1){
			DialogSet dialog = new DialogSet(playerlist, frame, Keys.mafia, Messages.getString("gui.whosmafia"));
			ArrayList<String> mafias = dialog.getPlayer();
			for (String mafia : mafias){
				playerlist.get(mafia).character = 2;
			}
		}
		
	}
	}
	
	private void detective(){ if (Keys.detective > 0){
		// first night
		if (Keys.round == 1){
			DialogSet dialog = new DialogSet(playerlist, frame, Keys.detective, Messages.getString("gui.whosdetective"));
			ArrayList<String> detectives = dialog.getPlayer();
			for (String detective : detectives){
				playerlist.get(detective).character = 2;
			}
		}
		
	}	
	}
	
	public void actionPerformed(ActionEvent event) {
		
		if (event.getActionCommand().equals("startnight")){
			night();
			buttonStartnight.setVisible(false);
			frame.pack();
		}
		else {
		}
	}

}
