package test;

import static org.junit.Assert.*;

import java.util.LinkedList;

import com.pilatch.gamesim.card.PilatchCard;
import com.pilatch.gamesim.card.PilatchSuit;
import com.pilatch.gamesim.card.PokerCard;
import com.pilatch.gamesim.card.PokerSuit;
import com.pilatch.gamesim.card.Rank;
import com.pilatch.gamesim.card.RankedSuitedUniformBackCard;
import com.pilatch.gamesim.hand.HandEvaluator;
import com.pilatch.gamesim.hand.RankedSuitedHand;
import com.pilatch.gamesim.hand.RankedSuitedHandEvaluator;
import com.pilatch.gamesim.ranks.IntegerRankRange;
import com.pilatch.gamesim.ranks.Pilatch14NamedRanks;
import com.pilatch.gamesim.ranks.PokerNamedRanks;
import com.pilatch.gamesim.weight.PokerWeightScale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RankedSuitedHandEvaluatorTest {

	private RankedSuitedHandEvaluator e;
	private RankedSuitedHand h;
	private LinkedList<String> valuedHands;
	
	private static PokerNamedRanks pokerNamedRanks;
	private static IntegerRankRange originalPilatchRankRange;
	private static IntegerRankRange pilatch14RankRange;
	private static IntegerRankRange pokerRankRange;
	private static RankedSuitedHandEvaluator pilatch14RawHandEvaluator;
	private static RankedSuitedHandEvaluator pilatch14RawWraparoundHandEvaluator;
	private static RankedSuitedHandEvaluator pokerRawHandEvaluator;
	private static RankedSuitedHandEvaluator pokerWeightedHandEvaluator;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pokerNamedRanks = new PokerNamedRanks();
		originalPilatchRankRange = new IntegerRankRange(2, 13, pokerNamedRanks);
		pokerRankRange = new IntegerRankRange(1, 13, pokerNamedRanks);
		pokerRawHandEvaluator = new RankedSuitedHandEvaluator(5, pokerRankRange, true);
		pokerWeightedHandEvaluator = new RankedSuitedHandEvaluator(5, pokerRankRange, true, PokerWeightScale.getInstance());
		pilatch14RankRange = new IntegerRankRange(1, 14, new Pilatch14NamedRanks());
		pilatch14RawHandEvaluator = new RankedSuitedHandEvaluator(5, pilatch14RankRange, false);
		pilatch14RawWraparoundHandEvaluator = new RankedSuitedHandEvaluator(5, pilatch14RankRange, true);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		valuedHands = new LinkedList<String>();
		h = new RankedSuitedHand();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testPokerBestFiveOfSevenRawFullHouse() {
		e = pokerRawHandEvaluator;
		//not weighted; find all the valued hands in a boat
		h.add(new PokerCard(new Rank(1), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(1), PokerSuit.DIAMONDS));
		h.add(new PokerCard(new Rank(13), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(13), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(13), PokerSuit.HEARTS));
		h.add(new PokerCard(new Rank(1), PokerSuit.HEARTS));
		h.add(new PokerCard(new Rank(2), PokerSuit.HEARTS));
		assertTrue(h.size() == 7);
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 4);
		assertTrue(valuedHands.contains("3 of a kind & 2 of a kind"));
		assertTrue(valuedHands.contains("3 of a kind"));
		assertTrue(valuedHands.contains("2x 2 of a kind"));
		assertTrue(valuedHands.contains("2 of a kind"));
		//find only trips and a pair, but not a boat
		h = new RankedSuitedHand();
		h.add(new PokerCard(new Rank(1), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(3), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(13), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(13), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(13), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(4), PokerSuit.HEARTS));
		h.add(new PokerCard(new Rank(2), PokerSuit.HEARTS));
		assertTrue(h.size() == 7);
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 2);
		assertTrue(valuedHands.contains("3 of a kind"));
		assertTrue(valuedHands.contains("2 of a kind"));
	}
	
	@Test
	public final void testPokerBestFiveOfSevenWeightedFullHouse() {
		e = pokerRawHandEvaluator;
		//raw versus weighted evaluation
		h.add(new PokerCard(new Rank(1), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(2), PokerSuit.DIAMONDS));
		h.add(new PokerCard(new Rank(8), PokerSuit.HEARTS));
		h.add(new PokerCard(new Rank(8), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(5), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(5), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(5), PokerSuit.HEARTS));
		assertTrue(h.size() == 7);
		//find a bunch of hands
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 4);
		assertTrue(valuedHands.contains("2 of a kind"));
		assertTrue(valuedHands.contains("2x 2 of a kind"));
		assertTrue(valuedHands.contains("3 of a kind"));
		assertTrue(valuedHands.contains("3 of a kind & 2 of a kind"));
		//find a full house, but not the others
		e = pokerWeightedHandEvaluator;
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 1);
		assertFalse(valuedHands.contains("3 of a kind"));
		assertTrue(valuedHands.contains("3 of a kind & 2 of a kind"));
		
	}
	
	@Test
	public final void testPokerBestSixOfSevenTriplePair() {
		h.add(new PokerCard(new Rank(1), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(1), PokerSuit.DIAMONDS));
		h.add(new PokerCard(new Rank(13), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(13), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(2), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(2), PokerSuit.HEARTS));
		h.add(new PokerCard(new Rank(5), PokerSuit.HEARTS));
		HandEvaluator<RankedSuitedUniformBackCard> e = new RankedSuitedHandEvaluator(6, pokerRankRange);
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 3);
		assertTrue(valuedHands.contains("2 of a kind"));
		assertTrue(valuedHands.contains("2x 2 of a kind"));
		assertTrue(valuedHands.contains("3x 2 of a kind"));
	}
	
	@Test
	public final void testPilatchBestFiveOfSevenWraparoundStraightWithTwoPair() {
		h.add(new PilatchCard(new Rank(1), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(13), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(13), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(12), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(11), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(10), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(1), PilatchSuit.ROCK));
		assertTrue(h.size() == 7);
		e = pokerRawHandEvaluator; //yes, they are Pilatch suits, but it shouldn't care
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 3);
		assertTrue(valuedHands.contains("straight"));
		assertTrue(valuedHands.contains("2x 2 of a kind"));
		assertTrue(valuedHands.contains("2 of a kind"));
	}
	
	@Test
	public final void testPilatch14RawWraparoundLowStraightWithPair() {
		h.add(new PilatchCard(new Rank(14), PilatchSuit.PAPER)); //Ace is 14, and lower than 1 with wraparounds
		h.add(new PilatchCard(new Rank(1), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(3), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(1), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(4), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(12), PilatchSuit.ROCK));
		assertTrue(h.size() == 7);
		e = pilatch14RawWraparoundHandEvaluator;
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 2);
		assertTrue(valuedHands.contains("2 of a kind"));
		assertTrue(valuedHands.contains("straight"));
	}
	
	@Test
	public final void testPilatch14RawWraparoundHighStraightOnly() {
		h.add(new PilatchCard(new Rank(11), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(14), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(13), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(4), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(3), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(12), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(10), PilatchSuit.ROCK));
		assertTrue(h.size() == 7);
		e = pilatch14RawWraparoundHandEvaluator;
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 1);
		assertTrue(valuedHands.contains("straight"));
	}
	
	@Test
	public final void testPilatch14RawWraparoundNoStraight() {
		h.add(new PilatchCard(new Rank(11), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(14), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(13), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(11), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(3), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(10), PilatchSuit.ROCK));
		assertTrue(h.size() == 7);
		e = pilatch14RawWraparoundHandEvaluator;
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 1);
		assertTrue(valuedHands.contains("2 of a kind"));
		assertFalse(valuedHands.contains("straight"));
	}
	
	@Test
	public final void testPilatch14RawNoWraparoundNoStraight1() {
		h.add(new PilatchCard(new Rank(4), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(14), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(13), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(11), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(3), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(1), PilatchSuit.ROCK));
		assertTrue(h.size() == 7);
		e = pilatch14RawHandEvaluator;
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 1);
		assertFalse(valuedHands.contains("straight"));
		assertTrue(valuedHands.contains("high card"));
	}
	
	@Test
	public final void testPilatch14RawNoWraparoundNoStraight2() {
		h.add(new PilatchCard(new Rank(1), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(3), PilatchSuit.SCISSORS));
		h.add(new PilatchCard(new Rank(4), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(6), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(7), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(8), PilatchSuit.ROCK));
		assertTrue(h.size() == 7);
		e = pilatch14RawHandEvaluator;
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 1);
		assertFalse(valuedHands.contains("straight"));
		assertTrue(valuedHands.contains("high card"));
	}
	
	@Test
	public final void testPilatch14RawNoWraparoundStraightFlushAndPair() {
		h.add(new PilatchCard(new Rank(7), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(8), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(9), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(10), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(11), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.ROCK));
		assertTrue(h.size() == 7);
		e = pilatch14RawHandEvaluator;
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 4);
		assertTrue(valuedHands.contains("straight flush"));
		assertTrue(valuedHands.contains("flush"));
		assertTrue(valuedHands.contains("straight"));
		assertTrue(valuedHands.contains("2 of a kind"));
	}

	@Test
	public final void testPilatch14WeightedNoWraparoundStraightFlushAndPair() {
		h.add(new PilatchCard(new Rank(7), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(8), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(9), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(10), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(11), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.ROCK));
		assertTrue(h.size() == 7);
		LinkedList<String> temporaryWeightScale = new LinkedList<String>();
		temporaryWeightScale.add("straight flush");
		temporaryWeightScale.add("flush");
		temporaryWeightScale.add("straight");
		temporaryWeightScale.add("2 of a kind");
		e = new RankedSuitedHandEvaluator(5, pilatch14RankRange, false, temporaryWeightScale);
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 1);
		assertTrue(valuedHands.contains("straight flush"));
	}
	
	@Test
	public final void testRawBestFiveOfSevenQuadAndPair() {
		h.add(new PokerCard(new Rank(13), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(13), PokerSuit.HEARTS));
		h.add(new PokerCard(new Rank(13), PokerSuit.DIAMONDS));
		h.add(new PokerCard(new Rank(13), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(2), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(5), PokerSuit.HEARTS));
		h.add(new PokerCard(new Rank(2), PokerSuit.HEARTS));
		assertTrue(h.size() == 7);
		e = pokerRawHandEvaluator;
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 5);
		assertTrue(valuedHands.contains("4 of a kind"));
		assertTrue(valuedHands.contains("3 of a kind & 2 of a kind"));
		assertTrue(valuedHands.contains("3 of a kind"));
		assertTrue(valuedHands.contains("2x 2 of a kind"));
		assertTrue(valuedHands.contains("2 of a kind"));
	}

	@Test
	public final void testRawBestSixOfSevenQuadAndPair() {
		h.add(new PokerCard(new Rank(13), PokerSuit.SPADES));
		h.add(new PokerCard(new Rank(13), PokerSuit.HEARTS));
		h.add(new PokerCard(new Rank(13), PokerSuit.DIAMONDS));
		h.add(new PokerCard(new Rank(13), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(2), PokerSuit.CLUBS));
		h.add(new PokerCard(new Rank(5), PokerSuit.HEARTS));
		h.add(new PokerCard(new Rank(2), PokerSuit.HEARTS));
		assertTrue(h.size() == 7);
		e = new RankedSuitedHandEvaluator(6, pokerRankRange, true); //honor six-card hands
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 6);
		assertTrue(valuedHands.contains("4 of a kind"));
		assertTrue(valuedHands.contains("4 of a kind & 2 of a kind"));
		assertTrue(valuedHands.contains("3 of a kind & 2 of a kind"));
		assertTrue(valuedHands.contains("3 of a kind"));
		assertTrue(valuedHands.contains("2x 2 of a kind"));
		assertTrue(valuedHands.contains("2 of a kind"));
	}
	
	@Test
	public final void testPilatch14RawFlushWraparoundStraightPair() {
		h.add(new PilatchCard(new Rank(4), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(3), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(1), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(14), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(7), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(14), PilatchSuit.SCISSORS));
		assertTrue(h.size() == 7);
		e = pilatch14RawWraparoundHandEvaluator;
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 3);
		assertTrue(valuedHands.contains("flush"));
		assertTrue(valuedHands.contains("straight"));
		assertTrue(valuedHands.contains("2 of a kind"));
	}
	
	@Test
	public final void testOriginalPilatchBestThreeOfFiveRawFlushPairStraight() {
		h.add(new PilatchCard(new Rank(4), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(3), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(13), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(13), PilatchSuit.SCISSORS));
		assertTrue(h.size() == 5);
		e = new RankedSuitedHandEvaluator(3, originalPilatchRankRange, false);
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 3);
		assertTrue(valuedHands.contains("flush"));
		assertTrue(valuedHands.contains("straight"));
		assertTrue(valuedHands.contains("2 of a kind"));
		assertFalse(valuedHands.contains("straight flush"));
	}
	
	@Test
	public final void testOriginalPilatchBestThreeOfFiveWeightedFlushPairStraight() {
		h.add(new PilatchCard(new Rank(4), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(3), PilatchSuit.ROCK));
		h.add(new PilatchCard(new Rank(2), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(13), PilatchSuit.PAPER));
		h.add(new PilatchCard(new Rank(13), PilatchSuit.SCISSORS));
		assertTrue(h.size() == 5);
		LinkedList<String> temporaryWeightScale = new LinkedList<String>();
		temporaryWeightScale.add("straight");
		temporaryWeightScale.add("2 of a kind");
		temporaryWeightScale.add("flush");
		e = new RankedSuitedHandEvaluator(3, originalPilatchRankRange, false, temporaryWeightScale);
		valuedHands = e.evaluate(h);
		assertTrue(valuedHands.toString(), valuedHands.size() == 1);
		assertFalse(valuedHands.contains("straight flush"));
		assertFalse(valuedHands.contains("flush"));
		assertFalse(valuedHands.contains("2 of a kind"));
		assertTrue(valuedHands.contains("straight"));
	}
}
