package com.pilatch.gamesim.card;

public enum RainbowPokerSuit implements Suit{
	PURPLE, BLUE, RED, GREEN, ORANGE;
	
	public String getName(){
		switch(this){
		case PURPLE: return "purple";
		case BLUE: return "blue";
		case RED: return "red";
		case GREEN: return "green";
		case ORANGE: return "orange";
		default: return null;
		}
	}
	
}
