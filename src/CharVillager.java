

import Messages;
import Player;

import java.util.ArrayList;


public class CharVillager {

	// general
	public String name;
	public ArrayList<Player> player;
	
	// to generate
	public int size;
	
	public CharVillager(int _size){
		
		// handover
		size = _size;
		
		// initialize
		name = Messages.getString("conf.villager.group");
	}
}
