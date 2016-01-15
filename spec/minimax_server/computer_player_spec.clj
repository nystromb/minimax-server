(ns minimax-server.computer-player-spec
  (:require [speclj.core :refer :all]
            [minimax-server.computer-player :refer :all]))

(describe "a computer player"
  (it "will return a move for an available space"
    (let [board1 ["x" "o" "x" "o" "x" "o" "x" "o" "_"]
          board2 ["_" "o" "x" "o" "x" "o" "x" "o" "x"]]
      (should= 8 (stupid-move "x" board1))
      (should= 0 (stupid-move "x" board2))))

  (it "chooses a move within the bounds of the board"
    (let [board ["_" "_" "_" "_" "_" "_" "_" "_" "_"] move (stupid-move "x" board)]
      (should (<= 0 move))
      (should (<= move 8)))))
