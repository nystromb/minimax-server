(ns minimax-server.router-spec
  (:import
    [java.util.function Function]
    [scarvill.httpserver.response ResponseBuilder Status]
    [scarvill.httpserver.request RequestBuilder Method])
  (:require
    [speclj.core :refer :all]
    [minimax-server.spec-helper :refer :all]
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

(defn mock-handler [value-to-return]
  (reify Function (apply [this request]
    (new-response value-to-return))))

(defn route-request [router request]
  (get-body-as-str (.apply router request)))

(describe "routing scheme"

  (it "adds a route for each url-handler mapping"
    (let [router (new-router {"/" (mock-handler "root route") "/api" (mock-handler "api route")})
          root-response (route-request router (new-request "/"))
          foo-response (route-request router (new-request "/api"))]
      (should (and
        (= "root route" root-response)
        (= "api route" foo-response))))))
