(ns minimax-server.parse-request
  (:import
    [java.util.function Function]
    [scarvill.httpserver.response ResponseBuilder Status]
    [scarvill.httpserver.request Request])
  (:require
    [minimax-server.minimax :refer [new-state minimax]]
    [clojure.string :as str]))

(defn other-mark [mark marks]
  (first (remove #(= mark %) marks)))

(defn parse-board [request]
  (vec (map keyword (str/split (.getParameterValue request "board") #","))))

(defn parse-active-player [request]
  (keyword (.getParameterValue request "current_player")))

(defn get-game-state [^Request request]
  (let [board (parse-board request)
        active-player (parse-active-player request)
        inactive-player (other-mark active-player [:x :o])]
    (new-state active-player inactive-player board)))
