package com.pilatch.gamesim.deck;

import com.pilatch.gamesim.card.PilatchSuit;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.Pilatch14NamedRanks;

public class Pilatch15Deck extends Deck {
	public Pilatch15Deck(){
		IntegerRankRange range = new IntegerRankRange(1, 15);
		this.buildRankedSuitedCards(range, PilatchSuit.values());
	}
}
