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
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Createplayer extends JPanel implements ActionListener{

	private static final long serialVersionUID = 6208935351597319699L;
	
	private Map<String, Player> playerlist;

	
	// GUI
	private GridBagConstraints con;
	private JFrame frame;
	
	private JLabel labelTxt;
	
	private JButton buttonAdd;
	
	private JPanel panelPlayer;
		private int counterPan;
		private ArrayList<JPanel> panelx;
			private ArrayList<JLabel> labelx;
			private ArrayList<JTextField> fieldx;
		
	private JButton buttonAcc;
	
	public Createplayer(Map<String, Player> _playerlist, JFrame _frame){
		playerlist = _playerlist;
		frame = _frame;
		
		this.setLayout(new GridBagLayout());
		con = new GridBagConstraints();
		con.fill = GridBagConstraints.VERTICAL;
		con.gridx = 0;
		con.gridy = 0;		
		
	}
	
	public void create(){
		labelTxt = new JLabel(Messages.getString("gui.createTxt"));
		this.add(labelTxt, con);
		
		buttonAdd = new JButton(Messages.getString("gui.add"));
		buttonAdd.addActionListener(this);
		con.gridy = 1;
		this.add(buttonAdd, con);
		
		panelx = new ArrayList<JPanel>();
		labelx = new ArrayList<JLabel>();
		fieldx = new ArrayList<JTextField>();
		
		panelPlayer = new JPanel(new GridLayout(0,1));
		
		counterPan = 0;
		for (int i=0; i<5; i++){
			addPlayer();
		}

		con.gridy = 2;
		this.add(panelPlayer, con);
		
		buttonAcc = new JButton(Messages.getString("gui.acc"));
		buttonAcc.addActionListener(this);
		con.gridy = 3;
		this.add(buttonAcc, con);
	}
	
	public void addPlayer(){
		int player = counterPan+1;
		panelx.add(new JPanel(new GridLayout(1,2)));
		labelx.add(new JLabel(Messages.getString("gui.player")+" "+player));
		fieldx.add(new JTextField(15));
		
		panelx.get(counterPan).add(labelx.get(counterPan));
		panelx.get(counterPan).add(fieldx.get(counterPan));
		panelPlayer.add(panelx.get(counterPan));
		
		counterPan++;
	}


	public void actionPerformed(ActionEvent event) {
		
		if (event.getActionCommand().equals(Messages.getString("gui.add"))){
			addPlayer();
			frame.pack();
			this.revalidate();
		}
		else if (event.getActionCommand().equals(Messages.getString("gui.acc"))){
			int count = 1;
			for (JTextField curField : fieldx){
				String player = curField.getText();
				
				if (!player.equals("")){
					playerlist.put(player, new Player(player));
					playerlist.get(player).setNumber(count);
					count++;
				}
			}
			
			Log.newLine("");
			Log.timestamp();
			Log.addLine(Messages.getString("log.player")+" ");
			
			Set<String> playerset = playerlist.keySet();
			int size = playerlist.size();
			
			for (int i=1; i<=size; i++){
				for (String curPlayer : playerset){
					int num = playerlist.get(curPlayer).getNumber();
					
					if (i == num){
						Log.addLine(curPlayer+"("+num+"), ");
					}
				}
			}
			Log.addLine("\n\n");
			
			this.setVisible(false);
		}
	}
}
