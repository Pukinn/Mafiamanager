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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DialogNewGroup extends JDialog implements ActionListener {

	private static final long serialVersionUID = -6486508555294866021L;
	
	// general
	private ArrayList<SwitchedModule> groups;
	private String groupType;
	
	// layout
	private JTextField fieldName;
	private JLabel lblError;
	private JComboBox cbPlayer;
	
	// out
	public String groupName;
	public int groupSize;
	
	
	public DialogNewGroup(
							JFrame _parentframe,
							ArrayList<SwitchedModule> _groups,
							int _actualPlayer,
							String _groupType
							){
		
		super(_parentframe, true);
		
		groups = _groups;
		groupType = _groupType;
		
		// generate label
		JLabel lblText = new JLabel(Messages.getString("dia.addgroup"));
		
		// generate textfield
		fieldName = new JTextField(20);
		
		// generate error field
		lblError = new JLabel(" ");
		lblError.setForeground(Color.red);
		
		// generate button
		JButton buttAcc = new JButton(Messages.getString("dia.acc"));
		buttAcc.addActionListener(this);
		
		// generate panel groupsize
		JPanel panSize = new JPanel();
		
		// generate label size
		JLabel lblSize = new JLabel(Messages.getString("dia.addgroup.size"));
		
		// get left player
		int orderdPlayer = 0;
		for (SwitchedModule sm : groups){
			orderdPlayer += sm.groupSize;
		}
		int leftPlayer = _actualPlayer - orderdPlayer;
		
		// generate combo box
		cbPlayer = new JComboBox();
		for (int i = 1; i <= leftPlayer; ++i){
			cbPlayer.addItem(i);
		}
		cbPlayer.setMaximumRowCount(10);
		
		
		// layout
		this.setTitle(Messages.getString("dia.addgroup.title"));
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = GridBagConstraints.RELATIVE;
		con.insets = new Insets(5, 5, 5, 5);
		
		this.add(lblText, con);
		this.add(lblError, con);
		this.add(fieldName, con);
		this.add(panSize, con);
			panSize.add(lblSize);
			panSize.add(cbPlayer);
		this.add(buttAcc, con);
		
		
		this.pack();
		this.setLocationRelativeTo(_parentframe);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String group = fieldName.getText().trim();
		
		boolean bErr = false;
		for (SwitchedModule sm : groups){
			if (sm.groupName.equals(group)){ bErr = true; }
		}
		
		if (group.equals("")){
			lblError.setText(Messages.getString("dia.err.noname"));
			pack();
		}
		else if (bErr) {
			lblError.setText(Messages.getString("dia.err.eqname"));
			this.pack();
		}
		else {

			groupName = group;
			groupSize = cbPlayer.getSelectedIndex()+1;
			
			this.setVisible(false);
		}
	}

}
