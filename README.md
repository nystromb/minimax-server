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
The app will serve requests at ```http://107.170.25.194:5000```. For it to respond with a move, you must pass a ```current_player``` and a ```board``` parameter. For example, a request to the url ```http://localhost:5000/?current_player=o&board=x,x,_,o,o,_,x,_,_``` would yield a response of ```5```.
