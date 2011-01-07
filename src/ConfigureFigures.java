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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SortedMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ConfigureFigures  extends JPanel implements ActionListener{

	private static final long serialVersionUID = -9021187337746013906L;
	
	// general
	private SortedMap<String, Player> playerlist;
	private int counterFigures;
	private int counterPlayer;
	
	// gui
	private GridBagConstraints con;
	private KeyListener keylistener;
	
	private JFrame frame;
		private JLabel labelTxt;
		private JLabel labelCounter;
		private JPanel panelFigures;
			private ArrayList<JPanel> panelx;
				private ArrayList<JLabel> labelx;
				private ArrayList<JTextField> fieldx;
		private JButton buttonAcc;
	
	public ConfigureFigures(SortedMap<String, Player> _playerlist, JFrame _frame){
		// general
		playerlist = _playerlist;
		frame = _frame;
		
		// gui
		this.setLayout(new GridBagLayout());
		con = new GridBagConstraints();
		con.gridx = 0;
		con.gridy = 0;
	}
	
	// create and show panel
	public void create(){
		// text
		labelTxt = new JLabel(Messages.getString("gui.configureTxt"));
		this.add(labelTxt, con);
		
		// counter
		counterPlayer = playerlist.size();
		
		labelCounter = new JLabel(Messages.getString("gui.leftPlayer")+" "+counterPlayer);
		con.gridy = 1;
		this.add(labelCounter, con);
		
		// labels and textfields for figures
		panelx = new ArrayList<JPanel>();
		labelx = new ArrayList<JLabel>();
		fieldx = new ArrayList<JTextField>();
		
		panelFigures = new JPanel(new GridLayout(0,1));

		keylistener = new KeyListener() {
			public void keyPressed(KeyEvent event) {
			}
			public void keyReleased(KeyEvent event) {
				counterPlayer = playerlist.size() - getSum();
				labelCounter.revalidate();
			}
			public void keyTyped(KeyEvent event) {
			}
		};
		
		counterFigures = 0;
		addFigure(2);
		addFigure(3);
		addFigure(4);
		
		con.gridy = 2;
		this.add(panelFigures, con);
		
		// button accept
		buttonAcc = new JButton(Messages.getString("gui.acc"));
		buttonAcc.addActionListener(this);
		con.gridy = 3;
		this.add(buttonAcc, con);
		
		frame.add(this);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	// add figure
	public void addFigure(int _fig){
		
		panelx.add(new JPanel(new GridLayout(1,2)));
		labelx.add(new JLabel(Keys.IntToFigure(_fig)+":"));
		fieldx.add(new JTextField(5));
		fieldx.get(counterFigures).addKeyListener(keylistener);
		
		panelx.get(counterFigures).add(labelx.get(counterFigures));
		panelx.get(counterFigures).add(fieldx.get(counterFigures));
		panelFigures.add(panelx.get(counterFigures));
		
		counterFigures++;
	}
	
	public int getSum(){
		int sum = 0;
		
		for (JTextField curField : fieldx){
			String number = curField.getText();
			
			if (!number.equals("")){
				try {
					sum += Integer.parseInt(number);
				} catch (NumberFormatException e){
					System.err.println(Messages.getString("err.nonum"));
				}
			}
		}
		
		return sum;
	}
	
	public void configure(){
		
		System.out.println(Messages.getString("howmuchFigures"));

		Scanner scanner = new Scanner(System.in);
		
		int iPlayer = playerlist.size();
		
		System.out.println(Messages.getString("Mafia")+": ("+Messages.getString("of")+" "+iPlayer+")");
		Keys.mafia = scanner.nextInt();
		iPlayer -= Keys.mafia;
		
		System.out.println("Seelenretter: (von "+iPlayer+")");
		Keys.soulsaver = scanner.nextInt();
		iPlayer -= Keys.soulsaver;
		
		System.out.println("Detektiv: (von "+iPlayer+")");
		Keys.detective = scanner.nextInt();
		iPlayer -= Keys.detective;
		
		Keys.citizen = iPlayer;
		
		Log.newLine(Messages.getString("figures")+":");
		Log.newLine("citizens ("+Keys.citizen+"), mafia ("+Keys.mafia+"), soulsavers ("+Keys.soulsaver+"), detectives ("+Keys.detective+")");


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
