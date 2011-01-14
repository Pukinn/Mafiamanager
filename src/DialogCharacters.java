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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class DialogCharacters  extends JDialog implements ActionListener{

	private static final long serialVersionUID = -9021187337746013906L;
	
	// general
	private Board board;
	
	// gui
	private GridBagConstraints con;
	
	private JLabel labelTxt;
	private CharacterGroup groupMafia;
	private CharacterGroup groupDetective;
	private CharacterGroup groupDoctor;
	private CharacterGroup groupTerrorist;
	private JLabel labelErrors;
	private JButton buttonAcc;
	
	public DialogCharacters(JFrame _frame, Board _board){
		super(_frame, true);
		
		// initializing
		board = _board;
		con = new GridBagConstraints();
		
		// gui
		setLayout(new GridBagLayout());
		con.gridx = 0;
		con.insets = new Insets(0,0,15,0);
		
		// text
		labelTxt = new JLabel(Messages.getString("gui.configureTxt"));
		con.gridy = 0;
		add(labelTxt, con);
		
		// CHARACTERS
		con.anchor = GridBagConstraints.LINE_END;
		
		//mafia
		groupMafia = new CharacterGroup(this, "mafia");
		con.gridy = 1;
		con.fill = GridBagConstraints.NONE;
		add(groupMafia, con);
		
		//detective
		groupDetective = new CharacterGroup(this, "detective");
		con.gridy = 2;
		con.fill = GridBagConstraints.NONE;
		add(groupDetective, con);
		
		//doctor
		groupDoctor = new CharacterGroup(this, "doctor");
		con.gridy = 3;
		con.fill = GridBagConstraints.NONE;
		add(groupDoctor, con);
		
		//terrorist
		groupTerrorist = new CharacterGroup(this, "terrorist");
		con.gridy = 4;
		con.fill = GridBagConstraints.NONE;
		add(groupTerrorist, con);
		
		// error messages
		con.gridy = 5;
		labelErrors = new JLabel(" ");
		labelErrors.setForeground(Color.red);
		add(labelErrors, con);
		
		// button accept
		buttonAcc = new JButton(Messages.getString("gui.acc"));
		buttonAcc.addActionListener(this);
		con.gridy = 6;
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
			
			// generate groups
			ArrayList<Group> groups = new ArrayList<Group>();
			
			// get groups			
			groups.addAll(groupMafia.getGroups());
			groups.addAll(groupDetective.getGroups());
			groups.addAll(groupDoctor.getGroups());
			groups.addAll(groupTerrorist.getGroups());
			
			// if no error
			if (!groupMafia.error &&
					!groupDetective.error &&
					!groupDoctor.error &&
					!groupTerrorist.error){				
				
				// check for enough player
				int amountPlayer = 0;
				for (Group group : groups){
					amountPlayer += group.groupsize();
				}
				
				if (Keys.playerlist.size() < amountPlayer){
					groups.clear();
					labelErrors.setText(Messages.getString("conf.err.character"));
					pack();
				}
				else if (amountPlayer == 0){
					groups.clear();
					labelErrors.setText(Messages.getString("conf.err.player"));
					pack();
				}
				else {
					int villager = Keys.playerlist.size() - amountPlayer;
					Keys.villager = new Group("villager", Messages.getString("conf.villager.group"), villager);
					Keys.mafia = new ArrayList<Group>();
					Keys.detectives = new ArrayList<Group>();
					Keys.doctors = new ArrayList<Group>();
					Keys.terrorists = new ArrayList<Group>();
					
					Keys.mafia.addAll(groupMafia.getGroups());
					Keys.detectives.addAll(groupDetective.getGroups());
					Keys.doctors.addAll(groupDoctor.getGroups());
					Keys.terrorists.addAll(groupTerrorist.getGroups());
					
					// write log
					board.space();
					board.line(Messages.getString("log.character"));
					for (Group group : Keys.groups){
						String mess = Messages.getString("conf."+group.group()) + " ";
						mess += group.groupname();
						mess += "("+group.groupsize()+")";
						board.line(mess);
					}
					
					// end dialog
					setVisible(false);
				}
			}
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
