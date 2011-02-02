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
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class DialogDay extends JDialog implements ActionListener{

	private static final long serialVersionUID = -7503615675227726238L;
	
	// general
	private int numberSets;
	
	public ArrayList<String> returnPlayer;
	public boolean terrkilled;
	
	// gui
	private JFrame parentframe;
	private ArrayList<JButton> buttons;
	private JButton buttonAcc;
	
	
	public ArrayList<String> getPlayer(){ return returnPlayer; }
	
	public DialogDay(
			JFrame _frame,
			int _number,
			String _head,
			ArrayList<String> _command,
			String _note){
		// initialize
		super(_frame, true);
		parentframe = _frame;
		setTitle("Mafiamanager");
		numberSets = _number;
		terrkilled = false;
		returnPlayer = new ArrayList<String>();
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.anchor = GridBagConstraints.CENTER;
		con.insets = new Insets(2,2,2,2);
		
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
		
		Set<String> playerset = Keys.playerlist.keySet();
		int size = Keys.playerlist.size();
		
		buttons = new ArrayList<JButton>();
		con.gridwidth = 1;
		con.gridx = GridBagConstraints.RELATIVE;
		for (int i=1; i<=size; i++){
			
			for (String playerStr : playerset){
				int num = Keys.playerlist.get(playerStr).number;
				
				if (i == num){
					JButton buttonSet = new JButton(playerStr);
					if (Keys.playerlist.get(playerStr).alive == false) {
						buttonSet.setEnabled(false);
					}
					buttonSet.addActionListener(this);
					buttonSet.setActionCommand("player");
					buttons.add(buttonSet);
					add(buttons.get(i-1), con);
					break;
				}
			}
		}
		con.gridy++;
		
		// button accept
		buttonAcc = new JButton(Messages.getString("day.acc"));
		buttonAcc.setEnabled(false);
		buttonAcc.addActionListener(this);
		buttonAcc.setActionCommand("accept");
		con.gridwidth = GridBagConstraints.REMAINDER;
		add(buttonAcc, con);
		con.gridy++;
		
		//button nominate
		JButton buttonNom = new JButton(Messages.getString("day.nominate"));
		buttonNom.addActionListener(this);
		buttonNom.setActionCommand("nominate");
		add(buttonNom, con);
		con.gridy++;
		
		// terrorists assassination
		if (Keys.aliveTerrorists() > 0){
			JButton buttonTerrorist = new JButton(Messages.getString("day.assassination"));
			buttonTerrorist.addActionListener(this);
			buttonTerrorist.setActionCommand("assasination");
			add(buttonTerrorist, con);
			con.gridy++;
		}
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		
		if (event.getActionCommand().equals("accept")){
			setVisible(false);
		}
		else if (event.getActionCommand().equals("player")){
			JButton button = (JButton)event.getSource();
			if (button.getBackground() == Color.green){
				button.setBackground(null);
				returnPlayer.remove(button.getText());
			} else {
				button.setBackground(Color.green);	
				returnPlayer.add(button.getText());
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
		else if (event.getActionCommand().equals("nominate")){
			
			
			// TODO: nomination
			
			
		}
		else if (event.getActionCommand().equals("assasination")){

			// get terrorist
			DialogTerrorists getterr = new DialogTerrorists(parentframe);
			
			// kill player
			Keys.bufferCommand.add(Messages.getString("day.ass.blast"));
			
			DialogSet actterr = new DialogSet(
					Keys.playerlist,
					parentframe,
					1,
					"",
					Keys.bufferCommand,
					"",
					"nodead");
			Keys.bufferCommand.clear();
			Keys.bufferNote.clear();
			
			Player player = Keys.playerlist.get(actterr.getPlayer().get(0));
			
			// action
			player.alive = false;
			terrkilled = true;
			
			// end day
			setVisible(false);
			
		}
	}
}
