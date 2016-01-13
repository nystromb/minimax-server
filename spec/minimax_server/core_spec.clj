(ns minimax-server.core-spec
  (import [scarvill.httpserver.response ResponseBuilder])
  (require [speclj.core :refer :all]
            [minimax-server.core :refer :all]))

(describe "temporary tests"
  (it "can use things from http server jar"
    (should-be-a ResponseBuilder (new ResponseBuilder))))
