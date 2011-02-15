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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class DialogNewPlayer extends JDialog implements ActionListener {

	private static final long serialVersionUID = -6486508555294866021L;

	private JTextField fieldName;
	
	public DialogNewPlayer(JFrame _frame){
		super(_frame, true);
		
		// label
		JLabel lblText = new JLabel(Messages.getString("dia.addplayer"));
		add(lblText);
		
		// textfield
		fieldName = new JTextField(20);
		add(fieldName);
		
		// button
		JButton buttAcc = new JButton(Messages.getString("dia.acc"));
		buttAcc.addActionListener(this);
		add(buttAcc);
	}
	
	public void actionPerformed(ActionEvent e) {
		
	}

}
