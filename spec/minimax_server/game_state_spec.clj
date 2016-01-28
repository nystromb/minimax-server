(ns minimax-server.game-state-spec
  (:require
    [speclj.core :refer :all]
    [minimax-server.spec-helper :refer :all]
    [minimax-server.game-state :refer :all]
    [clojure.data.json :as json]))

(defn response-data [response]
  (json/read-str (String. (.getBody response)) :key-fn keyword))

(describe "game state endpoint"

  (it "returns the best move for given player and board, selected via minimax"
    (let [request (ttt-request "o" "x,x,_,o,_,_,_,_,_")]
      (should= 2
        (:bestMove (response-data (.apply (game-state-service) request))))))

  (it "reports that game is 'tied' when given a full board"
    (let [request (ttt-request "o" "x,x,o,o,x,x,x,o,o")]
      (should= "tied"
        (:gameState (response-data (.apply (game-state-service) request))))))

  (it "reports that game is 'won' when given a board where a player won"
    (let [request (ttt-request "o" "x,x,x,o,o,_,_,_,_")]
      (should= "won"
        (:gameState (response-data (.apply (game-state-service) request))))))

  (it "reports that game is 'inProgress' when game is not over"
    (let [request (ttt-request "x" "x,x,_,o,o,_,_,_,_")]
      (should= "inProgress"
        (:gameState (response-data (.apply (game-state-service) request))))))

  (it "reports that the game is 'won' when board is full and a player won"
    (let [request (ttt-request "o" "x,x,x,o,o,x,x,o,o")]
      (should= "won"
        (:gameState (response-data (.apply (game-state-service) request)))))))
