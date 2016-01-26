(ns minimax-server.best-move-spec
  (:import
    [scarvill.httpserver.response Response]
    [scarvill.httpserver.request Request RequestBuilder Method])
  (:require
    [speclj.core :refer :all]
    [minimax-server.spec-helper :refer :all]
    [minimax-server.best-move :refer :all]
    [minimax-server.minimax :refer [minimax]]
    [clojure.string :as str]))

(defn mock-minimax [expected-player expected-board]
  (fn [game-state]
    (let [current-player (:active-player game-state)
          board (:board game-state)]
      (if (and (= board expected-board) (= current-player expected-player))
          "expectation met"
          "expectation not met"))))

(describe "best move endpoint"

  (it "applies the minimax algorithm to request"
    (let [example-request (ttt-request "x" "x,o,x,o,x,o,x,o,_")]
      (with-redefs [minimax (mock-minimax :x [:x :o :x :o :x :o :x :o :_])]
        (should=
          "expectation met"
          (get-body-as-str (.apply (best-move-service) example-request))))))

  (it "returns the best move selected via minimax in the response body"
    (let [example-request (ttt-request "o" "x,x,_,o,_,_,_,_,_")]
      (should= "2" (get-body-as-str (.apply (best-move-service) example-request))))))
