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


public class SaveGame {
	
	
	public SaveGame(){


	 
	    try
	    {
	        // Treiberklasse laden
	      Class.forName( "org.hsqldb.jdbcDriver" );
	    }
	    catch ( ClassNotFoundException e )
	    {
	      System.err.println( "Treiberklasse nicht gefunden!" );
	      return;
	    }
	 
	    Connection con = null;
	 
	    try
	    {
	      con = DriverManager.getConnection(
	              "jdbc:hsqldb:file:data/db; shutdown=true", "manager", "manager" );
	      Statement stmt = con.createStatement();
	 
	      // Alle Kunden ausgeben
	      String sql = "SELECT * FROM Customer";
	      ResultSet rs = stmt.executeQuery(sql);
	 
	      while ( rs.next() )
	      {
	        String id = rs.getString(1);
	        String firstName = rs.getString(2);
	        String lastName = rs.getString(3);
	        System.out.println(id + ", " + firstName + " " + lastName);
	      }
	 
	      // Resultset schließen
	      rs.close();
	 
	      // Statement schließen
	      stmt.close();
	    }
	    catch ( SQLException e )
	    {
	      e.printStackTrace();
	    }
	    finally
	    {
	      if ( con != null )
	      {
	        try {
	            con.close();
	            } catch ( SQLException e ) {
	                e.printStackTrace();
	            }
	      }
	    }
	    }
	
	
}
