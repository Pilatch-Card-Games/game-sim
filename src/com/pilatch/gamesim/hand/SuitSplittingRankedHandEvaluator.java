package com.pilatch.gamesim.hand;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import com.pilatch.gamesim.card.Rank;
import com.pilatch.gamesim.card.RankedSuitedUniformBackCard;
import com.pilatch.gamesim.card.Suit;
import com.pilatch.gamesim.ranks.RankRange;

public class SuitSplittingRankedHandEvaluator extends RankedSuitedHandEvaluator {

	public SuitSplittingRankedHandEvaluator(int valuedHandSize, RankRange rr) throws IllegalArgumentException{
		super(valuedHandSize, rr, true, null);
	}
	
	private HashMap<Suit, Hand<RankedSuitedUniformBackCard>> splitHandBySuits(Hand<RankedSuitedUniformBackCard> hand) {
		HashMap<Suit, Hand<RankedSuitedUniformBackCard>> suitedHands = new HashMap<Suit, Hand<RankedSuitedUniformBackCard>>();
		
		for (RankedSuitedUniformBackCard card : hand) {
			Suit suit = card.getSuit();
			if (suitedHands.get(suit) == null) {
				suitedHands.put(suit, new Hand<RankedSuitedUniformBackCard>());
			}
			suitedHands.get(suit).add(card);
		}
		
		return suitedHands;
	}
	
	@Override
	public LinkedList<String> evaluate(Hand<RankedSuitedUniformBackCard> hand) {
		LinkedList<String> valuedHands = new LinkedList<String>();
		Collection<Hand<RankedSuitedUniformBackCard>> suitedHands = splitHandBySuits(hand).values();		
		
		for (Hand<RankedSuitedUniformBackCard> suitedHand : suitedHands) {
			valuedHands.addAll(evaluateSuitedHand(suitedHand));
		}
		
		valuedHands.removeAll(Arrays.asList("flush"));
		
		return valuedHands;
	}
	
	private LinkedList<String> evaluateSuitedHand(Hand<RankedSuitedUniformBackCard> h) {
		LinkedList<String> valuedHands = new LinkedList<String>();
		LinkedHashMap<Rank, LinkedList<Suit>> rankOrdered = new LinkedHashMap<Rank, LinkedList<Suit>>();
		HashMap<Suit, LinkedList<Rank>> suitOrdered = new HashMap<Suit, LinkedList<Rank>>();
		order(h, rankOrdered, suitOrdered); // TODO: suitOrdered doesn't matter, so delete it if time allows.
		
		try{
			this.subMatches(rankOrdered, valuedHands);
		}catch (InvertedBoatStringException ibse){
			System.out.println("inverted boat");
			System.out.println(h);
			System.exit(1);
		}
		this.straight(rankOrdered, valuedHands);
		if(valuedHands.size() == 0){
			valuedHands.add("high card");
		}
		else if(this.weightScale != null && valuedHands.size() != 1){ //will effectively be greater than 1 because it's never negative
			//only return one hand - the best hand
			for(String weightedHand : this.weightScale){
				if(valuedHands.contains(weightedHand)){
					//blank out the valued hands, then add only the top dawg
					valuedHands = new LinkedList<String>();
					valuedHands.add(weightedHand);
				}
			}
		}
		return valuedHands;
	}
}
