(ns minimax-server.minimax
  (:require [minimax-server.tic-tac-toe :refer :all]
            [clojure.set :refer [difference]]))

(defn other-mark [mark marks]
  (first (remove #(= mark %) marks)))

(defn new-state [active-player inactive-player board]
  {:active-player active-player :inactive-player inactive-player :board board})

(defn terminal-state? [{:keys [board]}]
  (game-over? board))

(defn evaluate [target-player {:keys [active-player inactive-player board]}]
  (let [player-marks [active-player inactive-player]]
    (cond (player-won? target-player board) 1
          (player-won? (other-mark target-player player-marks) board) -1
          :else 0)))

(defn generate-successors [{:keys [active-player inactive-player board]}]
  (let [successor-state (fn [space]
    (new-state inactive-player active-player (mark-board board active-player space)))]
    (map successor-state (available-spaces board))))
