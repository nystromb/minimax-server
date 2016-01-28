(ns minimax-server.game-state
  (:import
    [java.util.function Function])
  (:require
    [minimax-server.tic-tac-toe :refer [is-draw? game-over?]]
    [minimax-server.minimax :refer [minimax]]
    [minimax-server.parse-request :as parse]
    [minimax-server.generate-response :refer [generate-response]]
    [clojure.data.json :as json]))

(defn game-state-label [{:keys [board active-player inactive-player]}]
  (cond
    (is-draw? board) "tied"
    (game-over? board) "won"
    :else "inProgress"))

(defn game-state-service []
  (reify Function (apply [this request]
    (let [game-state (parse/get-game-state request)]
      (generate-response
        (json/write-str {
          :bestMove (minimax game-state)
          :gameState (game-state-label game-state)}))))))
