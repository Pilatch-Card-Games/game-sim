package com.pilatch.gamesim.game;

import java.util.LinkedList;

import com.pilatch.gamesim.deck.Deck;
import com.pilatch.gamesim.hand.HandEvaluator;

public class HandProbabilityAffectingRules {
	
	Deck deck;
	int valuedHandSize;
	int totalHandSize;
	long numHandsToDeal;
	HandEvaluator handEvaluator;
	LinkedList<String> weightScale; //optional

	public HandProbabilityAffectingRules(Deck deck, int valuedHandSize,
			int totalHandSize, long handsToDeal,
			HandEvaluator handEvaluator, LinkedList<String> weightScale) {
		this.deck = deck;
		this.valuedHandSize = valuedHandSize;
		this.totalHandSize = totalHandSize;
		this.numHandsToDeal = handsToDeal;
		this.weightScale = weightScale;
		this.handEvaluator = handEvaluator;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

	public int getValuedHandSize() {
		return valuedHandSize;
	}

	public void setValuedHandSize(int valuedHandSize) {
		this.valuedHandSize = valuedHandSize;
	}

	public int getTotalHandSize() {
		return totalHandSize;
	}

	public void setTotalHandSize(int totalHandSize) {
		this.totalHandSize = totalHandSize;
	}

	public long getNumHandsToDeal() {
		return numHandsToDeal;
	}

	public void setNumHandsToDeal(long handsToDeal) {
		this.numHandsToDeal = handsToDeal;
	}

	public LinkedList<String> getWeightScale() {
		return weightScale;
	}

	public void setWeightScale(LinkedList<String> weightScale) {
		this.weightScale = weightScale;
	}

	public HandEvaluator getHandEvaluator() {
		return handEvaluator;
	}

	public void setHandEvaluator(HandEvaluator handEvaluator) {
		this.handEvaluator = handEvaluator;
	}
	
}
