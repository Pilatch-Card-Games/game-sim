game-sim
========

Rarity based valued hand scale generator for poker-like games.

Proven accurate within one-thousandth of a percentage point when dealing 2.6 million poker hands.

## Purpose

To inform game design decisions when creating poker-like betting games using arbitrary decks.

Was crucial to determining hand rarity for games like [Tournament Pilatch](https://pilatch.com/games/betting/Tournament-Pilatch). Note the hand scale at the bottom of that page. Also useful for finding a pleasant difference between rarities, and [determining whether to promote the _nothing_ hand](https://pilatch.com/blog/Ethan/Rags-n-Riches).

## Use

Build this module, then run the compiled jar with dependencies in the target folder, optionally passing a test name from amonng those defined near the top of `src/com/pilatch/gamesim/Tester.java` for example:

    java -jar target/GameSim-0.2-SNAPSHOT-jar-with-dependencies.jar pilatch14stud5

To perform your own experiments, modify the aforementioned java file to add a new test. Anything more fancy, and you'll have to make some new classes. Good luck!
