package com.pilatch.gamesim.deck;

import com.pilatch.gamesim.card.PilatchSuit;
import com.pilatch.gamesim.card.PilatchCard;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.Pilatch15JokerUnrankedNamedRanks;
import com.pilatch.gamesim.ranks.RankRange;

public class Pilatch15JokerUnrankedDeck extends Deck {
	public Pilatch15JokerUnrankedDeck() {
		RankRange range = new IntegerRankRange(1, 14, new Pilatch15JokerUnrankedNamedRanks());
		this.buildRankedSuitedCards(range, PilatchSuit.values());
		for(PilatchSuit suit : PilatchSuit.values()) {
			this.addCard(new PilatchCard(null, suit));
		}
	}
}
