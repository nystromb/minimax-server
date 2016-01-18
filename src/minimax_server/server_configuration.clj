 (ns minimax-server.server-configuration
  (:import [scarvill.httpserver.server ServerConfiguration HttpService Logger]
           [java.io PrintStream ByteArrayOutputStream])
  (:require [minimax-server.router :refer :all]))

(def null-logger
  (proxy [Logger] [(new PrintStream (new ByteArrayOutputStream))]
    (logResponse [_] nil)
    (logRequest [_] nil)
    (logException [_] nil)))

(defn minimax-server-config [port get-move]
  (proxy [ServerConfiguration] []
    (getPort [] port)
    (getPublicDirectory [] nil)
    (getService [] (new HttpService null-logger (minimax-router get-move)))))
