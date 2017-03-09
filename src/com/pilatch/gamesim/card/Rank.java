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
		if (o == null) {
			return false;
		}
		if (o.getClass() == this.getClass()) {
			Rank otherRank = (Rank)o;
			if (otherRank.getRankNumber() == this.rankNumber) {
				return true;
			}
			if(otherRank.getRankNumber() == null || this.rankNumber == null) {
				return false;
			}
			return otherRank.getRankNumber().equals(this.rankNumber);
		}
		return false;
	}
}
