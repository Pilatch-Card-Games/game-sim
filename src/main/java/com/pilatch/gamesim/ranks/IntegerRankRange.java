package com.pilatch.gamesim.ranks;

import java.util.HashMap;
import java.util.Map;

import com.pilatch.gamesim.card.Rank;

public class IntegerRankRange implements RankRange{
	private Rank[] range;
	private int position;
	private HashMap<Number, String> namedRanks; //e.g. {11=>"Jack", 12=>"Queen", 3.14=>"Pi"}
	private HashMap<Number, String> integersToNames; //all ranks and their String names
	
	public IntegerRankRange(int lowestRank, int highestRank) throws IndexOutOfBoundsException{
		if(lowestRank >= highestRank){
			throw new IndexOutOfBoundsException("The lowest value must be less than the highest.");
		}
		int numRanks = (highestRank - lowestRank) + 1;
		boolean hasNamedRanks = this.namedRanks != null;
		this.range = new Rank[numRanks];
		for(int i = 0; i < numRanks; i++){
			int currentRank = lowestRank + i;
			if(hasNamedRanks && this.namedRanks.containsKey((Number)currentRank)){
				this.range[i] = new Rank(currentRank, this.namedRanks.get(currentRank));
			}
			else{
				this.range[i] = new Rank(currentRank);
			}
		}
		this.position = 0;
	}
	
	public void restart(){
		position = 0;
	}
	
	public HashMap<Number, String> getNamedRanks(){
		return (HashMap<Number, String>)getIntegersToNames();
	}
	
	public IntegerRankRange(int lowestRank, int highestRank, HashMap<Number, String> namedRanks) throws IndexOutOfBoundsException{
		this(lowestRank, highestRank);
		this.namedRanks = namedRanks;
	}
	
	public Map<Number, String> getIntegersToNames(){
		if(this.integersToNames == null || this.integersToNames.isEmpty()){
			this.buildIntegersToNames();
		}
		return this.integersToNames;
	}
	
	public String getRankName(int rank){
		Map<Number, String> ints2noms = this.getIntegersToNames(); //ensure we don't get a null
		return ints2noms.get(rank);
	}
	
	public Rank next() throws IndexOutOfBoundsException{
		if(this.hasNext()){
			return range[this.position++]; //returns then increments
		}else{
			throw new IndexOutOfBoundsException("next() called without checking hasNext()");
		}
	}
	
	public boolean hasNext(){
		if(this.position >= range.length){
			return false;			
		}
		return true;
	}
	
	public void remove() throws UnsupportedOperationException{
		throw new UnsupportedOperationException("remove() not supported by this class.");
	}
	
	private void buildIntegersToNames(){
		this.integersToNames = new HashMap<Number,String>();
		for(Rank r : this.range){
			Number n = r.getRankNumber();
			if(this.namedRanks == null || !this.namedRanks.containsKey(n)){
				this.integersToNames.put(n.intValue(), n.toString());
			}else{
				this.integersToNames.put(n.intValue(), this.namedRanks.get(n));
			}
		}
	}

	public String toString(){
		String output = "";
		for(Rank r : this.range){
			output = output + r + " ";
		}
		return output;
	}
	
}
