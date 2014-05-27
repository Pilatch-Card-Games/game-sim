package com.pilatch.gamesim.weight;

import java.util.LinkedList;

public final class PokerWeightScale{
	
	private static LinkedList<String> scale;
	
	public static final LinkedList<String> getInstance() {
		if(scale == null) {			
			scale = new LinkedList<String>();
			scale.add("straight flush");
			scale.add("4 of a kind");
			scale.add("3 of a kind & 2 of a kind");
			scale.add("flush");
			scale.add("straight");
			scale.add("3 of a kind");
			scale.add("2x 2 of a kind");
			scale.add("2 of a kind");
			scale.add("high card");
		}
		return scale;
	}
}
