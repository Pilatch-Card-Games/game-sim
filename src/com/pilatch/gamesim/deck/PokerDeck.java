package com.pilatch.gamesim.deck;

import com.pilatch.gamesim.card.PokerSuit;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.PokerNamedRanks;

public class PokerDeck extends Deck {
	public PokerDeck(){
		IntegerRankRange range = new IntegerRankRange(1, 13, new PokerNamedRanks());
		this.buildRankedSuitedCards(range, PokerSuit.values());
	}
}
