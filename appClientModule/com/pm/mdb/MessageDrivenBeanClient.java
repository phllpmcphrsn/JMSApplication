package com.pm.mdb;
import java.util.Properties;
import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.*;

public class MessageDrivenBeanClient {
	private static Context context 					= null;
	private static Queue queue 						= null;
	private static QueueConnectionFactory factory 	= null;
	private static QueueConnection queueConnection 	= null;
	private static QueueSession queueSession 		= null;
	private static TextMessage textMessage	 		= null;
	private static ObjectMessage objMessage 		= null;
	
	public static void main(String[] args) throws NamingException, JMSException {
		System.out.println("Entering MessageDrivenBeanClient");
		
		context = MessageDrivenBeanClient.getInitialContext();
		queue = (Queue) context.lookup("queue/msgQueue");
		factory = (QueueConnectionFactory) context.lookup("ConnectionFactory");
		queueConnection = factory.createQueueConnection();
		queueSession = queueConnection.createQueueSession(false, QueueSession.AUTO_ACKNOWLEDGE);
		QueueSender queueSender = queueSession.createSender(queue);
		
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
			System.out.println("Please choose what you would like to do (Choose A, B, C, or D): ");
			input = scan.next();
			switch (input) {
			case "A":
				textMessage = playSong();
				queueSender.send(textMessage);
				break;
//			case "B":
//				searchForSong();
//				break;
//			case "C":
//				searchForArtist();
//				break;
			case "D":
				System.out.println("Thanks for using me.");
				System.exit(0);
				break;
			default:
				System.out.println("That was not an option. Choose again...");
				break;
			}
		}
//		System.out.println("Exiting MessageDrivenBeanClient");
		
	}

	private static void searchForArtist() {
		System.out.println("Searching for an artist...");
		
	}

	private static void searchForSong() {
		// TODO Auto-generated method stub
		
	}

	private static TextMessage playSong() throws JMSException {
		System.out.println("Searching for an artist...");
		TextMessage songMessage = queueSession.createTextMessage("SEARCH artist");
		return songMessage;
		
	}

	public static Context getInitialContext() throws NamingException {
		Properties properties = new Properties();
		properties.setProperty("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		properties.setProperty("java.naming.factory.url.pkgs","org.jboss.naming");
		properties.setProperty("java.naming.provider.url","127.0.0.1:1099");
		return new InitialContext(properties);
	}
	
	public static void initMDB() throws NamingException, JMSException {}


}