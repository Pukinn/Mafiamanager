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

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	private static final long serialVersionUID = 6909929629490253770L;

	private GridBagConstraints conBoard;
	private GridBagConstraints conPan;
	private SimpleDateFormat formatter;
	private JFrame frame;
	
	public Board(JFrame _frame){
		frame = _frame;
		
		setLayout(new GridBagLayout());
		conPan = new GridBagConstraints();
		conPan.anchor = GridBagConstraints.FIRST_LINE_START;
		
		conBoard = new GridBagConstraints();
		conBoard.gridy = GridBagConstraints.RELATIVE;
		conBoard.anchor = GridBagConstraints.WEST;
		conBoard.insets = new Insets(0,10,0,0);
		
		formatter = new SimpleDateFormat("HH:mm:ss");
	}
	
	public void line(String _text){
		conBoard.gridwidth = 1;
		
		conBoard.gridx = 0;
		Date currentTime = new Date();
		String time = formatter.format(currentTime);
		JLabel note = new JLabel(time);
		note.setFont(note.getFont().deriveFont(Font.PLAIN));
		add(note, conBoard);
		Log.timestamp();
				
		conBoard.gridx = 1;
		JLabel text = new JLabel(_text);
		text.setFont(text.getFont().deriveFont(Font.PLAIN));
		add(text, conBoard);
		Log.addLine(_text + "\n");
		
		frame.pack();
	}
}
