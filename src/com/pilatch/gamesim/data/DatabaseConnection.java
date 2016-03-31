package com.pilatch.gamesim.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public abstract class DatabaseConnection {
	protected Connection conn;
	
	//localhost, java, java, java
	public DatabaseConnection(String host, String db_name, String user, String pass){
		connect(host, db_name, user, pass);
	}
	
	//connecting may be different for each type of database
	public abstract void connect(String host, String db_name, String user, String pass);
	
	public void close(){
		try {
			this.conn.close();
		} catch(SQLException ex) {}
	}
	
	public ResultSet query(String sql){
		Statement stmt = null;
		ResultSet rs = null;
        try {        	
        	stmt = this.conn.createStatement();
        	rs = stmt.executeQuery(sql);
        } catch (SQLException ex) {
        	this.printError(ex);
        }
        return rs;
	}
	
	public int update(String sql){ //update, insert, and delete statements, returns the auto_increment key or -1
		Statement stmt = null;
		ResultSet rs = null;
		int auto_incrementKey = -1;
        try {        	
        	stmt = this.conn.createStatement();
        	stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        	rs = stmt.getGeneratedKeys();
        	if(rs.next()){
        		auto_incrementKey = rs.getInt(1);
        	}
        } catch (SQLException ex) {
        	this.printError(ex);
        } finally {
        	if(stmt != null) {
        		try {        			
        			stmt.close();
        		} catch(SQLException ex) {}
        	}
        }
        return auto_incrementKey; // -1 on failure
	}

	public int getInt(String sql){ //returns -1 on failure
		ResultSet rs = query(sql);
		try{
			if(rs.next()){
				return rs.getInt(1); //success!
			}
		} catch(SQLException ex){}
		return -1; //failure
	}
	
	public boolean getBoolean(String sql){
		ResultSet rs = query(sql);
		try{
			if(rs.next()){
				return rs.getInt(1) > 0; //success!
			}
		} catch(SQLException ex){}
		return false; //failure		
	}
	
	public String getString(String sql){
		ResultSet rs = query(sql);
		try{
			if(rs.next()){
				return rs.getString(1); //success!
			}
		} catch(SQLException ex){}
		return null; //failure		
	}	
	
	public void printError(SQLException ex){
        System.out.println("SQLException: " + ex.getMessage());
        System.out.println("SQLState: " + ex.getSQLState());
        System.out.println("VendorError: " + ex.getErrorCode());
        ex.printStackTrace();
	}
}
