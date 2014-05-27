package com.pilatch.gamesim.hand;
import java.util.HashMap;
import java.util.LinkedList;

public class ValuedHandCount extends HashMap<String,Long> {

	static final long serialVersionUID = 1L;
	
	public synchronized void add(LinkedList<String> valuedHands){
		for(String valuedHand : valuedHands){
			if(get(valuedHand) == null){
				put(valuedHand, 1L);
			}
			else{
				put(valuedHand, get(valuedHand) + 1);
			}
		}
	}
	
}
