(ns minimax-server.server-spec
  (:import
    [scarvill.httpserver.server ServerConfiguration HttpService]
    [scarvill.httpserver.routing RouteRequest])
  (:require
    [speclj.core :refer :all]
    [minimax-server.server :refer [minimax-server-config]]))

(defn router [] (new RouteRequest))

(describe "server configuration"

  (it "implements ServerConfiguration"
    (should-be-a ServerConfiguration (minimax-server-config 0 "" (router))))

  (it "returns port given at init"
    (should= 5000 (.getPort (minimax-server-config 5000 "" (router)))))

  (it "returns public directory given at init"
    (should= "/public" (.getPublicDirectory (minimax-server-config 0 "/public" (router)))))

  (it "returns an HttpService"
    (should-be-a HttpService (.getService (minimax-server-config 0 "" (router))))))
