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

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class DialogSet extends JDialog implements ActionListener{

	private static final long serialVersionUID = 1419568310395863251L;
	
	// general
	private HashMap<String, Player> playerlist;
	private int numberSets;
	private ArrayList<String> returnPlayer;
	
	// gui
	private ArrayList<JButton> buttons;
	private JButton buttonAcc;
	
	public ArrayList<String> getPlayer(){ return returnPlayer; }
	
	public DialogSet(HashMap<String, Player> _playerlist,
			JFrame _frame,
			int _number,
			String _head,
			ArrayList<String> _command,
			String _note,
			String _param){
		// initialize
		super(_frame, true);
		setTitle("Mafiamanager");
		playerlist = _playerlist;
		numberSets = _number;
		returnPlayer = new ArrayList<String>();
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.anchor = GridBagConstraints.CENTER;
		con.insets = new Insets(0,0,5,0);
		
		con.gridwidth = GridBagConstraints.REMAINDER;
		con.gridy = 0;
		
		if (!_head.equals("")){
			JLabel label = new JLabel(_head);
			add(label, con);
			con.gridy++;
		}
		
		if (_command.size() != 0){
			for (String command : _command){
				JLabel label = new JLabel(command);
				label.setForeground(new Color(0,180,0));
				add(label, con);
				con.gridy++;
			}
		}
		
		if (!_note.equals("")){
			JLabel note = new JLabel(_note);
			note.setFont(note.getFont().deriveFont(Font.PLAIN));
			add(note, con);
			con.gridy++;
		}
		
		Set<String> playerset = playerlist.keySet();
		int size = playerlist.size();
		
		buttons = new ArrayList<JButton>();
		con.gridwidth = 1;
		con.gridx = GridBagConstraints.RELATIVE;
		for (int i=1; i<=size; i++){
			if (!(i==1)) { con.insets = new Insets(0,5,5,0); }
			

			for (String playerStr : playerset){
				int num = playerlist.get(playerStr).number;
				
				if (i == num){
					JButton buttonSet = new JButton(playerStr);
					buttonSet.addActionListener(this);
					buttonSet.setActionCommand(playerStr);
					buttons.add(buttonSet);
					add(buttons.get(i-1), con);
					break;
				}
			}
		}
		con.gridy++;
		
		buttonAcc = new JButton(Messages.getString("gui.acc"));
		buttonAcc.setEnabled(false);
		buttonAcc.addActionListener(this);
		con.gridwidth = GridBagConstraints.REMAINDER;
		add(buttonAcc, con);
		
		
		if (_param.equals("all")) { }
		else if (_param.equals("onlyunknown")) { onlyunknown(); }
		else if (_param.equals("nomafia")) { nomafia(); }
		else if (_param.equals("nodead")) { nodead(); }
		else { System.err.println(Messages.getString("err.nopar")); }
		
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void onlyunknown(){
		for (JButton button: buttons){
			Player player = playerlist.get(button.getText());
			if (!player.type().equals("undefined") || !player.alive){
				button.setEnabled(false);
			}
		}
	}
	
	private void nomafia(){
		for (JButton button: buttons){
			Player player = playerlist.get(button.getText());
			if (!player.type().equals("mafia") || !player.alive){
				button.setEnabled(false);
			}
		}
	}
	
	private void nodead(){
		for (JButton button: buttons){
			Player player = playerlist.get(button.getText());
			if (!player.alive){
				button.setEnabled(false);
			}
		}
	}

	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource().equals(buttonAcc)){			
			setVisible(false);
		} else {
			JButton button = (JButton)event.getSource();
			if (button.getBackground() == Color.green){
				button.setBackground(null);
				returnPlayer.remove(event.getActionCommand());
			} else {
				button.setBackground(Color.green);	
				returnPlayer.add(event.getActionCommand());
			}
			
			int counterSet = 0;
			for (JButton curButton : buttons){
				if (curButton.getBackground() == Color.green) { counterSet++; }
			}
			
			if (counterSet == numberSets){
				buttonAcc.setEnabled(true);
			} else {
				buttonAcc.setEnabled(false);
			}
		}
	}
}
