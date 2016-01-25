(ns minimax-server.router
  (:import [scarvill.httpserver.routing RouteRequest]
           [scarvill.httpserver.request Method])
  (:require [minimax-server.best-move :refer [best-move]]))

(defn new-router [routes]
  (let [router (new RouteRequest)]
    (do
      (doseq [[url handler] routes]
        (.addRoute router url Method/GET handler))
      router)))
