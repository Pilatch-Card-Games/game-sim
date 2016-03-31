package com.pilatch.gamesim.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class MySQLConnection extends DatabaseConnection {
	
	public MySQLConnection(String host, String db_name, String user, String pass){
		super(host, db_name, user, pass);
	}
	
	public void connect(String host, String db_name, String user, String pass){
        try {
            // The newInstance() call is a work around for some broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            System.out.println(ex.getClass() + " exception: "+ex.getMessage());
            return;
        }
        try {
        	String url = "jdbc:mysql://"+host+"/"+db_name+"?user="+user+"&password="+pass;
        	this.conn = DriverManager.getConnection(url);
        } catch(SQLException ex) {
        	this.printError(ex);
        }
	}
}
