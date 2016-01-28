(ns minimax-server.server-configuration-spec
  (:import
    [scarvill.httpserver.server ServerConfiguration HttpService]
    [scarvill.httpserver.routing RouteRequest]
    [java.util.function Function])
  (:require
    [speclj.core :refer :all]
    [minimax-server.server-configuration :refer :all]))

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
