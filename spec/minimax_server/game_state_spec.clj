(ns minimax-server.game-state-spec
  (:import
    [scarvill.httpserver.request RequestBuilder])
  (:require
    [speclj.core :refer :all]
    [minimax-server.game-state :refer :all]
    [clojure.data.json :as json]))

(defn request-with [current-player board]
  (.build (doto (new RequestBuilder)
    (.setParameter "current_player" current-player)
    (.setParameter "board" board)
    (.setURI "/"))))

(defn response-data [response]
  (json/read-str (String. (.getBody response)) :key-fn keyword))

(describe "game state endpoint"

  (it "returns the best move for given player and board, selected via minimax"
    (let [request (request-with "o" "x,x,_,o,_,_,_,_,_")]
      (should= 2
        (:bestMove (response-data (.apply (game-state-service) request))))))

  (it "reports that game is 'tied' when given a full board"
    (let [request (request-with "o" "x,x,o,o,x,x,x,o,o")]
      (should= "tied"
        (:gameState (response-data (.apply (game-state-service) request))))))

  (it "reports that game is 'won' when given a board where a player won"
    (let [request (request-with "o" "x,x,x,o,o,_,_,_,_")]
      (should= "won"
        (:gameState (response-data (.apply (game-state-service) request))))))

  (it "reports that game is 'inProgress' when game is not over"
    (let [request (request-with "x" "x,x,_,o,o,_,_,_,_")]
      (should= "inProgress"
        (:gameState (response-data (.apply (game-state-service) request)))))))
