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
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	private static final long serialVersionUID = 6909929629490253770L;

	private GridBagConstraints con;
	
	public Board(){
		setLayout(new GridBagLayout());
		con = new GridBagConstraints();
		con.gridy = GridBagConstraints.RELATIVE;
		con.anchor = GridBagConstraints.WEST;
	}
	
	public void command(String _text){
		con.gridwidth = 1;
		
		con.gridx = 0;
		
		JLabel command = new JLabel(Messages.getString("board.command"));
		command.setForeground(Color.green);
		command.setFont(command.getFont().deriveFont(Font.ITALIC));
		add(command, con);
		
		con.gridx = 1;
		JLabel text = new JLabel(_text);
		text.setForeground(Color.green);
		text.setFont(text.getFont().deriveFont(Font.PLAIN));
		add(text, con);
	}
	
	public void note(String _text){
		con.gridwidth = 1;
		
		con.gridx = 0;
		JLabel note = new JLabel(Messages.getString("board.note"));
		note.setFont(note.getFont().deriveFont(Font.ITALIC));
		add(note, con);
		
		con.gridx = 1;
		JLabel text = new JLabel(_text);
		text.setFont(text.getFont().deriveFont(Font.PLAIN));
		add(text, con);
	}
	
	public void head(String _text){
		con.gridwidth = 2;
		con.gridx = 0;
		JLabel text = new JLabel(_text);
		add(text, con);
	}
}
