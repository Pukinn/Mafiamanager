


import java.util.ArrayList;


public class CharMafia {
	
	// general
	public String type;
	public String name;
	public ArrayList<Player> player;
	
	// to generate
	public int size;
	
	public CharMafia(int _size, String _name){
		
		// handover
		size = _size;
		name = _name;
		
		// initialize
		type = "mafia";

	}
}
