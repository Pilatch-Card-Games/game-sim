package com.pilatch.gamesim.hand;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Iterator;

import com.pilatch.gamesim.card.Rank;
import com.pilatch.gamesim.card.RankedSuitedUniformBackCard;
import com.pilatch.gamesim.card.Suit;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.PokerNamedRanks;
import com.pilatch.gamesim.ranks.RankRange;
import com.pilatch.gamesim.ranks.SpecialRanks;
import com.pilatch.gamesim.util.HighToLowIntegerComparator;

public class RankedSuitedHandEvaluator extends HandEvaluator<RankedSuitedUniformBackCard> {

	private int valuedHandSize;
	LinkedList<String> weightScale;
	RankRange rr;
	private Number aceNumber = null;
	private int acePosition = 0; //-1: first, 0: don't know, 1: last
	
	//wrapAroundStraights defaults to true
	public RankedSuitedHandEvaluator(int valuedHandSize, RankRange rr) throws IllegalArgumentException{
		this(valuedHandSize, rr, true, null);
	}
	
	public RankedSuitedHandEvaluator(int valuedHandSize, RankRange rr, boolean wrapAroundStraights) throws IllegalArgumentException{
		this(valuedHandSize, rr, wrapAroundStraights, null);
	}
	
	public RankedSuitedHandEvaluator(int valuedHandSize, RankRange rr, boolean wrapAroundStraights, LinkedList<String> weightScale) throws IllegalArgumentException{
		if(valuedHandSize < 1){
			throw new IllegalArgumentException(this.getClass() + " needs a valued hand size of 1 or more.");
		}
		this.valuedHandSize = valuedHandSize;
		if(rr == null){
			this.rr = new IntegerRankRange(1, 13, new PokerNamedRanks());			
		}
		else{
			this.rr = rr;
		}
		if(wrapAroundStraights){
			HashMap<Number, String> namedRanks = this.rr.getNamedRanks();
			Set<Number> namedRankNumbers = namedRanks.keySet();
			for(Number n : namedRankNumbers){
				if(SpecialRanks.ACE.equals(namedRanks.get(n))){
					this.aceNumber = n;
					break;
				}
			}			
		}
		this.setRangeAcePosition();
		if(weightScale != null){
			this.weightScale = weightScale;
		}
	}
	
