(ns minimax-server.core
  (:import [scarvill.httpserver.server Server])
  (:require [minimax-server.minimax :refer [minimax]]
            [minimax-server.server-configuration :refer [minimax-server-config]]
            [minimax-server.router :refer [new-router]]
            [minimax-server.best-move :refer [best-move]]))

(def ttt-router
  (new-router {"/api/best_move" (best-move)}))

(def ttt-server
  (new Server (minimax-server-config 5000 ttt-router)))

(defn main []
  (.start ttt-server))
