package com.pilatch.gamesim.util;

import java.util.Comparator;

public class HighToLowIntegerComparator implements Comparator<Integer> {

	public int compare(Integer a, Integer b){
		return a > b ? -1 : (a == b ? 0 : 1);
	}

}
