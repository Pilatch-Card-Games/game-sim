package com.pilatch.gamesim.card;

public class UniformBackCard implements Card {
	private String fullName;
	private boolean faceUp;
	
	public UniformBackCard(String fullName){
		this.fullName = fullName;
		this.faceUp = false;
	}
	
	public String getFront() {
		return fullName;
	}

	public String getBack() {
		return "uniform card back";
	}

	public void flip() {
		this.faceUp = !this.faceUp;

	}

}
