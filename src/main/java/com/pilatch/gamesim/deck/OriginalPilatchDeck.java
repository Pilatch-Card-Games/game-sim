package com.pilatch.gamesim.deck;

import com.pilatch.gamesim.card.PilatchSuit;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.PokerNamedRanks;

public class OriginalPilatchDeck extends Deck {
	public OriginalPilatchDeck(){
		IntegerRankRange range = new IntegerRankRange(2, 13, new PokerNamedRanks());
		this.buildRankedSuitedCards(range, PilatchSuit.values());
	}
}
