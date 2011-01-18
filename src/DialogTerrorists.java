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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class DialogTerrorists extends JDialog implements ActionListener {

	private static final long serialVersionUID = -4801701390297741588L;

	
	public DialogTerrorists(JFrame _frame){
		
		// initialize
		super(_frame, true);
		setTitle("Mafiamanager");
		setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		con.anchor = GridBagConstraints.CENTER;
		con.insets = new Insets(2,2,2,2);
		
		con.gridwidth = GridBagConstraints.REMAINDER;
		con.gridx = GridBagConstraints.RELATIVE;
		con.gridy = 0;
		
		// head
		JLabel labelTerrorists = new JLabel(Messages.getString("day.ass.terrorists"));
		add(labelTerrorists, con);
		
		// terrorist groups
		con.gridwidth = 1;
		con.gridy = 1;
		for (CharTerrorist terr : Keys.terrorists){
			
			// groupname
			JLabel groupname = new JLabel(terr.name + ":");
			add(groupname, con);
			
			// terrorist player
			for (Player player : terr.player){
				JButton button = new JButton(player.name);
				button.addActionListener(this);
				if (!player.alive) { button.setEnabled(false); }
				add(button, con);
			}
			
			con.gridy++;
		}
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		String strPlayer = ((JButton)event.getSource()).getText();
		Keys.playerlist.get(strPlayer).alive = false;
		setVisible(false);
	}

}
