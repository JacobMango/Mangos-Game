package com.aussipvp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {

	private Connection con;
	private Statement st;
	private ResultSet rs;

	public DatabaseConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "");
			st = con.createStatement();
		} catch (Exception e) {
			System.out.println("Error: " + e.getCause());
		}
	}
	
	public Connection getConnection() {
		return con;
	}
	public Statement getStatement() {
		return st;
	}
	public ResultSet getResultSet() {
		return rs;
	}
	
	public ResultSet assignResultSet(ResultSet rs) {
		return this.rs = rs;
	}

	/*
	 * public void set(String username, String password) { try { //String query
	 * = "INSERT INTO `mangosgame`(`username`, `password`) VALUES ('" + username
	 * + "','" + password + "')"; PreparedStatement pst = con.prepareStatement(
	 * "INSERT INTO `mangosgame`(`username`, `password`) VALUES (?, ?)");
	 * pst.setString(1, username); pst.setString(2, password);
	 * pst.executeUpdate(); } catch (Exception e) { System.out.println("Error: "
	 * + e.getCause()); } }
	 */
}
