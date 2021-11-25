package com.perisic.sixeq.engine;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Main class where the games are coming from. 
 * Basic functionality
 */
public class GameEngine {
	String thePlayer = null;
	File gameFiles;

	/**
	 * Each player has their own game engine.
	 * 
	 * @param player
	 */
	public GameEngine(String player, int level) {
		counter = level;
		gameFiles = new File("com/perisic/sixeq/peripherals/assets/");
		System.out.printf("FileLength: %d\n", gameFiles.length());
		System.out.printf("FilePath: %s \n",gameFiles.getPath());
		URI i;
		try {
			i = GameEngine.class.getResource("/com/perisic/sixeq/peripherals/assets/sixeqgame_0.png").toURI();
			System.out.print(i);
//			Path dirPath = Paths.get(i);
			/*
			 * Files.list(dirPath) .forEach(p -> System.out.println("* " + p));
			 */
//			for(File a:gameFiles.listFiles()) {
//				System.out.print("FileName: "+a.getName());
//			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		thePlayer = player;
	}

	public int counter = 0;
	int score = 0; 
/*
 * Retrieves a game. This basic version only has two games that alternate.
 */
	public URL nextGame() {
//		
		
		try {
			return new File( gameFiles.getPath(),gameFiles.listFiles()[counter].getName()).toURL();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
		
			e.printStackTrace();
			return null;
		}//GameEngine.class.getResource(gameFiles.getPath()+gameFiles.listFiles()[counter++].getName());
		/*
		 * if (counter++ % 2 == 0) { return
		 * GameEngine.class.getResource("assets/game1.png"); } else { return
		 * GameEngine.class.getResource("/game2.png"); }
		 */
			}

	/**
	 * Checks if the parameter i is a solution to the game URL. If so, score is increased by one. 
	 * @param game
	 * @param i
	 * @return
	 */
	public boolean checkSolution(URL game, int i) {
		System.out.printf("CurrentCounter: %d\n",counter);
		String fielNameString = gameFiles.listFiles()[counter].getName();
		System.out.print("FileName: "+fielNameString+"\n");
		String suffixedNumberString = fielNameString.split("_")[1];
		System.out.print("SuffixedNumber: "+suffixedNumberString+"\n");
		String numberString = suffixedNumberString.substring(0, fielNameString.split("_")[1].indexOf("."));
		System.out.print("NumberString: "+numberString+"\n");
		int answer =Integer.parseInt(numberString.substring(numberString.length()-1, numberString.length()));
		System.out.printf("Answer: %d\n", answer);
		if (i == answer) {
			score++;
			counter++;
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Retrieves the score. 
	 * 
	 * @param player
	 * @return
	 */
	public int getScore() {
		return score;
	}

}
