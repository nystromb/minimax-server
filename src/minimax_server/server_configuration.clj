(ns minimax-server.server-configuration
  (:import
    [scarvill.httpserver.server ServerConfiguration HttpService Logger]
    [java.io PrintStream ByteArrayOutputStream]
    [java.nio.file Paths]
    [java.net URI])
  (:require
    [minimax-server.router :refer :all]))

(def null-logger
  (proxy [Logger] [(new PrintStream (new ByteArrayOutputStream))]
    (logResponse [_] nil)
    (logRequest [_] nil)
    (logException [_] nil)))

(defn minimax-server-config [port public-directory router]
  (do
    (.routeToResourcesInDirectory router (.toPath (clojure.java.io/file public-directory)))
    (proxy [ServerConfiguration] []
      (getPort [] port)
      (getPublicDirectory [] public-directory)
      (getService [] (new HttpService null-logger router)))))
