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
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Controler extends JPanel implements ActionListener{

	private static final long serialVersionUID = 5299138914080396570L;

	// general
	private static SortedMap<String, Player> playerlist;
	
	// gui
	private static GridBagConstraints con;
	
	private static JPanel panelPlayers;
		private static ArrayList<JPanel> panelXplayer;
			private static ArrayList<JLabel> labelXplayer;
	private static JPanel panelInteract;
		private static JButton buttonStartnight;
	
	public Controler(SortedMap<String, Player> _playerlist){
		playerlist = _playerlist;
		setLayout(new GridLayout(0,1));
		
		con = new GridBagConstraints();
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
			for (String player : playerset){
				int num = playerlist.get(player).getNumber();
				
				if (i == num){
					panelXplayer.add(new JPanel(new GridLayout(0,1)));
					JPanel curPanel = panelXplayer.get(num-1);
					
					con.gridy = 0;
					con.gridx = GridBagConstraints.RELATIVE;
					panelPlayers.add(curPanel, con);
					
					labelXplayer.add(new JLabel(playerlist.get(player).name()));
					curPanel.add(labelXplayer.get(num-1));
					break;
				}
			}
		}
	}

	public void actionPerformed(ActionEvent event) {
		
	}

}
