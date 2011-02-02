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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogPlayer extends JDialog implements ActionListener{

	private static final long serialVersionUID = 6208935351597319699L;
	
	// general
	private Board board;
	private int counterPlayer;

	// gui
	private GridBagConstraints con;

	private JLabel labelTxt;
	private JButton buttonAdd;
	private JPanel panelPlayer;
		private ArrayList<JPanel> panelx;
			private ArrayList<JLabel> labelx;
			private ArrayList<JTextField> fieldx;
	private JButton buttonAcc;

	public DialogPlayer(JFrame _frame, Board _board){
		super(_frame, true);
		
		// general
		board = _board;
		Log.create();
		
		// gui
		setLayout(new GridBagLayout());
		con = new GridBagConstraints();
		con.gridx = 0;
		
		// text
		labelTxt = new JLabel(Messages.getString("confp.createTxt"));
		con.gridy = 0;
		add(labelTxt, con);
		
		// button 'add player'
		buttonAdd = new JButton(Messages.getString("confp.add"));
		buttonAdd.addActionListener(this);
		con.gridy = 1;
		add(buttonAdd, con);
		
		// labels and textfields for players
		panelx = new ArrayList<JPanel>();
		labelx = new ArrayList<JLabel>();
		fieldx = new ArrayList<JTextField>();
		
		panelPlayer = new JPanel(new GridLayout(0,1));
		
		counterPlayer = 0;
		for (int i=0; i<5; i++){
			addPlayer();
		}

		con.gridy = 2;
		add(panelPlayer, con);
		
		// button accept
		buttonAcc = new JButton(Messages.getString("confp.acc"));
		buttonAcc.addActionListener(this);
		con.gridy = 3;
		add(buttonAcc, con);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	
	// add additional player
	public void addPlayer(){
		int player = counterPlayer+1;
		panelx.add(new JPanel(new GridLayout(1,2)));
		labelx.add(new JLabel(Messages.getString("confp.player")+" "+player));
		fieldx.add(new JTextField(15));
		
		panelx.get(counterPlayer).add(labelx.get(counterPlayer));
		panelx.get(counterPlayer).add(fieldx.get(counterPlayer));
		panelPlayer.add(panelx.get(counterPlayer));
		
		counterPlayer++;
	}


	public void actionPerformed(ActionEvent event) {
		
		// 'add player' pressed
		if (event.getSource() == buttonAdd){
			addPlayer();
			pack();
		}
		// 'accept' pressed
		else if (event.getSource() == buttonAcc){
			Keys.playerlist.clear();
			
			// generate players
			int count = 1;
			for (JTextField curField : fieldx){
				String player = curField.getText();
				
				if (!player.equals("")){
					Keys.playerlist.put(player, new Player(player));
					Keys.playerlist.get(player).number = count;
					count++;
				}
			}
			
			// write log
			board.space();
			board.line(Messages.getString("log.player"));
			
			// write players in log
			Set<String> playerset = Keys.playerlist.keySet();
			int size = Keys.playerlist.size();
			
			for (int i=1; i<=size; i++){
				for (String curPlayer : playerset){
					int num = Keys.playerlist.get(curPlayer).number;

					if (i == num){
						board.line(curPlayer+"("+num+")");
						break;
					}
				}
			}

			// end creating players
			setVisible(false);
		}
	}
}