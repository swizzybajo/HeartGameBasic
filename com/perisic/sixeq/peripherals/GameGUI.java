package com.perisic.sixeq.peripherals;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

import com.perisic.sixeq.engine.GameEngine;
import com.perisic.sixeq.peripherals.LoginGUI.ReplaceScreen;

/**
 * A Simple Graphical User Interface for the Six Equation Game.
 * 
 * @author Marc Conrad
 *
 */
public class GameGUI extends JFrame implements ActionListener, ReplaceScreen {

	private static final long serialVersionUID = -107785653906635L;

	/**
	 * Method that is called when a button has been pressed.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		int solution = Integer.parseInt(e.getActionCommand());
		boolean correct = myGame.checkSolution(currentGame, solution);
		int score = myGame.getScore(); 
		if (correct) {
			System.out.println("YEAH!");
			currentGame = myGame.nextGame(); 
			ImageIcon ii = new ImageIcon(currentGame);
			questArea.setIcon(ii);
			infoArea.setText("Good!  Score: "+score);
		} else { 
			System.out.println("Not Correct"); 
			infoArea.setText("Oops. Try again!  Score: "+score);
		}
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
		
		getContentPane().add(panel);
		panel.repaint();

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
		panel.remove(loginGUI);
//		super.repaint();
		panel.setLayout(new BorderLayout());

		JPanel panelButton = new JPanel(new FlowLayout());

		myGame = new GameEngine(username);
		currentGame = myGame.nextGame();

		infoArea = new JTextArea(1, 40);
		
		infoArea.setEditable(false);
		infoArea.setText("What is the value of the Heart?   Score: 0");
		
		JScrollPane infoPane = new JScrollPane(infoArea);
		panel.add(infoPane,BorderLayout.NORTH);
		

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

//		panel.repaint();
		super.revalidate();
		super.repaint();


		
	}
}