(ns minimax-server.best-move
  (:import [java.util.function Function]
           [scarvill.httpserver.response ResponseBuilder Status]
           [scarvill.httpserver.request Request])
  (:require [clojure.string :as str]
            [minimax-server.minimax :refer [new-state minimax]]))

(set! *warn-on-reflection* true)

(def blank-mark :_)

(defn other-mark [mark marks]
  (first (remove #(= mark %) marks)))

(defn response-with-body [body]
  (.build
    (doto (new ResponseBuilder)
      (.setStatus (Status/OK))
      (.setBody (byte-array (map byte (str body)))))))

(defn get-game-state [^Request request]
  (let [board (vec (map keyword (str/split (.getParameterValue request "board") #",")))
        active-player (keyword (.getParameterValue request "current_player"))]
    (new-state active-player (other-mark active-player [:x :o]) board)))

(defn best-move-service []
  (reify Function (apply [this request]
      (response-with-body (minimax (get-game-state request))))))
