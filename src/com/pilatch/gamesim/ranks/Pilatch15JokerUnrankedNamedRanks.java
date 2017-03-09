package com.pilatch.gamesim.ranks;

import java.util.HashMap;
import com.pilatch.gamesim.ranks.SpecialRanks;

public final class Pilatch15JokerUnrankedNamedRanks extends HashMap<Number, String> {

	public static final long serialVersionUID = 1L;
	
	public Pilatch15JokerUnrankedNamedRanks() {
		this.put(11, "Jack");
		this.put(12, "Queen");
		this.put(13, "King");
		this.put(14, SpecialRanks.ACE);
		this.put(null, SpecialRanks.UNRANKED);
	}
	
}