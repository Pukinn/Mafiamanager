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



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Statistics {

    Connection conn;
  
    public Statistics(){   
    	try {
    		
			Class.forName("org.hsqldb.jdbcDriver");
			conn = DriverManager.getConnection("jdbc:hsqldb:data/db",		// filenames
                    "manager",												// username
                    "manager");												// password
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

    public void shutdown() {
    	try {
    		
			Statement st = conn.createStatement();
	        st.execute("SHUTDOWN");
	        conn.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}

    }
                               
    
    // add character to database
    public void addCharacter(String _char) {
    	try {
    		
	        Statement st = conn.createStatement();
	
	        st.executeUpdate("ALTER TABLE player ADD " + _char + "_win INT");
	        st.executeUpdate("ALTER TABLE player ADD " + _char + "_loose INT");
	
	        st.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void addPlayer(String _player) {
    	try {    	
    	
	        Statement st = conn.createStatement();
	
	        st.executeUpdate("INSERT INTO player (name) VALUES ('" + _player + "')");
	
	        st.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void deletePlayer(String _player) {
    	try {
    	
	        Statement st = conn.createStatement();
	
	        st.executeUpdate("DELETE FROM player WHERE player=" + _player);
	
	        st.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void addStat(String _name, String _char, boolean _win) {
    	try {
    	
	    	String winloose = "_loose";
	    	if(_win) { winloose = "_win"; };
	    	
	    	Statement st = conn.createStatement();
	    	ResultSet rs = st.executeQuery("SELECT name, " + _char + winloose + " FROM player " +
	    									"WHERE name = '" + _name + "'");
	
	    	rs.next();
	    	int newVal = rs.getInt(2) + 1;
	    	
	    	st.executeUpdate("UPDATE player SET " + _char + winloose + " = " + newVal +
	    									"WHERE name = '" + _name + "'");
	    	
	    	rs.close();
	    	st.close();
	    	
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    


}