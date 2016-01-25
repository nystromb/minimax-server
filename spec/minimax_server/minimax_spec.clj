(ns minimax-server.minimax-spec
  (:require [speclj.core :refer :all]
            [minimax-server.tic-tac-toe :refer :all]
            [minimax-server.minimax :refer :all]))

(describe "tic tac toe minimax algorithm"

  (context "checking for a terminal state"

    (it "returns true if game state has a board where a player won"
      (let [board [:x :x :x
                   :_ :_ :_
                   :_ :_ :_]]
        (should-be terminal-state? (new-state :x :o board))))

    (it "returns true if game state has a board where the game ended in a draw"
      (let [board [:x :o :x
                   :x :o :o
                   :o :x :x]]
        (should-be terminal-state? (new-state :x :o board))))

    (it "returns false if game state has a board where the game has not ended"
      (let [board [:_ :_ :_
                   :_ :_ :_
                   :_ :_ :_]]
        (should-not-be terminal-state? (new-state :x :o board)))))

  (context "evaluating a game state"

    (it "returns 1 if the current player won"
      (let [board [:x :x :x
                   :_ :_ :_
                   :_ :_ :_]]
        (should= 1 (recursively-evaluate (new-state :x :o board)))))

    (it "returns -1 if the player other than the current player won"
      (let [board [:x :x :x
                   :_ :_ :_
                   :_ :_ :_]]
        (should= -1 (recursively-evaluate (new-state :o :x board)))))

    (it "returns 0 when neither player has won"
      (let [board [:x :o :x
                   :x :o :o
                   :o :x :x]]
        (do (should= 0 (recursively-evaluate (new-state :x :o board)))
            (should= 0 (recursively-evaluate (new-state :o :x board))))))

    (it "returns 1 if the current player can make a winning move"
      (let [board [:x :x :_
                   :_ :_ :_
                   :_ :_ :_]]
        (should= 1 (recursively-evaluate (new-state :x :o board)))))

    (it "returns -1 if the current player can't prevent the other player from winning"
      (let [board [:x :x :_
                   :x :_ :_
                   :_ :_ :_]]
        (should= -1 (recursively-evaluate (new-state :o :x board)))))

    (it "returns 0 if the game would end in a draw with both players playing optimally"
      (let [board [:x :x :o
                   :o :o :_
                   :x :_ :_]]
        (should= 0 (recursively-evaluate (new-state :x :o board))))))

  (context "generating subsequent game states"

    (it "returns a game state for every available move on board in given game state"
      (let [board [:_ :_ :_
                   :_ :_ :_
                   :_ :_ :_]]
        (should= (count board)
                 (count (generate-successors (new-state :x :o board))))))

    (it "returns game states where the active and inactive players have been swapped"
      (let [active-player :x inactive-player :o
            initial-state (new-state active-player inactive-player [:_ :_ :_
                                                                    :_ :_ :_
                                                                    :_ :_ :_])
            players-swapped (fn [state] (and (= active-player (:inactive-player state))
                                             (= inactive-player (:active-player state))))]
        (should-be #(every? players-swapped %) (generate-successors initial-state))))

    (it "returns all possible game states that can result from given game state"
      (let [active-player :x inactive-player :o
            board [:_ :_ :_
                   :x :o :x
                   :o :x :o]
            available-spaces [0 1 2]
            initial-state (new-state active-player inactive-player board)
            successor-state (fn [marked-space]
              (new-state inactive-player active-player
                (mark-board board active-player marked-space)))]
        (should== (map successor-state available-spaces) (generate-successors initial-state)))))

  (context "applying the minimax algorithm"

    (it "returns the move that blocks opponent when they could win next turn"
      (let [state (new-state :o :x [:x :_ :_
                                    :x :o :_
                                    :_ :_ :_])]
        (should= 6 (minimax state))))

    (it "returns a winning move when a fork is not possible"
      (let [state (new-state :o :x [:x :x :_
                                    :o :o :_
                                    :x :_ :_])]
        (should= 5 (minimax state))))

    (it "returns nil when there are no possible moves"
      (let [state (new-state :o :x [:x :x :o
                                    :o :o :x
                                    :x :x :o])]
        (should= nil (minimax state))))

    (it "returns the move with the lower index when both a fork or a win is possible"
      (let [fork-state (new-state :x :o [:x :_ :_
                                         :x :o :o
                                         :_ :_ :_])
            win-state (new-state :o :x [:x :x :_
                                        :_ :o :_
                                        :_ :o :_])]
        (do (should= 1 (minimax fork-state))
            (should= 2 (minimax win-state)))))))
