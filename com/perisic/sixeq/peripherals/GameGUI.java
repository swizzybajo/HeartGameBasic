package com.perisic.sixeq.peripherals;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.*;

import com.perisic.sixeq.engine.GameDb;
import com.perisic.sixeq.engine.GameEngine;
import com.perisic.sixeq.peripherals.LoginGUI.ReplaceScreen;
import com.sun.net.httpserver.Authenticator.Retry;

/**
 * A Simple Graphical User Interface for the Six Equation Game.
 * 
 * @author Marc Conrad
 *
 */
public class GameGUI extends JFrame implements ActionListener, ReplaceScreen {

	private static final long serialVersionUID = -107785653906635L;
	ResultSet  resultSet;
	String username="";
	Timer timer;
	JLabel timerLabel;
	int timeLeft = 15000;
	JPanel retryPanel = new JPanel();
	JButton retryBtn;


	/**
	 * Method that is called when a button has been pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() != retryBtn) {
		int solution = Integer.parseInt(e.getActionCommand());
		boolean correct = myGame.checkSolution(currentGame, solution);
		int score = myGame.getScore(); 
		if (correct) {
			System.out.println("YEAH!");
			currentGame = myGame.nextGame(); 
			updateLevelDb(myGame.counter);
			ImageIcon ii = new ImageIcon(currentGame);
			questArea.setIcon(ii);
			timeLeft+=5000;
			if(score > getMax().intValue() ) {
				updateScoreDb(score);
			}
			String infoText = String.format("LEVEL: %d\tHighest: %d\tAll Time High: %d\t\tGood!  Score: %d",getLastStage(), getMax(), allTimeHigh(), score);
			System.out.println(infoText);
			infoArea.setText(infoText);
		} else { 
			System.out.println("Not Correct");
			String infoText = String.format("LEVEL: %d\tHighest: %d\tAll Time High: %d\t\tOops. Try again!  Score: %d",getLastStage(), getMax(), allTimeHigh(), score);
			System.out.println(infoText);
			
			infoArea.setText(infoText);
		}}else {
			panel.setVisible(true);
            retryPanel.setVisible(false);
            timeLeft = 15000;
            moveToGame(true, username);
            
            GameGUI.this.revalidate();
            GameGUI.this.repaint();
		}
	}
	
	
	void updateScoreDb(int score) {
		GameDb gameDb = new GameDb();
		try {
			
			String queryString =String.format("UPDATE users SET highest_score=%d WHERE username='%s'",score,username);
			System.out.print(queryString);
			boolean status= gameDb.execQuery(queryString);
			System.out.print(status);
//			resultSet = gameDb. getResultSet();
//			ResultSetMetaData resultSetMetaData = gameDb.getMetaData();
//			int colCount = resultSetMetaData.getColumnCount();
			/*
			 * if(colCount >0 ) { resultSet.next(); long max = (long)resultSet.getObject(1);
			 * 
			 * 
			 * 
			 * }
			 */			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			gameDb.closeResource();
		}
		
		
	}
	
	void updateLevelDb(int level) {
		GameDb gameDb = new GameDb();
		try {
			
			String queryString =String.format("UPDATE users SET level=%d WHERE username='%s'",level,username);
			System.out.print(queryString);
			boolean status= gameDb.execQuery(queryString);
			System.out.print(status);
//			resultSet = gameDb. getResultSet();
//			ResultSetMetaData resultSetMetaData = gameDb.getMetaData();
//			int colCount = resultSetMetaData.getColumnCount();
			/*
			 * if(colCount >0 ) { resultSet.next(); long max = (long)resultSet.getObject(1);
			 * 
			 * 
			 * 
			 * }
			 */			
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			gameDb.closeResource();
		}
		
		
	}
	
	Long allTimeHigh() {
		GameDb gameDb = new GameDb();
		try {
			
			String queryString =String.format("SELECT MAX(highest_score) FROM users");
			gameDb.query(queryString);
			resultSet = gameDb. getResultSet();
			ResultSetMetaData resultSetMetaData = gameDb.getMetaData();
			int colCount = resultSetMetaData.getColumnCount();
			if(colCount >0 ) {
				resultSet.next();
				long max = (long)resultSet.getObject(1);
				return max;
					
				
			}
			
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			gameDb.closeResource();
		}
		
		return (long)0;
	}
	
	Long getMax() {
		GameDb gameDb = new GameDb();
		try {
			
			String queryString =String.format("SELECT highest_score FROM users WHERE username='%s'",username);
			 gameDb.query(queryString);
			resultSet = gameDb. getResultSet();
			ResultSetMetaData resultSetMetaData = gameDb.getMetaData();
			int colCount = resultSetMetaData.getColumnCount();
			if(colCount >0 ) {
				resultSet.next();
				long max =  (long)resultSet.getObject(1);
				return max;
					
				
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			gameDb.closeResource();
		}
		
		return (long)0;
	}
	
	Long getLastStage() {
		GameDb gameDb = new GameDb();
		try {
			
			String queryString =String.format("SELECT level FROM users WHERE username='%s'",username);
			gameDb.query(queryString);
			resultSet = gameDb. getResultSet();
			ResultSetMetaData resultSetMetaData = gameDb.getMetaData();
			int colCount = resultSetMetaData.getColumnCount();
			System.out.printf("COL SIZE: %d", colCount);
			System.out.printf("COL SIZE: %s", resultSetMetaData.getColumnName(1));

			if(colCount >0 ) {
				resultSet.next();

				long stage = (long)(resultSet.getObject(1));
				return stage;		
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			gameDb.closeResource();
		}
		
		return (long) 0.0;
	}

	JLabel questArea = null;
	GameEngine myGame = null;
	URL currentGame = null;
	JTextArea infoArea = null;
	JPanel panel;
	String player;
	LoginGUI loginGUI;
	
/**
 * Initializes the game. 
 * @param player
 */
	private void initGame(String player) {
		setSize(690, 500);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("What is the value of the heart?");
		panel = new JPanel();
		loginGUI = new LoginGUI(this);
		panel.add(loginGUI);
		
		 retryBtn = new JButton("Retry");
			retryBtn.addActionListener(this);
			retryPanel.add(retryBtn);
			retryPanel.setVisible(false);
			
		
		getContentPane().add(panel);
		panel.repaint();

	}
	
	private void initTimer() {
		
		timerLabel = new JLabel(String.format("%d",timeLeft));
		ActionListener al=new ActionListener() {
		    public void actionPerformed(ActionEvent ae) {
		    	timeLeft -= 1000;
		    	System.out.printf("TimeLeft: %d",timeLeft);
		        SimpleDateFormat df=new SimpleDateFormat("mm:ss:S");
		        timerLabel.setText(df.format(timeLeft));
		        if(timeLeft<=0)
		        {
		            timer.stop();
		            panel.setVisible(false);
		            retryPanel.setVisible(true);
		            
		            GameGUI.this.revalidate();
		            GameGUI.this.repaint();
		            
		        }
		    }
		};
		timer=new Timer(1000,al);//create the timer which calls the actionperformed method for every 1000 millisecond(1 second=1000 millisecond)
		
		timer.start();//start the task
	}
/**
 * Default player is null. 
 */
	public GameGUI() {
		super();
		initGame(null);
	}

	/**
	 * Use this to start GUI, e.g., after login.
	 * 
	 * @param player
	 */
	public GameGUI(String player) {
		super();
		initGame(player);
	}

	/**
	 * Main entry point into the equation game.
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		GameGUI myGUI = new GameGUI();
		myGUI.setVisible(true);

	}
	@Override
	public void moveToGame(boolean login, String username) {
		if(login == false) {
			return;
		}
		initTimer();
	this.username = username;
		panel.remove(loginGUI);
//		super.repaint();
		panel.setLayout(new BorderLayout());

		JPanel panelButton = new JPanel(new FlowLayout());

		myGame = new GameEngine(username, getLastStage().intValue());
		currentGame = myGame.nextGame();

		infoArea = new JTextArea(1, 40);
		
		infoArea.setEditable(false);
		infoArea.setText("What is the value of the Heart?   Score: 0");
		
		JScrollPane infoPane = new JScrollPane(infoArea);
		JPanel infoPanel = new JPanel();
		infoPanel.add(infoPane);
		infoPanel.add(timerLabel);
		
		panel.add(infoPanel,BorderLayout.NORTH);
		

		ImageIcon ii = new ImageIcon(currentGame);
		questArea = new JLabel(ii);
	    questArea.setSize(330, 600);
	    
		JScrollPane questPane = new JScrollPane(questArea);
		panel.add(questPane,BorderLayout.CENTER);
		

		for (int i = 0; i < 10; i++) {
			JButton btn = new JButton(String.valueOf(i));
			panelButton.add(btn);
			btn.addActionListener(this);
		}
		panel.add(panelButton,BorderLayout.SOUTH);
		
		
		getContentPane().add(retryPanel);

//		panel.repaint();
		super.revalidate();
		super.repaint();


		
	}
}