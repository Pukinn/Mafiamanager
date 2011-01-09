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
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class DialogSet extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1419568310395863251L;
	
	// general
	private SortedMap<String, Player> playerlist;
	private int counterSet;
	private int numberSets;
	private ArrayList<String> returnPlayer;
	
	// gui
	private ArrayList<JButton> buttons;
	
	public ArrayList<String> getPlayer(){ return returnPlayer; }
	
	public DialogSet(SortedMap<String,
			Player> _playerlist,
			JFrame _frame,
			int _number,
			String _head,
			String _param){
		// initialize
		super(_frame, true);
		playerlist = _playerlist;
		numberSets = _number;
		counterSet = 0;
		returnPlayer = new ArrayList<String>();
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = GridBagConstraints.RELATIVE;
		con.anchor = GridBagConstraints.CENTER;
		
		JLabel head = new JLabel(_head);
		con.gridwidth = GridBagConstraints.REMAINDER;
		add(head, con);
		
		Set<String> playerset = playerlist.keySet();
		int size = playerlist.size();
		
		buttons = new ArrayList<JButton>();
		con.gridwidth = 1;
		for (int i=1; i<=size; i++){
			if (!(i==1)) { con.insets = new Insets(0,5,0,0); }
			

			for (String playerStr : playerset){
				int num = playerlist.get(playerStr).number;
				
				if (i == num){
					con.gridy = 1;
					JButton buttonSet = new JButton(playerStr);
					buttonSet.addActionListener(this);
					buttonSet.setActionCommand(playerStr);
					buttons.add(buttonSet);
					add(buttons.get(i-1), con);
					break;
				}
			}
		}
		
		if (_param.equals("all")) { }
		else if (_param.equals("onlyunknown")) { onlyunknown(); }
		else if (_param.equals("nomafia")) { nomafia(); }
		else { System.err.println(Messages.getString("err.nopar")); }
		
		
		pack();
		setLocationRelativeTo(_frame);
		setVisible(true);
	}
	
	private void onlyunknown(){
		for (JButton button: buttons){
			Player player = playerlist.get(button.getText());
			if (player.character != 0 || !player.alive){
				button.setEnabled(false);
			}
		}
	}
	
	private void nomafia(){
		for (JButton button: buttons){
			Player player = playerlist.get(button.getText());
			if (player.character == 2 || !player.alive){
				button.setEnabled(false);
			}
		}
	}

	public void actionPerformed(ActionEvent event) {
		
		returnPlayer.add(event.getActionCommand());
		((JButton)event.getSource()).setEnabled(false);
		
		counterSet++;
		if (counterSet == numberSets){
			setVisible(false);
		}
	}
	
}
