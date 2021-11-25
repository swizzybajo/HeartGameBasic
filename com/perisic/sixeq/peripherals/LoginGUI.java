package com.perisic.sixeq.peripherals;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.perisic.sixeq.engine.GameDb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginGUI extends JPanel implements ActionListener{
	
	JTextField emailField, passField;
	JButton submitButton;
	static final String DATABASE_URL = "jdbc:mysql://localhost/heartgame";
	
	Connection connection;
	Statement statement;
	ResultSet  resultSet;
	ReplaceScreen replaceScreen;
	
	
	public LoginGUI(ReplaceScreen replaceScreen) {
		
		
		super(new GridBagLayout());
		this.replaceScreen = replaceScreen;

		GridBagConstraints constraints = new GridBagConstraints();
//		setLayout( new GridLayout(3,1,10,20));
		
		emailField = new JTextField(25);
		passField = new JTextField(25);
		submitButton = new JButton();
		
		
		
		
		
		
		submitButton.setText("LOGIN");

		JPanel emailPanel = new JPanel(new FlowLayout());
		JPanel passPanel = new JPanel(new FlowLayout());
		
		submitButton.setSize(new Dimension(5, 25));

		submitButton.setMaximumSize(new Dimension(5, 25));
		
		
		
		
		emailPanel.add(new JLabel("Email:"));
		emailPanel.add(emailField);
		
		passPanel.add(new JLabel("Password:"));
		passPanel.add(passField);
		

		
		
		
		
		
		
		submitButton.addActionListener(this);
		setAlignmentX(LEFT_ALIGNMENT);

		
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		add(emailPanel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;


		add(passPanel, constraints);
		
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 1;
		constraints.fill = GridBagConstraints.RELATIVE;

		add(submitButton, constraints);
		
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		GameDb gameDb = new GameDb();
		try {
			
			String queryString =String.format("SELECT COUNT(*) FROM users WHERE username='%s' AND password='%s'",emailField.getText(),passField.getText());
			gameDb.query(queryString);
			resultSet = gameDb. getResultSet();
			ResultSetMetaData resultSetMetaData = gameDb.getMetaData();
			int colCount = resultSetMetaData.getColumnCount();
			
			if(colCount >0 ) {
				resultSet.next();
				if((Long)resultSet.getObject(1)  >0) { 
				
				this.replaceScreen.moveToGame(true, emailField.getText());
				return;
				}
			}
			this.replaceScreen.moveToGame(false,emailField.getText());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			gameDb.closeResource();
		}
		
		
		
	}
	
	interface ReplaceScreen {
		void moveToGame(boolean login, String username);
	}

}
