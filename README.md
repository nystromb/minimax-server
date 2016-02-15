[![Build Status](https://travis-ci.org/nystromb/minimax-server.svg?branch=master)](https://travis-ci.org/nystromb/minimax-server)

# minimax-server

A web API for tic tac toe game logic. It will calculate the best move for a given player and board, and will report whether a game is 'in progress,' 'tied,' or 'won.' It serves HTTP transactions using the [java-httpserver](https://github.com/scarvill91/java-httpserver) project.

## Requirements
  * Leiningen 2.5
  * Clojure 1.7
  * Speclj 3.3

## Usage
Enter the command ```git clone https://github.com/nystromb/minimax-server.git``` to clone the project. Enter ```lein run``` in the command line from the project root directory. 

The app will serve API requests at ```http://localhost:5000/api/game_state```, using localhost as an example domain. Given the current player's mark and a game board as query string parameters, it will provide a JSON response with the best move that can be made and whether the game is 'inProgress', 'tied', or 'won'. The best move is defined as the index of the designated space in the given board.

For example, a request to the url ```http://localhost:5000/api/game_state?current_player=o&board=x,x,_,o,o,_,x,_,_``` would yield a response with the following body:

```json
{
  "bestMove": 5,
  "gameState": "inProgress"
}
```
