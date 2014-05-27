package com.pilatch.gamesim.data;


import java.util.Hashtable;

public class MySQLDataSystem extends DataSystem {

	public MySQLDataSystem(String host, String db_name, String user, String pass){
		this.db = new MySQLConnection(host, db_name, user, pass);
	}
	
	public static void main(String[] args){
		MySQLDataSystem ds = new MySQLDataSystem("localhost", "java", "java", "java");
		/*String[] suits = {"Rock", "Paper", "Scissors"};
		String name = "Pilatch";
		ds.makeSuitGroup(suits, name);*/
		//int deckID = ds.makeDeck("Original Pilatch", 2, 12);
		//System.out.println(ds.getNumSuits(0));
		//System.out.println(ds.makeCardGame("Original Pilatch", 1, 4, 3, 3, 3, true));
		/*Hashtable<Integer,String> n2n = new Hashtable<Integer,String>();
		n2n.put(1, "Ace");
		n2n.put(11, "Jack");
		n2n.put(12, "Queen");
		n2n.put(13, "King");
		int namedRankGroup = ds.makeNamedRankGroup(n2n, "Poker");
		System.out.println(namedRankGroup);*/
	}
	
}
