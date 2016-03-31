package com.pilatch.gamesim.ranks;

import java.util.HashMap;

public final class PokerNamedRanks extends HashMap<Number, String> {

	public static final long serialVersionUID = 1L;
	
	public PokerNamedRanks(){
		this.put(1, "Ace"); //Ace is a special name, allowing for high and low straights
		this.put(11, "Jack");
		this.put(12, "Queen");
		this.put(13, "King");
	}
	
}
