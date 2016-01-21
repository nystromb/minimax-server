(ns minimax-server.tic-tac-toe-spec
  (:require [speclj.core :refer :all]
            [minimax-server.tic-tac-toe :refer :all]))

(describe "tic tac toe minimax helper functions"

  (context "getting a board's available spaces"

    (it "returns all blank spaces on given board"
      (let [board [:_ :_ :_
                   :_ :_ :_
                   :_ :_ :_]]
        (should== [0 1 2 3 4 5 6 7 8] (available-spaces board))))

    (it "returns nothing when there are no blank spaces on board"
      (let [board [:x :o :x
                   :x :o :o
                   :o :x :x]]
        (should-be empty? (available-spaces board)))))

  (context "marking a board"

    (it "marks board in given space if space is not already marked"
      (let [board [:_ :_ :_
                   :_ :_ :_
                   :_ :_ :_]
            space 0
            mark :x]
        (should= mark (nth (mark-board board mark space) space))))

    (it "does nothing if the space is marked"
      (let [board [:_ :_ :_
                   :_ :o :_
                   :_ :_ :_]
            space 4
            mark :x]
        (should-not-be #(= mark %) (nth (mark-board board mark space) space)))))

  (context "player win detection"

    (it "returns true if player of given mark has won"
      (let [board [:x :x :x
                   :_ :_ :_
                   :_ :_ :_]]
        (should= true (player-won? :x board))))

    (it "returns false if player of given mark has not won"
      (let [board [:x :o :x
                   :x :o :o
                   :o :x :x]]
        (should= false (player-won? :o board)))))

  (context "game over detection"

    (it "should be false if game is not over"
      (let [board [:_ :_ :_
                   :_ :_ :_
                   :_ :_ :_]]
        (should-not-be game-over? board)))

    (it "should be true if game is a draw"
      (let [board [:x :o :x
                   :x :o :o
                   :o :x :x]]
        (should-be game-over? board)))

    (it "should be true if a player x has a winning line"
      (let [board [:x :x :x
                   :_ :_ :_
                   :_ :_ :_]]
        (should-be game-over? board)))

    (it "should be true if player o has a winning line"
      (let [board [:o :_ :_
                   :_ :o :_
                   :_ :_ :o]]
          (should-be game-over? board)))

    (it "should not be game over if there is no winning line"
      (let [board [:o :x :o
                   :_ :x :_
                   :o :_ :_]]
          (should-not-be game-over? board)))))
