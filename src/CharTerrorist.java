


import java.util.ArrayList;


public class CharTerrorist {
	
	// general
	public String type;
	public String name;
	public ArrayList<Player> player;
	
	// to generate
	public int size;
	
	public CharTerrorist(int _size, String _name){
		
		// handover
		size = _size;
		name = _name;
		
		// initialize
		type = "terrorist";
		player = new ArrayList<Player>();

	}
	
	// returns how much players are alive
	public int playeralive(){
		int alive = 0;
		
		for (Player p : player){
			if (p.alive) { alive++; }
		}
		
		return alive;
	}
}
