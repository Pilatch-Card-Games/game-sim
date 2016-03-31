package com.pilatch.gamesim.cli;

import com.pilatch.gamesim.deck.Deck;
import com.pilatch.gamesim.deck.DeckFactory;
import com.pilatch.gamesim.deck.DeckType;
import com.pilatch.gamesim.game.HandProbabilityAffectingRules;
import com.pilatch.gamesim.hand.HandEvaluator;
import com.pilatch.gamesim.hand.RankedSuitedHandEvaluator;

import java.io.PrintWriter;
import java.lang.Integer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class RulesInterpreter {

	static final String VALUED_HAND_SIZE_DEFAULT = "5";
	static final String TOTAL_HAND_SIZE_DEFAULT = "5";
	static final String NUM_HANDS_TO_DEAL_DEFAULT = "1000000";
	static final String DECK_TYPE_DEFAULT = "PILATCH_14";
	
	static Options commandLineOptions = new Options()
	.addOption("d", "deck", true, "The type of deck this game will use; see com.pilatch.gamesim.DeckType")
	.addOption("v", "valuedHandSize", true, "Number of cards needed to make a hand - default: " + VALUED_HAND_SIZE_DEFAULT)
	.addOption("t", "totalHandSize", true, "Total number of cards each hand can choose from - default: " + TOTAL_HAND_SIZE_DEFAULT)
	.addOption("n", "numHandsToDeal", true, "How many hands will be simulated - default: " + NUM_HANDS_TO_DEAL_DEFAULT)
	.addOption("w", "wrapAroundStraights", false, "Let Aces be high or low on straights")
	.addOption("h", "help", false, "Show this message and quit")
	;
	
	
	public static HandProbabilityAffectingRules interpret(String[] args) {
		GnuParser parser = new GnuParser();
		CommandLine cl;
		try {
			cl = parser.parse(commandLineOptions, args, false);			
		}
		catch(ParseException pe) {
			System.out.println("parse exception caught: " + pe);
			return null;
		}
		if(cl.hasOption("h")) {
			HelpFormatter hf = new HelpFormatter();
			PrintWriter pw = new PrintWriter(System.out);
			hf.printHelp(pw, 100, "[options]", "", commandLineOptions, 2, 4, ""); //blank strings are header and footer
			pw.flush();
			return null;
		}
		int valuedHandSize = Integer.parseInt( cl.getOptionValue("v", VALUED_HAND_SIZE_DEFAULT) );
		int totalHandSize = Integer.parseInt( cl.getOptionValue("t", TOTAL_HAND_SIZE_DEFAULT) );
		long numHandsToDeal = Integer.parseInt( cl.getOptionValue("n", NUM_HANDS_TO_DEAL_DEFAULT) );
		DeckType deckType = interpretDeckType( cl.getOptionValue("d", DECK_TYPE_DEFAULT) );
		Deck deck = DeckFactory.newDeck(deckType);
		boolean wrapAroundStraights = cl.hasOption("w");
		HandEvaluator handEvaluator = new RankedSuitedHandEvaluator(valuedHandSize, deck.getRankRange(), wrapAroundStraights);
		return new HandProbabilityAffectingRules(deck, valuedHandSize, totalHandSize, numHandsToDeal, handEvaluator, null);
	}
	
	private static DeckType interpretDeckType(String deckType) {
		return DeckType.valueOf( deckType.toUpperCase().replaceAll("\\s+|-", "_") );
	}
}
