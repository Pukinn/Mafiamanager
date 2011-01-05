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
import java.util.Map;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Createplayer extends JPanel implements ActionListener{

	private static final long serialVersionUID = 6208935351597319699L;
	
	private Map<String, Player> playerlist;
	
	// GUI
	private JButton buttonAdd;
	
	public Createplayer(Map<String, Player> _playerlist){
		playerlist = _playerlist;
		
		this.setLayout(new GridLayout(0,1));
	}
	
	public void create(){
		buttonAdd = new JButton(Messages.getString("gui.add"));
		buttonAdd.addActionListener(this);
		this.add(buttonAdd);
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
