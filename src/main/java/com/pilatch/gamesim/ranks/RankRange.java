package com.pilatch.gamesim.ranks;

import java.util.Iterator;
import java.util.HashMap;

import com.pilatch.gamesim.card.Rank;

public interface RankRange extends Iterator<Rank>{
	
	public void restart();
	
	public HashMap<Number, String> getNamedRanks(); //returning null is totally cool.
	
}
