package com.pilatch.gamesim.deck;

import com.pilatch.gamesim.card.Suit;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.Pilatch14NamedRanks;


public class RainbowPokerSingleSuitDeck extends Deck {
	private enum RainbowPokerPurpleSuit implements Suit{
		PURPLE;
		
		public String getName(){
			switch(this){
			case PURPLE: return "Purple";
			default: return null;
			}
		}
		
	}

	public RainbowPokerSingleSuitDeck(){
		// Pilatch14 has the same named ranks as RainbowPoker.
		IntegerRankRange range = new IntegerRankRange(10, 14, new Pilatch14NamedRanks());
		buildRankedSuitedCardsRepeatedly(range, RainbowPokerPurpleSuit.values(), 3);
	}
}
