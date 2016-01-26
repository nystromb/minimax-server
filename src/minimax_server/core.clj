(ns minimax-server.core
  (:import
    [scarvill.httpserver.server Server])
  (:require
    [minimax-server.minimax :refer [minimax]]
    [minimax-server.server-configuration :refer [minimax-server-config]]
    [minimax-server.router :refer [new-router]]
    [minimax-server.best-move :refer [best-move-service]]
    [minimax-server.game-state :refer [game-state-service]]))

(def ttt-router
  (new-router {
    "/api/best_move" (best-move-service)
    "/api/game_state" (game-state-service)}))

(def ttt-server
  (new Server (minimax-server-config 5000 ttt-router)))

(defn main []
  (.start ttt-server))
