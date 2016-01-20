(ns minimax-server.tic-tac-toe-spec
  (:require [speclj.core :refer :all]
            [minimax-server.tic-tac-toe :refer :all]))

(describe "tic tac toe minimax helper functions"

  (context "player win detection"

    (it "returns true if player of given mark has won"
      (let [board ["x" "x" "x"
                   "_" "_" "_"
                   "_" "_" "_"]]
        (should= true (player-won? "x" board))))
    
    (it "returns false if player of given mark has not won" 
      (let [board ["x" "o" "x"
                   "x" "o" "o"
                   "o" "x" "x"]]
        (should= false (player-won? "o" board)))))
    
  (context "game over detection"

    (it "should be false if game is not over"
      (let [board ["_" "_" "_"
                   "_" "_" "_"
                   "_" "_" "_"]]
        (should-not-be game-over? board)))

    (it "should be true if game is a draw"
      (let [board ["x" "o" "x"
                   "x" "o" "o"
                   "o" "x" "x"]]
        (should-be game-over? board)))

    (it "should be true if a player x has a winning line"
      (let [board ["x" "x" "x"
                   "_" "_" "_"
                   "_" "_" "_"]]
        (should-be game-over? board)))

    (it "should be true if player o has a winning line"
      (let [board ["o" "_" "_"
                   "_" "o" "_"
                   "_" "_" "o"]]
          (should-be game-over? board)))
    
    (it "should not be game over if there is no winning line"
      (let [board ["o" "x" "o"
                   "_" "x" "_"
                   "o" "_" "_"]]
          (should-not-be game-over? board)))))
