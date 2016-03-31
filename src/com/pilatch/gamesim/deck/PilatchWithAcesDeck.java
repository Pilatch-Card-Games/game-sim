package com.pilatch.gamesim.deck;

import com.pilatch.gamesim.card.PilatchSuit;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.PokerNamedRanks;

public class PilatchWithAcesDeck extends Deck {
	public PilatchWithAcesDeck(){
		IntegerRankRange range = new IntegerRankRange(1, 13, new PokerNamedRanks());
		this.buildRankedSuitedCards(range, PilatchSuit.values());
	}
}
