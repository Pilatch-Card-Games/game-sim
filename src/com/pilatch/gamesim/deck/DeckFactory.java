package com.pilatch.gamesim.deck;


public class DeckFactory {
	public static Deck newDeck(DeckType type){
		switch(type){
		case POKER:
			return new PokerDeck();
		case ORIGINAL_PILATCH:
			return new OriginalPilatchDeck();
		case PILATCH_WITH_ACES:
			return new PilatchWithAcesDeck();
		case PILATCH_14:
			return new Pilatch14Deck();
		case PILATCH_15:
			return new Pilatch15Deck();
		case PILATCH_15_JOKER_UNRANKED:
			return new Pilatch15JokerUnrankedDeck();
		default:
			return null;
		}
	}
	
}
