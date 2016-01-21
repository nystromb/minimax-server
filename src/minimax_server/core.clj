(ns minimax-server.core
  (:import [scarvill.httpserver.server Server])
  (:require [minimax-server.minimax :refer [minimax]]
            [minimax-server.server-configuration :refer [minimax-server-config]]))

(def ttt-server
  (new Server (minimax-server-config 5000 minimax)))

(defn main []
  (.start ttt-server))
