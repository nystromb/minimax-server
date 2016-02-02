# minimax-server

[![Build Status](https://travis-ci.org/nystromb/minimax-server.svg?branch=master)](https://travis-ci.org/nystromb/minimax-server)

Uses [java-httpserver](https://github.com/scarvill91/java-httpserver) to serve http requests.

## Requirements
  * Leiningen 2.5
  * Clojure 1.7
  * Speclj 3.3

## Building the project
Enter the command ```git clone --recursive https://github.com/nystromb/minimax-server.git``` to clone the project.

To generate the files for the web interface portion of the project, you must follow the instructions in the [web-interface](https://github.com/nystromb/tic-tac-toe-web-app) submodule to build the submodule project. After doing so, run the ```gen_dists.sh``` script in the submodule to build the html/css/js files to be used by the web interface.

## Usage
Enter ```lein run``` in the command line from the project root directory. 

The app offers a playable tic tac toe web interface at ```http://107.170.25.194:5000/```. 

The app will serve API requests at ```http://107.170.25.194:5000/api/game_state```. Given the current player's mark and a game board as query string parameters, it will provide a JSON response with the best move that can be made and whether the game is 'inProgress', 'tied', or 'won'. The best move is defined as the index of the designated space in the given board.

For example, a request to the url ```http://107.170.25.194:5000/api/game_state?current_player=o&board=x,x,_,o,o,_,x,_,_``` would yield a response with the following body:

```json
{
  "bestMove": 5,
  "gameState": "inProgress"
}
```
