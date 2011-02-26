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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ModulePlayer
					extends JPanel
					implements ActionListener{

	// player values
	public String sName;		// the name of the player
	public int iLifevalue;		// amount of lifes of the player
	public int iDieAtRound;		// this player dies in this round
	public boolean bProtected;	// can not be killed this round
	
	// group values
	public String sGroup;		// in which group is the player
	
	// GUI
	private JButton buttonName;
	private JPanel panelStates;
	
	public ModulePlayer(String _name, ActionListener _actButtonPressed){
		
		// set default values
		sName = _name;			// set name
		iLifevalue = 1;			// player is alive
		iDieAtRound = 0;		// no die off setted
		bProtected = false;		// player is not protected
		sGroup = "undefined";
		
		
	// GUI
		
		// generate buttonName
		buttonName = new JButton(sName);
		buttonName.setEnabled(false);
		buttonName.addActionListener(_actButtonPressed);
		buttonName.addActionListener(this);
		buttonName.setActionCommand(sName);
		
		// generate state-panel
		panelStates = new JPanel();
		panelStates.setBackground(Color.black);
		panelStates.setPreferredSize(new Dimension(50, 50));
		
	// LAYOUT
		add(buttonName, BorderLayout.WEST);
		add(panelStates, BorderLayout.EAST);
	}

	// reset player to defaults
	public void reset(){
		iLifevalue = 1;			// player is alive
		iDieAtRound = 0;		// no die off setted
		bProtected = false;		// player is not protected
		sGroup = "undefined";
		buttonName.setBackground(null);
	}

	public void setNameButton(boolean _bool){
		buttonName.setEnabled(_bool);
	}
	
	public boolean isMarked(){
		boolean marked = false;
		
		if (buttonName.getBackground() == Color.green) { marked = true; }
		
		return marked;
	}
	
	public void mark(boolean _mark){
		if (_mark) { buttonName.setBackground(Color.green); }
		else { buttonName.setBackground(null); }
	}

	public void actionPerformed(ActionEvent e) {
		if (buttonName.getBackground() == Color.green){
			buttonName.setBackground(null);
		}
		else {
			buttonName.setBackground(Color.green);
		}
	}
}
