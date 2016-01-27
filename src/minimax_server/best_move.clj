(ns minimax-server.best-move
  (:import
    [java.util.function Function]
    [scarvill.httpserver.response ResponseBuilder Status]
    [scarvill.httpserver.request Request])
  (:require
    [clojure.string :as str]
    [minimax-server.parse-request :as parse]
    [minimax-server.generate-response :refer [generate-response]]
    [minimax-server.minimax :refer [new-state minimax]]))

(set! *warn-on-reflection* true)

(defn best-move-service []
  (reify Function (apply [this request]
    (generate-response (minimax (parse/get-game-state request))))))
