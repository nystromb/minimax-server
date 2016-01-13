(ns minimax-server.core-spec
  (:import )
  (:require [speclj.core :refer :all]
            [minimax-server.core :refer :all]))

(describe "temporary tests"
  (it "can use things from http server jar"
    (should-be-a scarvill.httpserver.response.ResponseBuilder (new scarvill.httpserver.response.ResponseBuilder))))
