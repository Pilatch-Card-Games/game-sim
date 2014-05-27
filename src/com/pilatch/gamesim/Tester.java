package com.pilatch.gamesim;

import java.util.*;

import com.pilatch.gamesim.cli.*;
import com.pilatch.gamesim.deck.*;
import com.pilatch.gamesim.game.*;
import com.pilatch.gamesim.hand.*;
import com.pilatch.gamesim.ranks.*;
import com.pilatch.gamesim.weight.*;

public class Tester {

	private static final String delim = ":";
	
	public static void main(String[] args){
		if(args.length == 0){
			args[0] = "--help";
		}
		String test = args[0];

		if("pilatch14".equals(test)){
			pilatch14(args.length == 2 && "weigh".equals(args[1]));
		}else if("pilatch15".equals(test)){
			pilatch15();
		}else if("poker5cardStud".equals(test)){
			poker5CardStud();			
		}else if("pokerBest5of7".equals(test)){
			pokerBest5of7();
		}else if("pilatch14stud5".equals(test)){
			pilatch14stud5();
		}else if("pilatch4point0".equals(test)){
			pilatch4point0();
		}else if("originalPilatch".equals(test)){
			originalPilatch();
		}else if("originalPilatchWithAces".equals(test)){
			originalPilatchWithAces();
		}else if("pilatch6point0".equals(test)){
			pilatch6point0();
		}else if("tourneyPilatch".equals(test)){
			tourneyPilatch();
		}else{
			HandProbabilityAffectingRules rules = RulesInterpreter.interpret(args);
			deal(rules.getDeck(), rules.getValuedHandSize(), rules.getTotalHandSize(), rules.getNumHandsToDeal(), rules.getHandEvaluator());
		}
		
	}

