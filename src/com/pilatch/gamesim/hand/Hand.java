package com.pilatch.gamesim.hand;

import java.util.LinkedList;
import java.util.ArrayList;

import com.pilatch.gamesim.card.Card;

public class Hand<Type extends Card> extends LinkedList<Type>{
	
	public static final long serialVersionUID = 1;
	
	public ArrayList<Type> fold(){
		ArrayList<Type> muck = new ArrayList<Type>();
		while(size() != 0){
			muck.add(pop());
		}
		return muck;
	}
	
	public void inspect(){
		for(Card c : this){
			System.out.println(c.getFront());
		}
	}
	
	public void add(ArrayList<Type> cards){
		for(Type c : cards){
			add(c);
		}
	}
	
	public void flip() {
		for(Type c : this) {
			c.flip();
		}
	}
	
}
