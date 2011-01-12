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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CharacterGroup extends JPanel
							implements ActionListener{

	private static final long serialVersionUID = -1945794586033999887L;
	
	// hand overs
	private DialogCharacters parent;
	private String group;
	private Integer leftVillager;
	private JLabel labelVillager;
	private int linecounter;
	private JButton buttonAcc;
	
	// general
	private KeyListener keylisName;
	private KeyListener keylisVillager;
	private boolean onlynumbers;
	
	// gui
	private GridBagConstraints con;
	
	public CharacterGroup(
			DialogCharacters _parent,
			String _group,
			Integer _leftVillager,
			JLabel _lblVillager,
			JButton _acc){
		
		// initialize
		parent = _parent;
		group = _group;
		leftVillager = _leftVillager;
		labelVillager = _lblVillager;
		linecounter = 0;
		buttonAcc = _acc;
		onlynumbers = true;
		con = new GridBagConstraints();
		
		// set
		setLayout(new GridBagLayout());
		con.insets = new Insets(2,2,2,2);
		con.anchor = GridBagConstraints.LINE_START;
		
		// keylistener
		keylisVillager = new KeyListener() {
			public void keyReleased(KeyEvent event) {
				String number = ((JTextField)event.getSource()).getText();
				
				if (!number.equals("")){
					try {
						leftVillager -= Integer.parseInt(number);;
					} catch (NumberFormatException e){
						onlynumbers = false;
					}
				}
				
				// positive villager
				if (onlynumbers && leftVillager >= 0){
					labelVillager.setForeground(null);
					buttonAcc.setEnabled(true);
					labelVillager.setText(Messages.getString("gui.leftPlayer")+" "+leftVillager);
				}
				// negative villager
				else if (onlynumbers) {
					labelVillager.setForeground(Color.red);
					buttonAcc.setEnabled(false);
					labelVillager.setText(Messages.getString("gui.leftPlayer")+" "+leftVillager);
				}
				// no allowed string
				else {
					labelVillager.setForeground(Color.red);
					buttonAcc.setEnabled(false);
					labelVillager.setText(Messages.getString("gui.leftPlayererr"));
				}
			}

			public void keyPressed(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
			
		};
		
		// get maximum 
		
		// create first
		newGroup();
	}
	
	// add new group
	public void newGroup(){
		con.gridy = linecounter;
		
		// group
		con.gridx = 0;
		String grouptext = Messages.getString("conf."+ group +".group") + " " + (linecounter+1);
		JLabel labelGroup = new JLabel(grouptext);
		add(labelGroup, con);

		// name of group
		con.gridx = 1;
		JTextField fieldName = new JTextField(15);
		if (linecounter < 3){
			String fieldtext = "conf."+ group +".group."+(linecounter+1);
			fieldName.setText(Messages.getString(fieldtext));
		}
		fieldName.addKeyListener(keylisName);
		fieldName.setActionCommand("checksame");
		add(fieldName, con);
		
		// number of players
		con.gridx = 2;
		JTextField fieldNum = new JTextField(5);
		fieldNum.addKeyListener(keylisVillager);
		fieldNum.setActionCommand("checknumbers");
		add(fieldNum, con);
		
		// button add group
		con.gridx = 3;
		JButton buttonAdd = new JButton(Messages.getString("conf.add"));
		buttonAdd.addActionListener(this);
		add(buttonAdd, con);
		
		parent.pack();
		linecounter++;
	}

	// button pressed
	public void actionPerformed(ActionEvent event) {
		
		System.out.println("blubb");
		newGroup();
	}
	
}
