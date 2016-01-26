(ns minimax-server.game-state
  (:import
    [java.util.function Function]
    [scarvill.httpserver.response ResponseBuilder Status]
    [scarvill.httpserver.request Request])
  (:require
    [minimax-server.tic-tac-toe :refer [is-draw? game-over?]]
    [minimax-server.minimax :refer [new-state minimax]]
    [minimax-server.parse-request :as parse]
    [clojure.string :as str]
    [clojure.data.json :as json]))

(defn game-state-label [{:keys [board active-player inactive-player]}]
  (cond
    (is-draw? board) "tied"
    (game-over? board) "won"
    :else "inProgress"))

(defn game-state-response [best-move game-state]
  (let [body (json/write-str {:bestMove best-move :gameState game-state})]
    (.build
    (doto (new ResponseBuilder)
      (.setStatus (Status/OK))
      (.setBody (byte-array (map byte (str body))))))))

(defn game-state-service []
  (reify Function (apply [this request]
    (let [game-state (parse/get-game-state request)]
      (game-state-response
        (minimax game-state)
        (game-state-label game-state))))))
