(ns minimax-server.router-spec
  (:import [java.util.function Function]
           [scarvill.httpserver.response ResponseBuilder Status]
           [scarvill.httpserver.request Request RequestBuilder Method])
  (:require [speclj.core :refer :all]
            [minimax-server.router :refer :all]))

(defn new-request [url]
  (.build (doto (new RequestBuilder)
    (.setURI url)
    (.setMethod Method/GET))))

(defn new-response [body]
  (.build
    (doto (new ResponseBuilder)
      (.setStatus (Status/OK))
      (.setBody (byte-array (map byte (str body)))))))

(defn get-body-as-str [response]
  (String. (.getBody response)))

(defn mock-handler [value-to-return]
  (reify Function (apply [this request]
    (new-response value-to-return))))

(describe "routing scheme"

  (it "adds a route for each url-handler mapping"
    (let [request ()
          routes {"/" (mock-handler "root route") "/api" (mock-handler "api route")}
          root-response (get-body-as-str (.apply (new-router routes) (new-request "/")))
          foo-response (get-body-as-str (.apply (new-router routes) (new-request "/api")))]
      (should (and (= "root route" root-response) (= "api route" foo-response))))))
