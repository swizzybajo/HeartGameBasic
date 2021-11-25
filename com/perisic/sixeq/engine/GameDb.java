package com.perisic.sixeq.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDb {
	
	Connection connection;
	Statement statement;
	ResultSet  resultSet;
	static final String DATABASE_URL = "jdbc:mysql://localhost/heartgame";

	public ResultSet query(String query) {
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(DATABASE_URL, "root", "");
				statement = connection.createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String queryString =String.format(query);
			resultSet = statement. executeQuery(queryString);
//			return resultSet;
			/*
			 * ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); int colCount =
			 * resultSetMetaData.getColumnCount();
			 * 
			 * 
			 * 
			 * if(colCount >0 ) { resultSet.next(); if((Long)resultSet.getObject(1) >0) {
			 * 
			 * this.replaceScreen.moveToGame(true, emailField.getText()); return; } }
			 * this.replaceScreen.moveToGame(false,emailField.getText());
			 */			/*
			 * if(colCount >0) { this.replaceScreen.moveToGame(true, emailField.getText());
			 * }else { this.replaceScreen.moveToGame(false,emailField.getText()); }
			 */			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultSet; 
	}
	
	public boolean execQuery(String query) {
		try {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(DATABASE_URL, "root", "");
				statement = connection.createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			String queryString =String.format(query);
			boolean state = statement. execute(queryString);
			return state;
//			return resultSet;
			/*
			 * ResultSetMetaData resultSetMetaData = resultSet.getMetaData(); int colCount =
			 * resultSetMetaData.getColumnCount();
			 * 
			 * 
			 * 
			 * if(colCount >0 ) { resultSet.next(); if((Long)resultSet.getObject(1) >0) {
			 * 
			 * this.replaceScreen.moveToGame(true, emailField.getText()); return; } }
			 * this.replaceScreen.moveToGame(false,emailField.getText());
			 */			/*
			 * if(colCount >0) { this.replaceScreen.moveToGame(true, emailField.getText());
			 * }else { this.replaceScreen.moveToGame(false,emailField.getText()); }
			 */			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return false; 
	}
	
	public ResultSetMetaData getMetaData() {
		try {
			return resultSet.getMetaData();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public ResultSet getResultSet() {
		try {
			return resultSet;
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	public void closeResource() {
		try {
			if (resultSet != null)
				resultSet.close();
			if(statement != null)
				statement.close();
			connection.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
