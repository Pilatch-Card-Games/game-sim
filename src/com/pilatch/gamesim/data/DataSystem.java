package com.pilatch.gamesim.data;


import java.util.Hashtable;
import java.util.Enumeration;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DataSystem { //must extend with a constructor specific to the database type being used

	protected DatabaseConnection db;
	
	public int getNumSuits(int suitGroup){
		return db.getInt("SELECT numSuits FROM suitGroups WHERE suitGroup = " + suitGroup);
	}
	
	public int makeDeck(String deckName, int suitGroup, int ranksPerSuit) { //makes a deck from an existing suitGroup
		int numSuits = getNumSuits(suitGroup);
		if(numSuits == -1 || ranksPerSuit < 1 || deckName.trim().length() < 1){
			return -1; //failure
		}
		String sql = "INSERT INTO decks VALUES(null, '"+deckName+"', "+suitGroup+", "+ranksPerSuit+", "+ (numSuits * ranksPerSuit) +")";
		return db.update(sql);
	}

	public int makeDeck(String deckName, int suitGroup, int ranksPerSuit, int namedRankGroup){
		int newDeckID = makeDeck(deckName, suitGroup, ranksPerSuit);
		if(newDeckID > 0){
			
		}
		return newDeckID;
	}
	
	public boolean assignNamedRankGroupToDeck(int deckID, int namedRankGroup){
		int result = -1;
		if( deckExists(deckID) && namedRankGroupExists(namedRankGroup) ){
			result = db.update("INSERT INTO decks_namedRankGroups VALUES("+deckID+", "+namedRankGroup+")");
		}
		return result != -1;
	}
	
	public int makeDeck //gives the suitGroup the same name as the deck
		(
		String deckName,
		String[] suits,
		int ranksPerSuit
		)
	{
		int suitGroup = makeSuitGroup(suits, deckName);
		return makeDeck(deckName, suitGroup, ranksPerSuit);
	}

	public boolean deckExists(int deckID){
		return db.getBoolean("SELECT deckID FROM decks WHERE deckID="+deckID);
	}
	
	public String getDeckName(int deckID){
		return db.getString("SELECT deckName FROM decks WHERE deckID="+deckID);
	}
	
	public int getDeckSize(int deckID){
		return db.getInt("SELECT deckSize FROM decks WHERE deckID =" + deckID);
	}
	
	public int makeCardGame //force a minimum & maximum number of players // returns the gameID
		(
		String gameName,
		int deckID,
		int minPlayers,
		int maxPlayers,
		int valuedHandSize,
		int initialHandSize,
		int communalUpSize,
		int communalDownSize,
		boolean nullHandIsWorst
		) 
	{		
		int deckSize = getDeckSize(deckID);
		int communalSize = communalUpSize + communalDownSize;
		if (
				gameName.trim().length() == 0 || 
				deckID < 1 || 
				minPlayers < 1 || 
				maxPlayers < 1 || 
				valuedHandSize < 1 ||
				valuedHandSize > (communalUpSize + initialHandSize) ||
				valuedHandSize > (communalDownSize + initialHandSize) ||
				initialHandSize < 1 ||
				communalUpSize < 0 ||
				communalDownSize < 0 ||
				( communalSize + (initialHandSize * minPlayers) ) > deckSize ||
				( maxPlayers * initialHandSize) > ( deckSize - communalSize )
			) 
		{
			return -1; //failure
		}		
		StringBuffer sql = new StringBuffer("INSERT INTO cardGames VALUES(null, ");
		sql.append("'"+gameName+"', ");
		sql.append(deckID + ", ");
		sql.append(minPlayers + ", ");
		sql.append(maxPlayers + ", ");
		sql.append(valuedHandSize + ", ");
		sql.append(initialHandSize + ", ");
		sql.append(communalUpSize + ", ");
		sql.append(communalDownSize + ", ");
		sql.append((nullHandIsWorst ? 1 : 0) + ");");
		//System.out.println(sql.toString());
		return db.update(sql.toString());
	}

	public int makeCardGame //minimum players is 2; max players will be computed based on deck size, hand sizes, etc.
		(
		String gameName,
		int deckID,
		int valuedHandSize,
		int initialHandSize,
		int communalUpSize,
		int communalDownSize,
		boolean nullHandIsWorst
		) 
	{
		int maxPlayers = (int)Math.floor( (getDeckSize(deckID) - communalUpSize - communalDownSize) / initialHandSize );
		if(maxPlayers < 2){
			return -1; //failure
		}
		return makeCardGame(gameName, deckID, 2, maxPlayers, valuedHandSize, initialHandSize, communalUpSize, communalDownSize, nullHandIsWorst);
	}
	
	public int makeSuitGroup(String[] suits, String suitGroupName) {
		int suitGroup = db.update("INSERT INTO suitGroups VALUES(null, '"+suitGroupName+"', "+suits.length+")");
		for(int i=0; i<suits.length; i++){
			db.update("INSERT INTO suits VALUES("+suitGroup+", '"+suits[i]+"')");
		}
		return suitGroup;
	}
	
	public int makeNamedRankGroup(Hashtable<Integer,String> numsToNames, String rankGroupName) {
		if(rankGroupName.trim().length() < 1 || numsToNames.isEmpty()){
			return -1; //failure
		}
		int namedRankGroup = db.update("INSERT INTO namedRankGroups VALUES(null, '"+rankGroupName+"')");
		if(namedRankGroup < 0){
			return -1; //failure
		}
		Enumeration<Integer> rankNums = numsToNames.keys();
		while(rankNums.hasMoreElements()){
			Integer rankNum = rankNums.nextElement();
			String rankName = numsToNames.get(rankNum);
			db.update("INSERT INTO namedRanks VALUES("+namedRankGroup+", "+rankNum+", '"+rankName+"');");
		}
		return namedRankGroup;
	}
	
	public boolean namedRankGroupExists(int namedRankGroup){
		boolean condition1 = db.getBoolean("SELECT namedRankGroup FROM namedRankGroups WHERE namedRankGroup="+namedRankGroup);
		boolean condition2 = db.getBoolean("SELECT COUNT(*) FROM namedRanks WHERE namedRankGroup="+namedRankGroup);
		return condition1 && condition2;
	}
	
	public String getHandDefinition(int handID){
		String sql = "SELECT numCards, handDefinition FROM hands WHERE handID="+handID;
		String def = null;
		ResultSet rs = db.query(sql);
		try{
			if(rs.next()){
				def = rs.getInt(1) + "cards:" + rs.getString(2);
			}else{
				return null; //failure
			}
		} catch(SQLException ex){
			db.printError(ex);
			return null; //failure
		}
		return def;
	}
	
	public int makeValuedHand(String handName, int numCards, String handDefinition ){
		String sql = "INSERT INTO hands VALUES(null, '"+handName+"', "+numCards+", '"+handDefinition+"')";
		return db.update(sql); //returns the handID
	}
	
	public int setValuedHandForGame(int gameID, int handID){
		String sql = "INSERT INTO cardGames_hands VALUES("+gameID+", "+handID+")"; 
		return db.update(sql);
	}
	
	public void clearHandCount(int gameID){
		String sql = "DELETE FROM handCount WHERE gameID="+gameID;
		db.update(sql);
	}
	
	public int saveHandCount(int gameID, int numPlayers, boolean weighted, int handID, int handCount){
		String sql = "SELECT handCount FROM handCount WHERE gameID="+gameID+" AND numPlayers="+numPlayers+" AND weighted="+(weighted ? 1:0);
		int existingCount = db.getInt(sql);
		if( existingCount > 0 ){
			handCount = handCount + existingCount;
			sql = "UPDATE handCount SET handCount="+handCount+" WHERE gameID="+gameID+" AND numPlayers="+numPlayers+" AND weighted="+(weighted ? 1:0);
		} else {			
			sql = "INSERT INTO handCount VALUES("+gameID+", "+numPlayers+", "+(weighted ? 1:0)+", "+handID+", "+handCount+", )";
		}
		return db.update(sql); //returns -1 on failure
	}
	
	public int setValuedHandScale(int gameID){
		if(gameID < 1){
			return -1; //failure
		}
		String sql = "SELECT COUNT(*) FROM valuedHandScales WHERE gameID="+gameID;
		if(db.getBoolean(sql)){ //clear out the old scale
			sql = "DELETE FROM valuedHandScales WHERE gameID="+gameID;
			db.update(sql);
		}
		sql = "SELECT handID FROM handCount WHERE gameID="+gameID+" ORDER BY handCount DESC";
		ResultSet rs = db.query(sql);
		int handRank = 1;
		try{
			while(rs.next()){
				sql = "INSERT INTO valuedHandScales VALUES("+gameID+", "+rs.getInt(1)+", "+handRank;
				db.update(sql);
				handRank++; //higher rank = rarer hand
			}
		} catch(SQLException ex){
			db.printError(ex); //should be handled by a debugger in the future
			return -1;
		}
		return 1;
	}
	
}