package com.pilatch.gamesim.card;

public class Rank {
	private Number rankNumber;
	private String rankName;
	
	public Rank(Number n){
		this.rankNumber = n;
	}
	
	public Rank(Number n, String name){
		this.rankNumber = n;
		this.rankName = name;
	}
	
	public Number getRankNumber(){
		return this.rankNumber;
	}
	
	public String getRankName(){
		if(this.rankName != null){
			return this.rankName;
		}
		return this.rankNumber.toString();
	}
	
	@Override
	public String toString(){
		return getRankName();
	}
	
	@Override
	public boolean equals(Object o){
		return o != null && o.getClass() == this.getClass() && ((Rank)o).getRankNumber().equals(this.rankNumber);
	}
}
