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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


public class DialogSettings extends JDialog implements ActionListener {
	
	private GameValues gamevalues;
	
	// radio buttons
	private JRadioButton rbAut;
	private JRadioButton rbCard;
	

	public DialogSettings(JFrame _parentframe, GameValues _gamevalues){
		super(_parentframe, true);
		
		gamevalues = _gamevalues;
		
		this.setTitle(Messages.getString("dia.settings.title"));
		
		// selection mode
		JLabel lblMode = new JLabel(Messages.getString("dia.settings.mode"));
		ButtonGroup selectionmode = new ButtonGroup();
		rbCard = new JRadioButton(Messages.getString("dia.settings.mode.cards"), gamevalues.selectionMode);
		selectionmode.add(rbCard);
		rbAut = new JRadioButton(Messages.getString("dia.settings.mode.aut"), !gamevalues.selectionMode);
		selectionmode.add(rbAut);
		
		// accept button
		JButton buttonAcc = new JButton(Messages.getString("dia.settings.acc"));
		buttonAcc.addActionListener(this);
		
		// spacer panel
		JPanel spacerpanel = new JPanel();
		spacerpanel.setPreferredSize(new Dimension(40,1));
		
		// Layout
		this.setLayout(new GridBagLayout());
		GridBagConstraints con = new GridBagConstraints();
		
		con.gridx = 0;
		con.gridy = 0;
		con.gridwidth = 2;
		con.gridheight = 1;
		con.anchor = GridBagConstraints.LINE_START;
		con.insets = new Insets(5, 5, 5, 5);
		this.add(lblMode, con);
		
		con.gridx = 0;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 2;
		con.anchor = GridBagConstraints.LINE_START;
		con.insets = new Insets(0, 0, 0, 0);
		this.add(spacerpanel, con);
		
		con.gridx = 1;
		con.gridy = 1;
		con.gridwidth = 1;
		con.gridheight = 1;
		con.anchor = GridBagConstraints.LINE_START;
		con.insets = new Insets(0, 5, 0, 5);
		this.add(rbCard, con);
		
		con.gridx = 1;
		con.gridy = 2;
		con.gridwidth = 1;
		con.gridheight = 1;
		con.anchor = GridBagConstraints.LINE_START;
		con.insets = new Insets(0, 5, 0, 5);
		this.add(rbAut, con);
		
		con.gridx = 0;
		con.gridy = 3;
		con.gridwidth = GridBagConstraints.REMAINDER;
		con.gridheight = 1;
		con.anchor = GridBagConstraints.CENTER;
		con.insets = new Insets(10, 5, 5, 5);
		this.add(buttonAcc, con);
		
		
		this.pack();
		this.setLocationRelativeTo(_parentframe);
		this.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent e) {
		
		gamevalues.selectionMode = !rbAut.isSelected();
		
		this.setVisible(false);
	}
	
}
