package com.pilatch.gamesim.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
// Notice, do not import com.mysql.jdbc.* or you will have problems!
public class LoadDatabaseDriver {
    public static void main(String[] args) {
        try {
            // The newInstance() call is a work around for some broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println(ex.getClass() + " exception: "+ex.getMessage());
            return;
        }
        Connection conn = null;
        try {
        	conn = DriverManager.getConnection("jdbc:mysql://localhost/java?user=java&password=java");
        } catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return;
        }
        Statement stmt = null;
        ResultSet rs = null;
        try {        	
        	stmt = conn.createStatement();
        	//stmt.executeUpdate("INSERT INTO foo VALUES('Inserting from Java')");
        	rs = stmt.executeQuery("SELECT bar FROM foo;");
    		while(rs.next()){
    			System.out.println("bar in foo: " + rs.getString(1));
    		}
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) { } // ignore
                rs = null;
            }
        	if(stmt != null) {
        		try {        			
        			stmt.close();
        		} catch(SQLException ex) {}
        	}
        }
    }
}