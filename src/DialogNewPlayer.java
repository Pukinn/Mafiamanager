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
import javax.swing.JTextField;


public class DialogNewPlayer extends JDialog implements ActionListener {

	private static final long serialVersionUID = -6486508555294866021L;
	
	// general
	private Statistics stat;
	private ArrayList<String> playerlist;
	
	// layout
	private JTextField fieldName;
	private JLabel lblError;
	
	
	public DialogNewPlayer(JFrame _parentframe, Statistics _stat){
		super(_parentframe, true);
		
		stat = _stat;
		playerlist = stat.getPlayer();
		
		// generate label
		JLabel lblText = new JLabel(Messages.getString("dia.addplayer"));
		
		// generate textfield
		fieldName = new JTextField(20);
		
		// generate error field
		lblError = new JLabel(" ");
		lblError.setForeground(Color.red);
		
		// generate button
		JButton buttAcc = new JButton(Messages.getString("dia.acc"));
		buttAcc.addActionListener(this);
		
		// layout
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = GridBagConstraints.RELATIVE;
		con.insets = new Insets(5, 5, 5, 5);
		
		add(lblText, con);
		add(lblError, con);
		add(fieldName, con);
		add(buttAcc, con);
		
		
		pack();
		setLocationRelativeTo(_parentframe);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		String player = fieldName.getText().trim();
		
		boolean bErr = false;
		for (String s : playerlist){
			if (s.equals(player)){ bErr = true; }
		}
		
		if (player.equals("")){
			lblError.setText(Messages.getString("dia.err.noname"));
			pack();
		}
		else if (bErr) {
			lblError.setText(Messages.getString("dia.err.eqname"));
			pack();
		}
		else {
			stat.addPlayer(player);
			setVisible(false);
		}
	}

}
