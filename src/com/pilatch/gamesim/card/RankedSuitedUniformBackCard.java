package com.pilatch.gamesim.card;

public class RankedSuitedUniformBackCard implements Card {
	private Rank rank;
	private Suit suit;
	private String fullName;
	private boolean faceUp;
	
	public RankedSuitedUniformBackCard(Rank rank, Suit suit){
		this.rank = rank;
		this.suit = suit;
		faceUp = false;
	}
	
	public String toString(){
		return faceUp ? getFront() : getBack();
	}
	
	public String getFront() {
		if(this.fullName == null){
			this.fullName = this.rank.getRankName() + " of " + this.suit.getName();
		}
		return this.fullName;
	}

	public String getAbbr(){
		return Integer.toString(this.rank.getRankNumber().intValue()) + this.suit;
	}
	
	public String getBack() {
		return "Uniform card back";
	}

	public void flip() {
		this.faceUp = !this.faceUp;
	}
	
	public Rank getRank(){
		return this.rank;
	}
	
	public Suit getSuit(){
		return this.suit;
	}
	
	public boolean isFaceUp(){
		return this.faceUp;
	}
	
	public boolean isFaceDown(){
		return !this.faceUp;
	}

}
