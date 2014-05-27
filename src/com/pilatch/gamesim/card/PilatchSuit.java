package com.pilatch.gamesim.card;

public enum PilatchSuit implements Suit{
	ROCK, PAPER, SCISSORS;
	
	public String getName(){
		switch(this){
		case ROCK: return "Rock";
		case PAPER: return "Paper";
		default: return "Scissors";
		}
	}
	
	public boolean beats(PilatchSuit suit){
		switch(this){
		case ROCK:
			if(suit == SCISSORS){
				return true;
			}
			break;
		case PAPER:
			if(suit == ROCK){
				return true;
			}
			break;
		default:
			if(suit == PAPER){
				return true;
			}
		}
		return false;
	}
}
