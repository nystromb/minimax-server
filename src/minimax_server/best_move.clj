(ns minimax-server.best-move
  (:import
    [java.util.function Function]
    [scarvill.httpserver.response ResponseBuilder Status]
    [scarvill.httpserver.request Request])
  (:require
    [clojure.string :as str]
    [minimax-server.parse-request :as parse]
    [minimax-server.minimax :refer [new-state minimax]]))

(set! *warn-on-reflection* true)

(defn best-move-response [move]
  (.build
    (doto (new ResponseBuilder)
      (.setStatus (Status/OK))
      (.setBody (byte-array (map byte (str move)))))))

(defn best-move-service []
  (reify Function (apply [this request]
    (best-move-response (minimax (parse/get-game-state request))))))
