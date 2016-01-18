(ns minimax-server.router
  (:import [java.util.function Function]
           [scarvill.httpserver.response ResponseBuilder Status])
  (:require [clojure.string :as str]))

(defn response-with-body [body]
  (.build (doto (ResponseBuilder.)
               (.setStatus (Status/OK))
               (.setBody (byte-array (map byte (str body)))))))

(defn get-current-player [request]
  (.getParameterValue request "current_player"))

(defn get-board [request]
  (str/split (.getParameterValue request "board") #","))

(defn minimax-router [get-move]
  (proxy [Function] []
    (apply [request]
           (response-with-body (get-move (get-current-player request) (get-board request))))))
