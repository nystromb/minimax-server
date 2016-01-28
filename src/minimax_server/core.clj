(ns minimax-server.core
  (:import
    [scarvill.httpserver.server Server]
    [scarvill.httpserver.resource FileResource]
    [scarvill.httpserver.routing GetRouteResource])
  (:require
    [minimax-server.server-configuration :refer [minimax-server-config]]
    [minimax-server.router :refer [new-router]]
    [minimax-server.best-move :refer [best-move-service]]
    [minimax-server.game-state :refer [game-state-service]]))

(defn web-interface []
  (new GetRouteResource
    (new FileResource (.toPath (clojure.java.io/file "./public/index.html")))))

(def ttt-router
  (new-router {
    "/" (web-interface)
    "/api/best_move" (best-move-service)
    "/api/game_state" (game-state-service)}))

(defn -main []
  (.start
    (new Server
      (minimax-server-config 5000 "./public" ttt-router))))
