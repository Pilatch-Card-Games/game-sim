package com.pilatch.gamesim.card;

public enum PokerSuit implements Suit{
	SPADES, HEARTS, CLUBS, DIAMONDS;
	
	public String getName(){
		switch(this){
		case SPADES: return "Spades";
		case HEARTS: return "Hearts";
		case CLUBS: return "Clubs";
		case DIAMONDS: return "Diamonds";
		default: return null;
		}
	}
	
}
