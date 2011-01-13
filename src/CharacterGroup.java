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
	private int linecounter;

	// general
	public boolean error;
	
	// gui
	private GridBagConstraints con;
	private ArrayList<JTextField> names;
	private ArrayList<JTextField> numbers;
	
	public CharacterGroup(DialogCharacters _parent, String _group){
		
		// initialize
		parent = _parent;
		group = _group;
		linecounter = 0;
		names = new ArrayList<JTextField>();
		numbers = new ArrayList<JTextField>();
		con = new GridBagConstraints();
		
		// set
		setLayout(new GridBagLayout());
		con.insets = new Insets(2,2,2,2);
		con.anchor = GridBagConstraints.LINE_START;
		
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
		names.add(fieldName);
		add(fieldName, con);
		
		// number of players
		con.gridx = 2;
		JTextField fieldNum = new JTextField(5);
		numbers.add(fieldNum);
		add(fieldNum, con);
		
		// button add group
		con.gridx = 3;
		JButton buttonAdd = new JButton(Messages.getString("conf.add"));
		buttonAdd.addActionListener(this);
		add(buttonAdd, con);
		
		parent.pack();
		linecounter++;
	}
	
	public ArrayList<Group> getGroups(){
		
		ArrayList<Group> groups = new ArrayList<Group>();
		error = false;
		
		for (int i=0; i<linecounter; i++){
			// get Strings
			String strName = names.get(i).getText();
			String strNum = numbers.get(i).getText();
			
			// check lonly number
			if ((strName.equals("") ||
					strName.equals(Messages.getString("conf.err.setname"))) &&
					!strNum.equals("")){
				
				strName = "err";
				names.get(i).setText(Messages.getString("conf.err.setname"));
				error = true;
				
				System.out.println("lonly number at line "+(i+1)+" "+group);
			}
			
			if (!strNum.equals("")){
				// check invalid number
				int number = 0;
				try {
					number = Integer.parseInt(strNum);
				} catch (NumberFormatException e){
					error = true;
					numbers.get(i).setText("err");
					System.out.println("invalid number at line"+(i+1)+" "+group);
				}
				
				// add group
				groups.add(new Group(group, strName, number));
			}
			

		}
		
		return groups;
	}

	// button pressed
	public void actionPerformed(ActionEvent event) {
		newGroup();
	}
	
}