	@Override
	public LinkedList<String> evaluate(Hand<RankedSuitedUniformBackCard> h) {
		LinkedList<String> valuedHands = new LinkedList<String>();
		LinkedHashMap<Rank, LinkedList<Suit>> rankOrdered = new LinkedHashMap<Rank, LinkedList<Suit>>();
		HashMap<Suit, LinkedList<Rank>> suitOrdered = new HashMap<Suit, LinkedList<Rank>>();
		order(h, rankOrdered, suitOrdered);
		
		try{
			this.subMatches(rankOrdered, valuedHands);
		}catch (InvertedBoatStringException ibse){
			System.out.println("inverted boat");
			System.out.println(h);
			System.exit(1);
		}
		this.straight(rankOrdered, valuedHands);
		Suit flushSuit;
		if((flushSuit = this.flush(suitOrdered, valuedHands)) != null){
			this.straightFlush(suitOrdered.get(flushSuit), valuedHands);
		}
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
	/**
	 * fills two maps:
	 * groups the hand by rank order -> the suits of cards of that rank
	 * groups the hand by suit -> the ranks of the cards of that rank
	 * @param h
	 */
	protected void order(Hand<RankedSuitedUniformBackCard> h, LinkedHashMap<Rank, LinkedList<Suit>> rankOrdered, HashMap<Suit, LinkedList<Rank>> suitOrdered){
		//go through all the cards in the hand, which is random
		RankSuitMap rankMap = new RankSuitMap();
		for(RankedSuitedUniformBackCard card : h){
			Rank rank = card.getRank();
			Suit suit = card.getSuit();
			
			//collect the cards by rank
			rankMap.addSuit(rank, suit);
			
			//collect the cards by suit
			LinkedList<Rank> ranks = suitOrdered.get(suit); //this works as long as the suit is an enum
			if(ranks == null){
				ranks = new LinkedList<Rank>();
				ranks.add(rank);
				suitOrdered.put(suit, ranks);
			}
			else{
				ranks.add(rank);
			}
		}

		//actually put rankOrdered in order
		Set<Rank> handRanks = rankMap.keySet();
		
		while(handRanks.size() != 0){
			//get the lowest rank in the rankMap each time, and put it in rankOrdered
			Rank lowestRank = null;
			for(Rank r : handRanks){
				if(lowestRank == null || r.getRankNumber().doubleValue() < lowestRank.getRankNumber().doubleValue()){
					lowestRank = r;
				}
			}
			rankOrdered.put(lowestRank, rankMap.remove(lowestRank));
		}
	}
	
	
	private Suit flush(HashMap<Suit, LinkedList<Rank>> suitOrdered, LinkedList<String> valuedHands){
		Set<Suit> suits = suitOrdered.keySet();
		for(Suit suit : suits){
			if(suitOrdered.get(suit).size() >= valuedHandSize){
				valuedHands.add("flush");
				return suit;
			}
		}
		return null;
	}
	
	//only to be called if a hand is a flush
	private void straightFlush(LinkedList<Rank> ranks, LinkedList<String> valuedHands){
		if (ranks.size() < this.valuedHandSize) return;

		ArrayList<Rank> orderedRanks = new ArrayList<Rank>();
		//put the ranks in order
		while(ranks.size() != 0){
			Rank lowestRank = null;
			int lowestRankIndex = 0;
			for(int i = 0; i < ranks.size(); i++){
				Rank r = ranks.get(i);
				if(lowestRank == null || r.getRankNumber().doubleValue() < lowestRank.getRankNumber().doubleValue()){
					lowestRank = r;
					lowestRankIndex = i;
				}
			}
			orderedRanks.add(ranks.remove(lowestRankIndex));
		}
		
		//if there is an ace in the low position, and the ace rank is low, add it to the top side too, for straight checking
		if(this.acePosition == -1 && orderedRanks.get(0).getRankNumber().equals(this.aceNumber)){
			orderedRanks.add(orderedRanks.get(0));
		}
		//otherwise, if there is an ace in the last position, and the ace rank is high, add it to the beginning
		else if(this.acePosition == 1 && orderedRanks.get(orderedRanks.size() - 1).getRankNumber().equals(this.aceNumber)){
			orderedRanks.add(0, orderedRanks.get(orderedRanks.size() - 1));
		}
		
		Iterator<Rank> handIterator = orderedRanks.iterator();
		if(isSequenceStraight(handIterator)){
			valuedHands.add("straight flush");
		}
	}
	
	private void setRangeAcePosition(){ //-1: first, 0: don't know, 1: last
		if(this.aceNumber == null){
			return;
		}
		int place = 0;
		rr.restart();
		while(rr.hasNext()){
			if(rr.next().getRankNumber().equals(this.aceNumber)){
				this.acePosition = place == 0 ? -1 : 1;
			}
			place++;
		}
	}
	
	private boolean isSequenceStraight(Iterator<Rank> handIterator){
		Number handNumber = handIterator.next().getRankNumber();
		Number rangeNumber = null; //we have not yet begun!
		int sequence = 0; //the number of ranks in-a-row the hand has
		rr.restart();
		
		//Ace wrap-around case 1:
		//the last number in the range is the ace and we are on the first rank in the hand
		if(handNumber.equals(this.aceNumber) && this.acePosition == 1){
			sequence = 1;
			handNumber = handIterator.next().getRankNumber();
		}
		
		//look through the meaty parts of the rank range
		while(rr.hasNext()){
			rangeNumber = rr.next().getRankNumber();
			if(handNumber.equals(rangeNumber)){
				//we have a match, move to the next rank in the hand
				sequence++;
				if(sequence == this.valuedHandSize){
					return true;
				}
				if(handIterator.hasNext()){
					handNumber = handIterator.next().getRankNumber();					
				}
				else{ //we are on the last rank of the hand
					break;
				}
				//...
			}
			//this hand rank is out of sequence
			else{
				sequence = 0;
			}
		}
		
		//Ace wrap-around case 2:
		//the first number in the range is the ace, and we are on the last number in the hand
		if(this.acePosition == -1 && sequence == this.valuedHandSize - 1 && handNumber.equals(this.aceNumber) && !handIterator.hasNext()){
			//System.out.println("wrap case 2");
			return true;
		}
		
		return false;
	}
	
	protected void straight(LinkedHashMap<Rank, LinkedList<Suit>> ordered, LinkedList<String> valuedHands){
		//a hand with fewer ranks in it than the valued hand size cannot be a straight
		if(ordered.size() < this.valuedHandSize){
			return;
		}
		
		//all the ranks found in the current hand
		Set<Rank> handRanks = ordered.keySet();
		Iterator<Rank> handIterator = null;
		
		if(this.aceNumber != null){ //add the ace to the top end of the hand too, or bottom if there is one
			LinkedList<Rank> handRanksList = new LinkedList<Rank>();
			Rank theAce = null;
			boolean addAceToBack = false; //only matters if hasAce is true
			
			//duplicate the handRanks into a linked list where we can add to the back or front of it
			for(Rank r : handRanks){
				handRanksList.add(r);
			}
			
			//look for an ace
			int place = 0; //where we are in the handRanks Set
			for(Rank r : handRanks){
				if(r.getRankNumber() == this.aceNumber){
					theAce = r;
					addAceToBack = place == 0; //determine whether this ace is at the front or the back
					break;
				}
				place++;
			}
			if(theAce != null){
				//slap the ace onto the other side
				if(addAceToBack){
					handRanksList.add(theAce);
				}else{
					handRanksList.addFirst(theAce);
				}
				handIterator = handRanksList.iterator();
			}
		}
		if(handIterator == null){
			handIterator = handRanks.iterator();			
		}
		
		if(isSequenceStraight(handIterator)){
			valuedHands.add("straight");
		}
		
	}
	
	private TreeMap<Integer, Integer> makeOfAKindNum(LinkedHashMap<Rank, LinkedList<Suit>> ordered, Comparator<Integer> comparator){
		TreeMap<Integer, Integer> ofAKindNum;
		ofAKindNum = comparator == null ? new TreeMap<Integer, Integer>() : new TreeMap<Integer, Integer>(comparator);
		Iterator<Rank> rankIterator = ordered.keySet().iterator();
		//of-a-kind -> number thereof
		while(rankIterator.hasNext()){
			int numOfRank = ordered.get(rankIterator.next()).size();
			if(numOfRank > 1){
				Integer storedOfAKindNum = ofAKindNum.get(numOfRank);
				if(storedOfAKindNum == null){
					ofAKindNum.put(numOfRank, 1);
				}
				else{
					ofAKindNum.put(numOfRank, storedOfAKindNum + 1);
				}
			}
		}
		return ofAKindNum;
	}
	
	private TreeMap<Integer, Integer> trimMatchesToValuedHandSize(LinkedHashMap<Rank, LinkedList<Suit>> ordered, Integer handSize){
		return trimMatchesToValuedHandSize(ordered, handSize, null);
	}
	
	//this algorithm is forced to traverse the matches high to low
	private TreeMap<Integer, Integer> trimMatchesToValuedHandSize(LinkedHashMap<Rank, LinkedList<Suit>> ordered, Integer handSize, HighToLowIntegerComparator comparator){
		TreeMap<Integer, Integer> ofAKindNum = makeOfAKindNum(ordered, comparator);
		Iterator<Integer> oakIterator = ofAKindNum.keySet().iterator();
		int totalMatchingCards = 0;
		if(handSize == null){
			handSize = this.valuedHandSize;
		}

		while(oakIterator.hasNext()){
			Integer ofAKind = oakIterator.next();
			totalMatchingCards += ofAKind * ofAKindNum.get(ofAKind);
		}

		oakIterator = ofAKindNum.keySet().iterator(); //restart the iteration
		if(!oakIterator.hasNext()){
			return null;
		}
		Integer ofAKind = oakIterator.next(); //please fail horribly, if you ever do
		Integer numOfAKind = ofAKindNum.get(ofAKind);
		while(totalMatchingCards > handSize){
			//demote as much as possible
			if(numOfAKind == 1){
				oakIterator.remove();				
			}else{
				ofAKindNum.put(ofAKind, --numOfAKind); //demote				
			}
			if(ofAKind == 2){
				totalMatchingCards -= 2;
			}
			else{ //bigger than 2 of a kind, which is as low as we go
				Integer lowerOfAKind = ofAKind - 1; //the next one down on the totem pole
				Integer numOfLowerKind = ofAKindNum.get(lowerOfAKind);
				if(numOfLowerKind == null){
					ofAKindNum.put(lowerOfAKind, 1);
				}
				else{
					ofAKindNum.put(lowerOfAKind, numOfLowerKind + 1);
				}
				totalMatchingCards--;
			}
			oakIterator = ofAKindNum.keySet().iterator(); //stick to the biggest while you can
			ofAKind = oakIterator.next(); //start at the beginning again, whatever the beginning may be					
			numOfAKind = ofAKindNum.get(ofAKind);
		}
		if(comparator == null){ //gotta return these in high to low format for conversion to Strings
			TreeMap<Integer, Integer> highToLowOfAKindNum = new TreeMap<Integer,Integer>(new HighToLowIntegerComparator());
			oakIterator = ofAKindNum.keySet().iterator(); //restart
			while(oakIterator.hasNext()){
				Integer i = oakIterator.next();
				highToLowOfAKindNum.put(i, ofAKindNum.get(i));
			}
			ofAKindNum = highToLowOfAKindNum;
		}

		return ofAKindNum;
	}
	
	private String ofAKindFormat(Integer ofAKind){
		return ofAKind.toString() + " of a kind";
	}
	
	private String multiOfAKindFormat(Integer numOfThisKind, Integer ofAKind){
		return (numOfThisKind > 1 ? numOfThisKind.toString() + "x " : "") + ofAKindFormat(ofAKind);
	}

	private void saveMatches(ValuedMatches vm, LinkedList<String> valuedHands)throws InvertedBoatStringException{
		for(TreeMap<Integer,Integer> ofAKindNum : vm){
			if(ofAKindNum.size() != 0){
				Iterator<Integer> oakIterator = ofAKindNum.keySet().iterator();
				Integer ofAKind = null;
				Integer numOfThisKind = null;
				//put the of a kind matches in order, biggest to lowest by of-a-kind
				String matches = "";
				while(oakIterator.hasNext()){
					ofAKind = oakIterator.next();
					numOfThisKind = ofAKindNum.get(ofAKind);
					if(!"".equals(matches)){
						matches = matches + " & ";
					}
					matches = matches + multiOfAKindFormat(numOfThisKind, ofAKind);
				}
				if(!valuedHands.contains(matches)){
					if("2 of a kind & 3 of a kind".equals(matches)){
						throw new InvertedBoatStringException();
					}
					valuedHands.add(matches);				
				}
			}
		}
	}
	
	private void addMatches(TreeMap<Integer,Integer> ofAKindNum, ValuedMatches valuedMatches){
		if(ofAKindNum != null && !valuedMatches.contains(ofAKindNum)){
			valuedMatches.add(ofAKindNum);
		}
	}
	
	protected void subMatches(LinkedHashMap<Rank, LinkedList<Suit>> ordered, LinkedList<String> valuedHands)throws InvertedBoatStringException{
		ValuedMatches vm = new ValuedMatches();
		for(int i = this.valuedHandSize; i != 1; i--){
			TreeMap<Integer, Integer> ofAKindNum = trimMatchesToValuedHandSize(ordered, i, new HighToLowIntegerComparator()); //ordered high to low
			addMatches(ofAKindNum, vm);
			if(i != 2){
				ofAKindNum = trimMatchesToValuedHandSize(ordered, i); //ordered low to high
				addMatches(ofAKindNum, vm);
			}
		}
		saveMatches(vm, valuedHands);
	}
	
}
