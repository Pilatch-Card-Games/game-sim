package com.pilatch.gamesim.weight;

import java.util.LinkedList;

public class Pilatch14WeightScale{

	public static LinkedList<String> getInstance() {
		LinkedList<String> scale = new LinkedList<String>();
		scale.add("straight flush");
		scale.add("3 of a kind & 2 of a kind");
		scale.add("straight");
		scale.add("3 of a kind");
		scale.add("flush");
		scale.add("2x 2 of a kind");
		scale.add("high card");
		scale.add("2 of a kind");
		return scale;
	}

}
