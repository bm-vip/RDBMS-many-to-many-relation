
# Game Love Service

In this assignment, we look forward to test your skills in software development. Please, be pragmatic and avoid over engineering your solution.

## Problem Statement:

We want you to implement a game love service that keeps track of the games that the player loves. 

* Design a REST API with the following:
  * It should be possible to create a new entry by feeding it with the following:
    * The player that loved the game.
    * The game it loved.
  * It should be possible to unlove games.
  * It should be possible to fetch all games a player have loved.
  * It should be possible to fetch the most loved games.
    * The list should contain the x top loved games, where x should be possible to define in every request.    
    * Each item in this list should contain:
        * The game identifier.
        * Number of loves the game has.

## Deliverables:

Your submission should contain a Java service based Maven project solution. Avoid sending large attachments in your submission so do a maven clean before submission.

In this case, we recommend you to make use of spring boot as a starting point for your application. 

In addition, in order for us to test your solution; make use of an embedded database like H2 or Sqlite, using a local file database scheme with your working solution.
