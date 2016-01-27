(ns minimax-server.generate-response
  (:import
    [scarvill.httpserver.response ResponseBuilder Status])
  (:require
    [clojure.data.json :as json]))

(defn generate-response [body]
  (.build
  (doto (new ResponseBuilder)
    (.setStatus (Status/OK))
    (.setHeader "Access-Control-Allow-Origin" "*")
    (.setBody (byte-array (map byte (str body)))))))
