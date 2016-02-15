(ns minimax-server.minimax
  (:require
    [clojure.set :refer [difference]]
    [minimax-server.game :refer [game-over? mark-board available-spaces player-won?]]))

(defn new-state [active-player inactive-player board]
  {:active-player active-player :inactive-player inactive-player :board board})

(defn terminal-state? [{:keys [board]}]
  (game-over? board))

(defn generate-successors [{:keys [active-player inactive-player board]}]
  (let [successor-state (fn [space]
    (new-state inactive-player active-player (mark-board board active-player space)))]
    (map successor-state (available-spaces board))))

(defn score-state [{:keys [active-player] :as state}]
  (let [score (fn [{:keys [active-player inactive-player board]}]
    (cond (player-won? active-player board) 1
          (player-won? inactive-player board) -1
          :else 0))]
    (if (terminal-state? state)
        (score state)
        (apply max (map (comp - score-state) (generate-successors state))))))

(defn last-marked-space [initial-state successor-state]
  (first (difference (set (available-spaces (:board initial-state)))
                     (set (available-spaces (:board successor-state))))))

(defn max-by [element-to-num elements]
  (key (first (sort-by val > (zipmap elements (map element-to-num elements))))))

(defn best-successor-state [successor-states]
  (max-by (comp - score-state) successor-states))

(defn minimax [initial-state]
  (if (terminal-state? initial-state)
    nil
    (last-marked-space initial-state
      (best-successor-state
        (generate-successors initial-state)))))
