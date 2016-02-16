(ns minimax-server.router-spec
  (:import
    [java.util.function Function]
    [scarvill.httpserver.response ResponseBuilder Status]
    [scarvill.httpserver.request RequestBuilder Method])
  (:require
    [speclj.core :refer :all]
    [minimax-server.router :refer [new-router game-state-service best-move-service]]
    [clojure.data.json :as json]
    [minimax-server.minimax :refer [minimax]]))

(defn get-body-as-str [response]
  (String. (.getBody response)))

(defn new-request [url]
  (.build (doto (new RequestBuilder)
    (.setURI url)
    (.setMethod Method/GET))))

(defn new-response [body]
  (.build
    (doto (new ResponseBuilder)
      (.setStatus (Status/OK))
      (.setBody (byte-array (map byte (str body)))))))

(defn mock-handler [value-to-return]
  (reify Function (apply [this request]
    (new-response value-to-return))))

(defn route-request [router request]
  (get-body-as-str (.apply router request)))

(defn ttt-request [current-player board]
  "Constructs a new Request object with the given current player and board"
  (.build (doto (new RequestBuilder)
    (.setParameter "current_player" current-player)
    (.setParameter "board" board)
    (.setURI "/foo"))))

(describe "routing"

  (it "adds a route for each url-handler mapping"
    (let [router (new-router {"/" (mock-handler "root route") "/api" (mock-handler "api route")})
          root-response (route-request router (new-request "/"))
          foo-response (route-request router (new-request "/api"))]
      (should (and
        (= "root route" root-response)
        (= "api route" foo-response)))))

  (describe "game state endpoint"

    (defn response-data [response]
      (json/read-str (String. (.getBody response)) :key-fn keyword))

    (it "returns the best move for given player and board, selected via minimax"
      (let [request (ttt-request "o" "x,x,_,o,_,_,_,_,_")]
        (should= 2
          (:bestMove (response-data (.apply (game-state-service) request))))))

    (it "reports that game is 'tied' when given a full board"
      (let [request (ttt-request "o" "x,x,o,o,x,x,x,o,o")]
        (should= "tied"
          (:gameState (response-data (.apply (game-state-service) request))))))

    (it "reports that game is 'won' when given a board where a player won"
      (let [request (ttt-request "o" "x,x,x,o,o,_,_,_,_")]
        (should= "won"
          (:gameState (response-data (.apply (game-state-service) request))))))

    (it "reports that game is 'inProgress' when game is not over"
      (let [request (ttt-request "x" "x,x,_,o,o,_,_,_,_")]
        (should= "inProgress"
          (:gameState (response-data (.apply (game-state-service) request))))))

    (it "reports that the game is 'won' when board is full and a player won"
      (let [request (ttt-request "o" "x,x,x,o,o,x,x,o,o")]
        (should= "won"
          (:gameState (response-data (.apply (game-state-service) request)))))))

  (describe "best move endpoint"

    (defn mock-minimax [expected-player expected-board]
      (fn [game-state]
        (let [current-player (:active-player game-state)
              board (:board game-state)]
          (if (and (= board expected-board) (= current-player expected-player))
              "expectation met"
              "expectation not met"))))

    (it "applies the minimax algorithm to request"
      (let [example-request (ttt-request "x" "x,o,x,o,x,o,x,o,_")]
        (with-redefs [minimax (mock-minimax :x [:x :o :x :o :x :o :x :o :_])]
          (should=
            "expectation met"
            (get-body-as-str (.apply (best-move-service) example-request))))))

    (it "returns the best move selected via minimax in the response body"
      (let [example-request (ttt-request "o" "x,x,_,o,_,_,_,_,_")]
        (should= "2" (get-body-as-str (.apply (best-move-service) example-request))))))

  (describe "handling bad client requests"

    (it "returns a 400 response when a request given to best-move-service doesn't have a current_player"
      (let [bad-request (.build (doto (new RequestBuilder)
                          (.setParameter "board" "_,_,_,_,_,_,_,_,_")
                          (.setURI "/foo")))]
        (should= (Status/BAD_REQUEST) (.getStatus (.apply (best-move-service) bad-request)))))

    (it "returns a 400 response when a request given to best-move-service doesn't have a board"
      (let [bad-request (.build (doto (new RequestBuilder)
                          (.setParameter "current_player" "x")
                          (.setURI "/foo")))]
        (should= (Status/BAD_REQUEST) (.getStatus (.apply (best-move-service) bad-request)))))

    (it "returns a 400 response when a request given to game-state-service doesn't have a current_player"
      (let [bad-request (.build (doto (new RequestBuilder)
                          (.setParameter "board" "_,_,_,_,_,_,_,_,_")
                          (.setURI "/foo")))]
        (should= (Status/BAD_REQUEST) (.getStatus (.apply (game-state-service) bad-request)))))

    (it "returns a 400 response when a request given to game-state-service doesn't have a board"
      (let [bad-request (.build (doto (new RequestBuilder)
                          (.setParameter "current_player" "x")
                          (.setURI "/foo")))]
        (should= (Status/BAD_REQUEST) (.getStatus (.apply (game-state-service) bad-request)))))))
