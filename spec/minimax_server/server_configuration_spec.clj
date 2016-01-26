(ns minimax-server.server-configuration-spec
  (:import
    [scarvill.httpserver.server ServerConfiguration HttpService]
    [java.util.function Function])
  (:require
    [speclj.core :refer :all]
    [minimax-server.server-configuration :refer :all]))

(def mock-router
  (reify Function (apply [this request])))

(describe "server configuration"

  (it "implements ServerConfiguration"
    (should-be-a ServerConfiguration (minimax-server-config 5000 mock-router)))

  (it "returns port given at init"
    (should= 5000 (.getPort (minimax-server-config 5000 mock-router))))

  (it "returns nil for public directory"
    (should= nil (.getPublicDirectory (minimax-server-config 5000 mock-router))))

  (it "returns an HttpService"
    (should-be-a HttpService (.getService (minimax-server-config 5000 mock-router)))))
