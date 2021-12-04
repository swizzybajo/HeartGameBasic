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

public class RegisterationGUI extends JPanel implements ActionListener{
	
	JTextField emailField, passField;
	JButton loginButton;
	JButton signupButton;

	static final String DATABASE_URL = "jdbc:mysql://localhost/heartgame";
	
	Connection connection;
	Statement statement;
	ResultSet  resultSet;
	ReplaceScreen replaceScreen;
	
	
	public RegisterationGUI(ReplaceScreen replaceScreen) {
		
		
		super(new GridBagLayout());
		this.replaceScreen = replaceScreen;

		GridBagConstraints constraints = new GridBagConstraints();
//		setLayout( new GridLayout(3,1,10,20));
		
		emailField = new JTextField(25);
		passField = new JTextField(25);
		loginButton = new JButton();
		signupButton = new JButton();
		
		
		
		
		
		
		loginButton.setText("Go to Login");

		signupButton.setText("Register");

		JPanel emailPanel = new JPanel(new FlowLayout());
		JPanel passPanel = new JPanel(new FlowLayout());
		JPanel actionPanel = new JPanel(new FlowLayout());
		
		loginButton.setSize(new Dimension(5, 25));

		loginButton.setMaximumSize(new Dimension(5, 25));
		
		signupButton.setSize(new Dimension(5, 25));

		signupButton.setMaximumSize(new Dimension(5, 25));
		
		
		
		
		emailPanel.add(new JLabel("Username:"));
		emailPanel.add(emailField);
		
		passPanel.add(new JLabel("Password:"));
		passPanel.add(passField);
		
		actionPanel.add(loginButton);
		actionPanel.add(signupButton);
		

		
		
		
		
		
		
		loginButton.addActionListener(this);
		signupButton.addActionListener(this);
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

		add(actionPanel, constraints);
		
		
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == signupButton) {
		
		GameDb gameDb = new GameDb();
		try {
			
			String queryString =String.format("INSERT INTO users (username, password) VALUES ('%s','%s')",emailField.getText(),passField.getText());
//			gameDb.query(queryString);
			int status= gameDb.execUpdate(queryString);
			System.out.println(status);
			this.replaceScreen.moveToGame(status==1?true:false, emailField.getText());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			gameDb.closeResource();
		}
		}else {
			this.replaceScreen.moveToLogin();
		}
		
		
		
	}
	
	interface ReplaceScreen {
		void moveToGame(boolean login, String username);
		void moveToLogin();
	}

}

