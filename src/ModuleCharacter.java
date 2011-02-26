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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


public abstract class ModuleCharacter extends JPanel{
	
	// interacting overview panel
	private Overview overview;
	
	// layout
	private JLabel round;
	private GameValues gamevalues;
	private JScrollPane scrpanInfoText;
	private JPanel panInfoText;
	private GridBagConstraints conInfoText;
	private JPanel panButtons;
	private JButton buttonAcc;
	public JPanel panFlexButtons;
	

	public ModuleCharacter(Overview _overview, String _title, GameValues _gamevalues){
		
		gamevalues = _gamevalues;
		
		// get interacting overview panel
		overview = _overview;
		
		// generate labels
		JLabel title = new JLabel(_title);
		round = new JLabel(Messages.getString("mod.round") + ": " + gamevalues.round);
		
		// generate panel for infotext
		panInfoText = new JPanel(new GridBagLayout());
		conInfoText = new GridBagConstraints();
		conInfoText.gridx = 0;
		conInfoText.gridy = GridBagConstraints.RELATIVE;
		conInfoText.insets = new Insets(5, 5, 5, 5);
		
		// generate scrollable panel for infotext
		scrpanInfoText = new JScrollPane();
		scrpanInfoText.setViewportView(panInfoText);
		int sizeX = overview.getPreferredSize().width;
		scrpanInfoText.setPreferredSize(new Dimension(sizeX,150));
		scrpanInfoText.setBorder(null);
		
		// generate panel for flexible buttons
		panFlexButtons = new JPanel();
		panFlexButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// generate accept action listener
		ActionListener accListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptAction();
			}
		};
		
		// generate standart accept button
		buttonAcc = new JButton(Messages.getString("mod.acc"));
		buttonAcc.addActionListener(accListener);
		buttonAcc.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		// generate panel for buttons
		panButtons = new JPanel();
		panButtons.setLayout(new BoxLayout(panButtons, BoxLayout.LINE_AXIS));
		
		// layout
		setLayout(new GridBagLayout());
		GridBagConstraints conModule = new GridBagConstraints();
		conModule.gridx = 0;
		conModule.gridy = GridBagConstraints.RELATIVE;
		
		add(round, conModule);
		add(title, conModule);
		add(scrpanInfoText, conModule);
		add(panButtons, conModule);
			panButtons.add(panFlexButtons);
			panButtons.add(Box.createHorizontalGlue());
			panButtons.add(buttonAcc);
		
	}

	
// ADDING TEXTS
	// note
	public void addNote(String _note){
		JLabel note = new JLabel(_note);
		note.setFont(note.getFont().deriveFont(Font.PLAIN));
		panInfoText.add(note, conInfoText);
		redraw();
	}

	// command
	public void addCommand(String _com){
		JLabel command = new JLabel(_com);
		command.setForeground(new Color(0,180,0));
		panInfoText.add(command, conInfoText);
		redraw();
	}
	
	// error
	public void addError(String _err){
		JLabel error = new JLabel(_err);
		error.setForeground(Color.red);
		panInfoText.add(error, conInfoText);
		redraw();
	}
	
// CALL
	public void call(){
		calling();
		redraw();
	}
	
	
// REDRAW MODULE
	public void redraw(){
		round.setText(Messages.getString("mod.round") + ": " + gamevalues.round);
		
		revalidate();
		JScrollBar verBar = scrpanInfoText.getVerticalScrollBar();
		verBar.setValue(verBar.getMaximum());
	}

// ACCEPT BUTTON
	// enable/disable button
	public void buttonEnabled(boolean _enabled){
		buttonAcc.setEnabled(_enabled);
	}
	
	// set visible button
	public void buttonVisible(boolean _visible){
		buttonAcc.setVisible(_visible);
	}
	
	// action
	abstract void acceptAction();
	// calling
	abstract void calling();
	// event from player modules
	abstract void playerPressed(ArrayList<String> _marked);
}
