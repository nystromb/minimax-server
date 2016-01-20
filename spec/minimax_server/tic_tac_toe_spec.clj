(ns minimax-server.tic-tac-toe-spec
  (:require [speclj.core :refer :all]
            [minimax-server.tic-tac-toe :refer :all]))

(describe "tic tac toe minimax helper functions"
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

    (it "should be true if a player has won at 0, 1, 2"
      (let [board ["x" "x" "x"
                   "_" "_" "_"
                   "_" "_" "_"]]
        (should-be game-over? board)))

    (it "should be true if a player has won at 3, 4, 5"
      (let [board ["_" "_" "_"
                   "x" "x" "x"
                   "_" "_" "_"]]
         (should-be game-over? board)))))
