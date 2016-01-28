# minimax-server

[![Build Status](https://travis-ci.org/nystromb/minimax-server.svg?branch=master)](https://travis-ci.org/nystromb/minimax-server)

Uses [java-httpserver](https://github.com/scarvill91/java-httpserver) to serve http requests.

## Requirements
  * Leiningen 2.5
  * Clojure 1.7
  * Speclj 3.3

## Running the app
Enter ```lein run``` in the command line from the project root directory.

## Using the app
The app offers a playable tic tac toe web interface at ```http://107.170.25.194:5000/```. 

The app will serve API requests at ```http://107.170.25.194:5000/api/game_state```. Given the current player's mark and a game board as query string parameters, it will provide a JSON response with the best move that can be made and whether the game is 'inProgress', 'tied', or 'won'. The best move is defined as the index of the designated space in the given board.

For example, a request to the url ```http://localhost:5000/api/game_state?current_player=o&board=x,x,_,o,o,_,x,_,_``` would yield a response with the following body:

```json
{
  "bestMove": 5,
  "gameState": "inProgress"
}
```
