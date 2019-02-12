package com.pilatch.gamesim.deck;

import com.pilatch.gamesim.card.RainbowPokerSuit;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.Pilatch14NamedRanks;


public class RainbowPokerDeck extends Deck {

	public RainbowPokerDeck(){
		// Pilatch14 has the same named ranks as RainbowPoker.
		IntegerRankRange range = new IntegerRankRange(10, 14, new Pilatch14NamedRanks());
		buildRankedSuitedCardsRepeatedly(range, RainbowPokerSuit.values(), 3);
	}
}
