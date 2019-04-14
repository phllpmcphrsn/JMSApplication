package com.pm.dbmodel;

import java.util.List;
import java.util.Scanner;

public class Main {

	private static Artist artistDB;
	private static Album albumDB;
	private static Song songDB;
	
	public static void main(String[] args) {
		init();
		String input;
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.println("************************************************");
			System.out.println("Welcome to the Music Library!");
			System.out.println("************************************************");
			System.out.println("A. Play Song from Playlist.\n"
					+ "B. Search for a Song.\n"
					+ "C. Search for an Artist.\n"
					+ "D. Exit.");
			System.out.print("Please choose what you would like to do (Choose A, B, C, or D): ");
			input = scan.next().toUpperCase();
			switch (input) {
//			case "A":
//				textMessage = playSong();
//				break;
			case "B":
				searchForSong();
				break;
//			case "C":
//				searchForArtist();
//				break;
			case "D":
				System.out.println("Thanks for using me.");
				closeAllDB();	
				System.exit(0);
				break;
			default:
				System.out.println("That was not an option. Choose again...");
				break;
			}
		}	
			
	}

	public static void init() {
		artistDB = new Artist();
		albumDB = new Album();
		songDB = new Song();
		if(!artistDB.open() || !albumDB.open() || !songDB.open()) {
			System.out.println("Can't open datasouce!");
			return;
		}
		artistDB.createView();
	}
	
	private static void searchForSong() {
		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter a song name: ");
		String input = scan.nextLine();
		
		List<View> songs = songDB.querySongsByTitle(input, 1);
		if(songs == null) {
			System.out.println("No songs found!");
			return;
		}
		
		for(View song : songs) {
			System.out.println(song.toString());
		}
	}

	
	private static void closeAllDB() {
		artistDB.close();
		albumDB.close();
		songDB.close();
	}

}
