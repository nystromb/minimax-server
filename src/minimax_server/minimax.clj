(ns minimax-server.minimax
  (:require
    [minimax-server.tic-tac-toe :refer :all]
    [clojure.set :refer [difference]]))

(defn new-state [active-player inactive-player board]
  {:active-player active-player :inactive-player inactive-player :board board})

(defn terminal-state? [{:keys [board]}]
  (game-over? board))

(defn generate-successors [{:keys [active-player inactive-player board]}]
  (let [successor-state (fn [space]
    (new-state inactive-player active-player (mark-board board active-player space)))]
    (map successor-state (available-spaces board))))

(defn evaluate [{:keys [active-player inactive-player board]}]
  (cond (player-won? active-player board) 1
        (player-won? inactive-player board) -1
        :else 0))

(defn recursively-evaluate [{:keys [active-player] :as state}]
  (if (terminal-state? state)
      (evaluate state)
      (apply max (map (comp - recursively-evaluate) (generate-successors state)))))

(defn last-marked-space [initial-state successor-state]
  (first (difference (set (available-spaces (:board initial-state)))
                     (set (available-spaces (:board successor-state))))))

(defn max-by [element-to-num elements]
  (key (first (sort-by val > (zipmap elements (map element-to-num elements))))))

(defn best-successor-state [successor-states]
  (max-by (comp - recursively-evaluate) successor-states))

(defn minimax [initial-state]
  (if (terminal-state? initial-state)
    nil
    (last-marked-space initial-state
      (best-successor-state
        (generate-successors initial-state)))))
