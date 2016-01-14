(ns minimax-server.router-spec
  (:import [scarvill.httpserver.response Response]
           [scarvill.httpserver.request Request RequestBuilder Method])
  (:require [speclj.core :refer :all]
            [minimax-server.router :refer :all]
            [clojure.string :as str]))

(describe "router"

  (defn request-with [current-player board]
    (-> (new RequestBuilder)
        (.setParameter "current_player" current-player)
        (.setParameter "board" board)
        (.setURI "/")
        .build))

  (defn get-body-as-str [response]
    (String. (.getBody response)))

  (def example-request (request-with "x" "x,o,x,o,x,o,x,o,_"))

  (it "returns a response"
     (should-be-a Response (.apply (minimax-router (fn [_ _] "foo")) example-request)))

  (it "returns the result of evaluating given function in the body of the response"
      (should= "foo"
               (get-body-as-str (.apply (minimax-router (fn [_ _] "foo")) example-request))))

  (it "parses the current player from request parameters"
      (let [return-given-mark (fn [mark _] mark)]
        (should= "x"
                 (get-body-as-str (.apply (minimax-router return-given-mark) example-request)))))

  (it "parses the board state from request parameters"
      (let [return-given-board-as-string (fn [_ board] (str/join " " board))]
        (should= "x o x o x o x o _"
                 (get-body-as-str (.apply (minimax-router return-given-board-as-string) example-request))))))
