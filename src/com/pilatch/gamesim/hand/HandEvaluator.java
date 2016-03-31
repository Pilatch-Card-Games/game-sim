package com.pilatch.gamesim.hand;

import java.util.LinkedList;

import com.pilatch.gamesim.card.Card;

public abstract class HandEvaluator<Type extends Card> {
	
	public abstract LinkedList<String> evaluate(Hand<Type> h);
	
	public LinkedList<String> evaluate(Hand<Type> h, Hand<Type> community){
		Hand<Type> combined = new Hand<Type>();
		for(Type c : h){
			combined.add(c);
		}
		for(Type c : community){
			combined.add(c);
		}
		return evaluate(combined);
	}
}
