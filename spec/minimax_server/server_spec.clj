(ns minimax-server.server-spec
  (:import
    [scarvill.httpserver.server ServerConfiguration HttpService]
    [scarvill.httpserver.routing RouteRequest]
    [scarvill.httpserver.response Status]
    [scarvill.httpserver.request RequestBuilder Method])
  (:require
    [speclj.core :refer :all]
    [minimax-server.server :refer [minimax-server-config ttt-router]]))

(defn null-router [] (new RouteRequest))

(describe "server"

  (describe "routing requests"

    (it "returns a 200 response to a request sent to the root route"
      (let [request (.build (doto (new RequestBuilder)
                      (.setMethod (Method/GET))
                      (.setURI "/")))]
        (should= (Status/OK) (.getStatus (.apply ttt-router request)))))

    (it "returns a 200 response to a valid request sent to the game_state API endpoint"
      (let [request (.build (doto (new RequestBuilder)
                      (.setMethod (Method/GET))
                      (.setURI "/api/game_state")
                      (.setParameter "board" "x,o,x,o,x,o,x,o,_")
                      (.setParameter "current_player" "x")))]
        (should= (Status/OK) (.getStatus (.apply ttt-router request)))))

    (it "returns a 200 response to a valid request sent to the best_move API endpoint"
      (let [request (.build (doto (new RequestBuilder)
                      (.setMethod (Method/GET))
                      (.setURI "/api/best_move")
                      (.setParameter "board" "x,o,x,o,x,o,x,o,_")
                      (.setParameter "current_player" "x")))]
        (should= (Status/OK) (.getStatus (.apply ttt-router request))))))

  (describe "configuration"

    (it "implements ServerConfiguration"
      (should-be-a ServerConfiguration (minimax-server-config 0 "" (null-router))))

    (it "returns port given at init"
      (should= 5000 (.getPort (minimax-server-config 5000 "" (null-router)))))

    (it "returns public directory given at init"
      (should= "/public" (.getPublicDirectory (minimax-server-config 0 "/public" (null-router)))))

    (it "returns an HttpService"
      (should-be-a HttpService (.getService (minimax-server-config 0 "" (null-router)))))))
