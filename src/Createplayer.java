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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Createplayer extends JPanel implements ActionListener{

	private static final long serialVersionUID = 6208935351597319699L;
	
	private Map<String, Player> playerlist;

	
	// GUI
	private JFrame frame;
	
	private JButton buttonAdd;
	
	private int counterPan;
	private ArrayList<JPanel> panelx;
		private ArrayList<JLabel> labelx;
		private ArrayList<JTextField> fieldx;
	
	public Createplayer(Map<String, Player> _playerlist, JFrame _frame){
		playerlist = _playerlist;
		frame = _frame;
		
		this.setLayout(new GridLayout(0,1));
	}
	
	public void create(){
		buttonAdd = new JButton(Messages.getString("gui.add"));
		buttonAdd.addActionListener(this);
		this.add(buttonAdd);
		
		panelx = new ArrayList<JPanel>();
		labelx = new ArrayList<JLabel>();
		fieldx = new ArrayList<JTextField>();
		
		counterPan = 0;
		for (int i=0; i<5; i++){
			addPlayer();
		}
	}
	
	public void addPlayer(){
		int player = counterPan+1;
		panelx.add(new JPanel(new GridLayout(1,2)));
		labelx.add(new JLabel(Messages.getString("gui.player")+" "+player));
		fieldx.add(new JTextField(15));
		
		panelx.get(counterPan).add(labelx.get(counterPan));
		panelx.get(counterPan).add(fieldx.get(counterPan));
		this.add(panelx.get(counterPan));
		
		counterPan++;
	}
	
	public void namePlayers(){
		
		System.out.println(Messages.getString("typePlayer")); //$NON-NLS-1$
		Scanner scanner = new Scanner(System.in);
		
		while(true){
			String player = scanner.nextLine();
			
			if (player.equals(Messages.getString("ready"))){ //$NON-NLS-1$
				break;
			}
			else {
				playerlist.put(player, new Player(player));
			}
		}
	}


	public void actionPerformed(ActionEvent event) {
		
		if (event.getActionCommand().equals(Messages.getString("gui.add"))){
			addPlayer();
			frame.pack();
			this.revalidate();
		}
		
	}
}
