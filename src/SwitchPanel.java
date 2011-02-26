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

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JPanel;


public class SwitchPanel extends JPanel {

	private boolean mode; // true=linear, false=circle
	
	// wait list for following components
	private ArrayList<Component> componentList;
	
	// default component
	private Component defaultComp;

	
// CONSTRUCTORS	
	// blank constructor
	public SwitchPanel(){
		this(true);
	}
	
	// constructor for linear switch panel
	public SwitchPanel(boolean _mode){
		this(_mode, new JPanel());
	}
	
	// constructor for linear switch panel with default component
	public SwitchPanel(boolean _mode, Component _comp){
		mode = _mode;
		componentList = new ArrayList<Component>();
		defaultComp = _comp;
		showDefault();
	}
	
	// constructor for circle switch panel
	public SwitchPanel(ArrayList<Component> _compl){
		this(false, _compl);
	}
	
	// constructor for circle switch panel
	public SwitchPanel(boolean _mode, ArrayList<Component> _compl){
		mode = _mode;
		componentList = _compl;
		nextComponent();
	}
	
// SET COMPONENTS	
	// add component to wait list
	public void addComponent(Component _comp){
		componentList.add(_comp);
	}
	
	// set default component
	public void setDefault(Component _comp){
		defaultComp = _comp;
	}

// SHOW COMPONENTS
	// show next component from wait list
	public void nextComponent(){
		removeAll();
		add(componentList.get(0));
		repaint();
		
		if (mode){
			componentList.remove(0);
		}
		else {
			componentList.add(componentList.get(0));
			componentList.remove(0);
		}
	}
	
	// show default component
	public void showDefault(){
		removeAll();
		add(defaultComp);
		repaint();
	}
	
// RESET LIST
	public void resetList(){
		componentList.clear();
	}
	
// RETURN
	// return list
	public ArrayList<Component> getList(){
		return componentList;
	}
	
	// return component
	public Component getCompFromList(int _num){
		return componentList.get(_num);
	}
	
	// return actual component
	public Component getActComponent(){
		return componentList.get(componentList.size() - 1);
	}
}
