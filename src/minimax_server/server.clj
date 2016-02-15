(ns minimax-server.server
  (:import
    [scarvill.httpserver.server Server ServerConfiguration HttpService Logger]
    [scarvill.httpserver.resource FileResource]
    [scarvill.httpserver.routing GetRouteResource]
    [java.io PrintStream ByteArrayOutputStream])
  (:require
    [minimax-server.router :refer [new-router best-move-service game-state-service]]))

(defn web-interface []
  (new GetRouteResource
    (new FileResource (.toPath (clojure.java.io/file "./web_interface/dist/index.html")))))

(def ttt-router
  (new-router {
    "/" (web-interface)
    "/api/best_move" (best-move-service)
    "/api/game_state" (game-state-service)}))

(def null-logger
  (proxy [Logger] [(new PrintStream (new ByteArrayOutputStream))]
    (logResponse [_] nil)
    (logRequest [_] nil)
    (logException [_] nil)))

(defn minimax-server-config [port public-directory router]
  (do
    (.routeToResourcesInDirectory router (.toPath (clojure.java.io/file public-directory)))
    (proxy [ServerConfiguration] []
      (getPort [] port)
      (getPublicDirectory [] public-directory)
      (getService [] (new HttpService null-logger router)))))

(defn -main []
  (.start
    (new Server
      (minimax-server-config 5000 "./web_interface/dist" ttt-router))))
