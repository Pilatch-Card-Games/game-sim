package com.pilatch.gamesim.hand;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;

import com.pilatch.gamesim.card.Rank;
import com.pilatch.gamesim.card.Suit;

public class RankSuitMap extends HashMap<Rank, LinkedList<Suit>> {

	static final long serialVersionUID = 1L;
	
	public void addSuit(Rank r, Suit s){
		LinkedList<Suit> suitList = null;
		if(this.size() == 0){
			suitList = new LinkedList<Suit>();
			suitList.add(s);
			this.put(r, suitList);
		}
		else{
			Iterator<Rank> it = this.keySet().iterator();
			while(it.hasNext()){
				Rank itRank = it.next();
				if(itRank.equals(r)){ //we have a match. shove the new suit in there
					suitList = this.get(itRank);
					suitList.add(s);
					this.put(itRank, suitList);
					return;
				}
			}
			suitList = new LinkedList<Suit>(); //nothing matched; make a new entry
			suitList.add(s);
			this.put(r, suitList);
		}
	}
	
}
