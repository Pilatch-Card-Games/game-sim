package com.pilatch.gamesim.hand;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Iterator;

import com.pilatch.gamesim.card.Rank;
import com.pilatch.gamesim.card.Suit;

public class RankSuitMap extends HashMap<Rank, LinkedList<Suit>> {

	static final long serialVersionUID = 1L;
	
	public void addSuit(Rank rankToAdd, Suit suitToAdd){
		LinkedList<Suit> suitList = null;
		if(this.size() == 0){
			suitList = new LinkedList<Suit>();
			suitList.add(suitToAdd);
			this.put(rankToAdd, suitList);
		}
		else{
			Iterator<Rank> it = this.keySet().iterator();
			while(it.hasNext()){
				Rank iteratorRank = it.next();
				// do == before .equals to check against null first
				if(iteratorRank == rankToAdd || (iteratorRank != null && iteratorRank.equals(rankToAdd))){ //we have a match. shove the new suit in there
					suitList = this.get(iteratorRank);
					suitList.add(suitToAdd);
					this.put(iteratorRank, suitList);
					return;
				}
			}
			suitList = new LinkedList<Suit>(); //nothing matched; make a new entry
			suitList.add(suitToAdd);
			this.put(rankToAdd, suitList);
		}
	}
	
}
