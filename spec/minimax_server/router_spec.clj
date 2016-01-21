(ns minimax-server.router-spec
  (:import [scarvill.httpserver.response Response]
           [scarvill.httpserver.request Request RequestBuilder Method])
  (:require [speclj.core :refer :all]
            [minimax-server.router :refer :all]
            [clojure.string :as str]))

(describe "router"

  (defn request-with [current-player board]
    (.build (doto (new RequestBuilder)
        (.setParameter "current_player" current-player)
        (.setParameter "board" board)
        (.setURI "/"))))

  (defn get-body-as-str [response]
    (String. (.getBody response)))

  (def example-request (request-with "x" "x,o,x,o,x,o,x,o,_"))

  (it "returns the result of evaluating given function in the body of the response"
      (should= "foo"
               (get-body-as-str (.apply (minimax-router (fn [_] "foo")) example-request))))

  (it "parses the current player from request parameters"
      (let [return-active-player (fn [state] (:active-player state))]
        (should= ":x"
                 (get-body-as-str (.apply (minimax-router return-active-player) example-request)))))

  (it "parses the board state from request parameters"
      (let [return-board (fn [state] (:board state))]
        (should= "[:x :o :x :o :x :o :x :o :_]"
                 (get-body-as-str (.apply (minimax-router return-board) example-request))))))
