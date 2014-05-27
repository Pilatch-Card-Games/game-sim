--For ScriptRunner comments must start on their own lines. Comments come before commented lines.

CREATE TABLE cardGames(
  gameID int unsigned not null auto_increment primary key,
  gameName nvarchar(100) not null,
  deckID int unsigned not null,
  minPlayers int(1) unsigned not null,
  maxPlayers int(1) unsigned not null,
  --how many cards form a valued hand, e.g. 5 in poker
  valuedHandSize int unsigned not null,
  --in Texas Hold'em, this would be 2
  initialHandSize int unsigned not null,
  --in Tournament Pilatch, this is 4
  communalUpSize int unsigned not null,
  --in Tournament Pilatch, this is 3
  communalDownSize int unsigned not null,
  --(boolean) whether a hand that doesn't match anything valued is always the worst
  nullHandIsWorst int(1) unsigned not null  
);

CREATE TABLE suits(
  suitID int unsigned not null,
  suitGroup int unsigned not null,
  suitName nvarchar(100) not null,
  primary key (suitID, suitGroup)
);

--which suits beat which other suits
CREATE TABLE suitPower(
  suitID int unsigned not null,
  --another suit that the above suit beats
  beatsSuitID int unsigned not null,
  primary key(suitID, beatsSuitID)
);

CREATE TABLE suitGroups(
  suitGroup int unsigned not null auto_increment,
  suitGroupName nvarchar(100) not null,
  primary key(suitGroup, suitGroupName)
);

CREATE TABLE decks(
  deckID int unsigned not null auto_increment primary key,
  deckName nvarchar(100) not null
);

CREATE TABLE cards(
  cardID int unsigned not null auto_increment primary key,
  cardName nvarchar(100) not null unique key
);

CREATE TABLE rankedSuitedCards(
  cardID int unsigned not null primary key,
  --rank begins at 1 and goes up; a rank of 0 indicates a wild card
  rank int unsigned not null,
  suit nvarchar(100) not null
);

--wild cards that can stand in as one of multiple ranks
CREATE TABLE multiRankCards(
  cardID int unsigned not null,
  --a possible rank of "0" indicates any rank is possible for this wild card;
  --otherwise store individual possible ranks in a comma-delimited list
  possibleRanks nvarchar(255) unsigned not null,
  primary key(cardID, possibleRank)
);

--wild cards that can stand in as one of multiple suits
CREATE TABLE multiSuitCards(
  cardID int unsigned not null,
  --a possible suitID of "0" indicate any suit is possible for this wild card;
  --otherwise store individual possible suitIDs in a comma-delimited list
  possibleSuitIDs nvarchar(255) not null,
  primary key(cardID, possibleSuit)
);

--a card can appear in both the multiSuit and multiRank table to be a full-on wild card

CREATE TABLE decks_cards(
  deckID int unsigned not null,
  cardID int unsigned not null,
  primary key(deckID, cardID)
);

--junction to prevent nulls in decks table
CREATE TABLE decks_namedRankGroups(
  deckID int unsigned not null,
  namedRankGroup int unsigned not null,
  primary key(deckID, namedRankGroup)
);

CREATE TABLE namedRankGroups(
  namedRankGroup int unsigned not null auto_increment,
  groupName nvarchar(100) not null,
  primary key(namedRankGroup, groupName)
);

CREATE TABLE namedRanks(
  namedRankGroup int unsigned not null,
  rankNum int unsigned not null,
  --can store non-integer rank values here, like "infinity" or "pi" or "Queen"
  rankName nvarchar(100) not null,
  primary key(namedRankGroup, rankNum)
);

--valued hands like straight, flush, pair, pinochle, etc
CREATE TABLE hands(
  handID int unsigned not null auto_increment primary key,
  handName nvarchar(100) not null,
  numCards int unsigned not null,
  --a String parsed by the program to define the hand
  handDefinition nvarchar(255) not null
);

CREATE TABLE handCount(
  gameID int unsigned not null,
  numPlayers int(1) unsigned not null,
  --(boolean)
  weighted int(1) unsigned not null,
  handID int unsigned not null,
  handCount int unsigned not null,
  primary key(gameID, numPlayers, handID)
);

CREATE TABLE valuedHandScales(
  gameID int unsigned not null,
  handID int unsigned not null,
  --the higher the better, ranks start at 1
  handRank int unsigned not null,
  primary key(gameID, handID, handRank)
);

--junction; which cardGames care about which hands
CREATE TABLE cardGames_hands(
  gameID int unsigned not null,
  handID int unsigned not null,
  primary key(gameID, handID)
);

CREATE TABLE wildCards(
  deckID int unsigned not null,
  wildCardName nvarchar(100) not null,
  --boolean; whether this card can be used as a card of any suit
  suitWild int(1) unsigned not null,
  --boolean; whether this card can be used as a card of any rank
  rankWild int(1) unsigned not null,
  --value relative to other wild cards in this deck; higher numbers are better
  valueAmongWildCards int unsigned not null,
  primary key(deckID, wildCardName)
);