package com.pilatch.gamesim.deck;

import com.pilatch.gamesim.card.PilatchSuit;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.Pilatch14NamedRanks;

public class Pilatch14Deck extends Deck {
	public Pilatch14Deck(){
		IntegerRankRange range = new IntegerRankRange(1, 14, new Pilatch14NamedRanks());
		this.buildRankedSuitedCards(range, PilatchSuit.values());
	}
}
