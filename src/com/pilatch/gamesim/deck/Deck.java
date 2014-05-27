package com.pilatch.gamesim.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import com.pilatch.gamesim.card.Card;
import com.pilatch.gamesim.card.Rank;
import com.pilatch.gamesim.card.RankedSuitedUniformBackCard;
import com.pilatch.gamesim.card.Suit;
import com.pilatch.gamesim.ranks.RankRange;

public abstract class Deck { //make constructors for specific kinds of decks
	private ArrayList<Card> cards;
	private RankRange rankRange;
	
	protected void buildRankedSuitedCards(RankRange range, Suit[] suits){
		this.rankRange = range;
		this.cards = new ArrayList<Card>();
		while(range.hasNext()){
			Rank r = range.next();
			for(Suit s : suits){
				this.cards.add(new RankedSuitedUniformBackCard(r, s));
			}
		}
	}
	
	protected void buildRankedSuitedCards(RankRange range, Suit[] suits, Hashtable<Rank, String> rankNames){
		this.rankRange = range;
		this.cards = new ArrayList<Card>();
		while(range.hasNext()){
			Rank r = range.next();
			for(Suit s : suits){
				this.cards.add(new RankedSuitedUniformBackCard(r, s));
			}
		}
	}
	
	public void addCards(ArrayList<Card> cards){
		this.cards.addAll(cards);
	}
	
	public void shuffle(){
		Collections.shuffle(this.cards);
	}
	
	public void flip(){
		for(Card c : cards){
			c.flip();
		}
	}
	
	public Card deal(){ //deal one card
		return this.cards.remove(cards.size() - 1);
	}
	
	public int size(){
		return cards.size();
	}

	public RankRange getRankRange() {
		return this.rankRange;
	}
	
	public void inspect(){
		for(Card c : this.cards){
			System.out.println(c.getFront());
		}
	}
	
	public String toString(){
		return cards.toString();
	}
	
}