	public static void pokerBest5of7(){
		Deck d = DeckFactory.newDeck(DeckType.POKER);		
		int valuedHandSize = 5;
		int totalHandSize = 7;
		int handsToDeal = 1337845; // 1/100 of 133,784,560 
		RankRange rr = new IntegerRankRange(1, 13, new PokerNamedRanks());
		LinkedList<String> weightScale = PokerWeightScale.getInstance();
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, true, weightScale);
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);		
	}
	
	public static void poker5CardStud(){
		Deck d = DeckFactory.newDeck(DeckType.POKER);		
		int valuedHandSize = 5;
		int totalHandSize = 5;
		int handsToDeal = 2598960;
		RankRange rr = new IntegerRankRange(1, 13, new PokerNamedRanks());
		LinkedList<String> weightScale = PokerWeightScale.getInstance();
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, true, weightScale);
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);		
	}
	
	public static void pilatch6point0() {
		Deck d = DeckFactory.newDeck(DeckType.PILATCH_14);		
		int valuedHandSize = 6;
		int totalHandSize = 8;
		int handsToDeal = 1000000;
		RankRange rr = new IntegerRankRange(1, 14, new Pilatch14NamedRanks());
		LinkedList<String> weightScale = Pilatch14WeightScale.getInstance();
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, true, null); //weightScale
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);
	}
	
	public static void pilatch4point0() {
		Deck d = DeckFactory.newDeck(DeckType.PILATCH_14);
		int valuedHandSize = 4;
		int totalHandSize = 6;
		int handsToDeal = 1000000;
		RankRange rr = new IntegerRankRange(1, 14, new Pilatch14NamedRanks());
		LinkedList<String> weightScale = Pilatch14WeightScale.getInstance();
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, true, null); //weightScale
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);
	}

	public static void originalPilatch() {
		Deck d = DeckFactory.newDeck(DeckType.ORIGINAL_PILATCH);
		int valuedHandSize = 4;
		int totalHandSize = 6;
		int handsToDeal = 1947792; //1947792
		RankRange rr = new IntegerRankRange(1, 12);
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, false, null); //weightScale
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);
	}

	public static void originalPilatchWithAces() {
		Deck d = DeckFactory.newDeck(DeckType.ORIGINAL_PILATCH);
		int valuedHandSize = 4;
		int totalHandSize = 6;
		int handsToDeal = 1000000;
		RankRange rr = new IntegerRankRange(1, 13, new PokerNamedRanks());
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, true, null); //weightScale
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);
	}

	public static void tourneyPilatch() {	
		Deck d = DeckFactory.newDeck(DeckType.PILATCH_WITH_ACES);
		int valuedHandSize = 5;
		int totalHandSize = 7;
		int handsToDeal = 15380937;
		RankRange rr = new IntegerRankRange(1, 13, new PokerNamedRanks());
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, true, null); //weightScale
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);
	}
	
	public static void pilatch14(boolean weigh) {
		Deck d = DeckFactory.newDeck(DeckType.PILATCH_14);
		int valuedHandSize = 5;
		int totalHandSize = 7;
		int handsToDeal = 2697832; //26978328 total hands
		RankRange rr = new IntegerRankRange(1, 14, new Pilatch14NamedRanks());
		LinkedList<String> weightScale = Pilatch14WeightScale.getInstance();
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, false, weigh ? weightScale : null); //weightScale is the final arg, if you want to go all weighty
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);
	}

	public static void pilatch15() {
		Deck d = DeckFactory.newDeck(DeckType.PILATCH_15);
		int valuedHandSize = 5;
		int totalHandSize = 7;
		int handsToDeal = 1000000;
		RankRange rr = new IntegerRankRange(1, 15);
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, false, null); //weightScale is the final arg, if you want to go all weighty
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);
	}
	
	public static void pilatch14stud5() {
		Deck d = DeckFactory.newDeck(DeckType.PILATCH_14);
		int valuedHandSize = 5;
		int totalHandSize = 5;
		int handsToDeal = 1000000;
		RankRange rr = new IntegerRankRange(1, 14, new Pilatch14NamedRanks());
		LinkedList<String> weightScale = Pilatch14WeightScale.getInstance();
		HandEvaluator e = new RankedSuitedHandEvaluator(valuedHandSize, rr, true, null); //weightScale
		deal(d, valuedHandSize, totalHandSize, handsToDeal, e);
	}

	private static void deal(Deck d, int valuedHandSize, int totalHandSize, int handsToDeal, HandEvaluator e) {
		deal(d, valuedHandSize, totalHandSize, (int)handsToDeal, e);
	}
	
	private static void deal(Deck d, int valuedHandSize, int totalHandSize, long handsToDeal, HandEvaluator e){
		d.flip();
		d.shuffle();
		LinkedList<String> valuedHands = new LinkedList<String>();
		ValuedHandCount count = new ValuedHandCount();
		Hand h = new RankedSuitedHand();
		Hand muck = new RankedSuitedHand(); //where we toss hands after they have been evaluated
		long handsDealt = 0;
		while(handsDealt < handsToDeal){
			while(d.size() >= totalHandSize){
				for(int i = 0; i < totalHandSize; i++){
					h.add(d.deal());
				}
				
				valuedHands = e.evaluate(h);
				count.add(valuedHands);
				muck.add(h.fold());
				handsDealt++;				
			}
			d.addCards(muck.fold());
			d.shuffle();
		}
		printCount(count, handsDealt);
	}
	
	private static String valuedHandPercentage(long valuedHandCount, long handsDealt){
		return (new Float(100 * valuedHandCount) / new Float(handsDealt)) + "%";
	}
	
	private static void printCount(ValuedHandCount count, long handsDealt){
		TreeMap<Long, String> weightedResults = new TreeMap<Long, String>();
		Iterator<String> countIterator = count.keySet().iterator();
		while(countIterator.hasNext()){
			String valuedHand = countIterator.next();
			Long valuedHandCount = count.get(valuedHand);
			weightedResults.put(valuedHandCount, valuedHand);
		}
		
		Iterator<Long> weightedCountIterator = weightedResults.keySet().iterator();
		while(weightedCountIterator.hasNext()){
			Long valuedHandCount = weightedCountIterator.next();
			String valuedHand = weightedResults.get(valuedHandCount);
			//detail:
			System.out.println(valuedHand + delim + valuedHandCount + delim + valuedHandPercentage(valuedHandCount, handsDealt));
			//handCount only
			//System.out.println(valuedHandCount);
		}
		//detail:
		System.out.println("Hands dealt"+delim+handsDealt);
		//handsDealt only
		//System.out.println(handsDealt);
	}
	
}