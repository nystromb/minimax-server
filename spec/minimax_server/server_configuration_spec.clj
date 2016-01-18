 (ns minimax-server.server-configuration-spec
  (:import [scarvill.httpserver.server ServerConfiguration HttpService])
  (:require [speclj.core :refer :all]
            [minimax-server.server-configuration :refer :all]))

(describe "server configuration"

  (it "implements ServerConfiguration"
      (should-be-a ServerConfiguration (minimax-server-config 5000 identity)))

  (it "returns port given at init"
      (should= 5000 (.getPort (minimax-server-config 5000 identity))))

  (it "returns nil for public directory"
      (should= nil (.getPublicDirectory (minimax-server-config 5000 identity))))

  (it "returns an HttpService"
      (should-be-a HttpService (.getService (minimax-server-config 5000 identity)))))
