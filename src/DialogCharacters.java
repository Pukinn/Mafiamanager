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
import java.util.SortedMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;


public class DialogCharacters  extends JDialog implements ActionListener{

	private static final long serialVersionUID = -9021187337746013906L;
	
	// general
	private SortedMap<String, Player> playerlist;
	private Board board;
	private Integer leftVillager;
	
	// gui
	private GridBagConstraints con;
	
	private JLabel labelTxt;
	private JLabel labelCounter;
	private CharacterGroup groupMafia;
	private CharacterGroup groupDetective;
	private CharacterGroup groupDoctor;
	private CharacterGroup groupTerrorist;	
	private JButton buttonAcc;
	
	public DialogCharacters(SortedMap<String, Player> _playerlist, JFrame _frame, Board _board){
		super(_frame, true);
		
		// initializing
		playerlist = _playerlist;
		board = _board;
		con = new GridBagConstraints();
		
		// gui
		setLayout(new GridBagLayout());
		con.gridx = 0;
		con.insets = new Insets(0,0,5,0);
		
		// text
		labelTxt = new JLabel(Messages.getString("gui.configureTxt"));
		con.gridy = 0;
		add(labelTxt, con);
		
		// counter
		leftVillager = new Integer(playerlist.size());
		labelCounter = new JLabel(Messages.getString("gui.leftPlayer")+" "+leftVillager);
		con.gridy = 1;
		add(labelCounter, con);
		
		// CHARACTERS
		con.anchor = GridBagConstraints.LINE_END;
		
		// separator
		con.gridy = 2;
		con.fill = GridBagConstraints.HORIZONTAL;
		add(new JSeparator(), con);
		
		//mafia
		groupMafia = new CharacterGroup(
				this,
				"mafia",
				leftVillager,
				labelCounter,
				buttonAcc);
		con.gridy = 3;
		con.fill = GridBagConstraints.NONE;
		add(groupMafia, con);
		
		// separator
		con.gridy = 4;
		con.fill = GridBagConstraints.HORIZONTAL;
		add(new JSeparator(), con);
		
		//detective
		groupDetective = new CharacterGroup(
				this,
				"detective",
				leftVillager,
				labelCounter,
				buttonAcc);
		con.gridy = 5;
		con.fill = GridBagConstraints.NONE;
		add(groupDetective, con);
		
		// separator
		con.gridy = 6;
		con.fill = GridBagConstraints.HORIZONTAL;
		add(new JSeparator(), con);
		
		//doctor
		groupDoctor = new CharacterGroup(
				this,
				"doctor",
				leftVillager,
				labelCounter,
				buttonAcc);
		con.gridy = 7;
		con.fill = GridBagConstraints.NONE;
		add(groupDoctor, con);
		
		// separator
		con.gridy = 8;
		con.fill = GridBagConstraints.HORIZONTAL;
		add(new JSeparator(), con);
		
		//terrorist
		groupTerrorist = new CharacterGroup(
				this,
				"terrorist",
				leftVillager,
				labelCounter,
				buttonAcc);
		con.gridy = 9;
		con.fill = GridBagConstraints.NONE;
		add(groupTerrorist, con);
		
		// separator
		con.gridy = 10;
		con.fill = GridBagConstraints.HORIZONTAL;
		add(new JSeparator(), con);
		
		// button accept
		buttonAcc = new JButton(Messages.getString("gui.acc"));
		buttonAcc.addActionListener(this);
		con.gridy = 11;
		con.anchor = GridBagConstraints.CENTER;
		con.fill = GridBagConstraints.NONE;
		add(buttonAcc, con);
		
		
		// dialog end
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	// button Accept pressed
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == buttonAcc){
		//	Keys.villager = leftVillager;
			
		//	Keys.mafia = StringToInt(fieldx.get(0).getText());
		//	Keys.detective = StringToInt(fieldx.get(1).getText());
		//	Keys.doctor = StringToInt(fieldx.get(2).getText());
		//	Keys.terrorist = StringToInt(fieldx.get(3).getText());
			
			board.space();
			board.line(Messages.getString("log.character"));
	//		if (Keys.villager > 0) board.line(Messages.getString("villager")+"("+Keys.villager+")");
	//		if (Keys.mafia > 0) board.line(Messages.getString("mafia")+"("+Keys.mafia+")");
	//		if (Keys.detective > 0) board.line(Messages.getString("detective")+"("+Keys.detective+")");
	//		if (Keys.doctor > 0) board.line(Messages.getString("doctor")+"("+Keys.doctor+")");
	//		if (Keys.terrorist > 0) board.line(Messages.getString("terrorist")+"("+Keys.terrorist+")");
			
			setVisible(false);
		}
		
	}
	
	// convert string to integer
	public int StringToInt(String _s){
		int i = 0;
		if (!_s.equals("")){
			try { i = Integer.parseInt(_s); }
			catch (NumberFormatException e){}
		}
		return i;
	}
}
