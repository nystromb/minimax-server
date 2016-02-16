(ns minimax-server.router
  (:import
    [scarvill.httpserver.routing RouteRequest]
    [scarvill.httpserver.request Method]
    [scarvill.httpserver.response ResponseBuilder Status]
    [java.util.function Function])
  (:require
    [clojure.data.json :as json]
    [clojure.string :as str]
    [minimax-server.minimax :refer [minimax new-state]]
    [minimax-server.game :refer [is-draw? game-over? other-mark]]))

(defn new-router [routes]
  (let [router (new RouteRequest)]
    (do (doseq [[url handler] routes] (.addRoute router url Method/GET handler)) router)))

(defn parse-board [request]
  (vec (map keyword (str/split (.getParameterValue request "board") #","))))

(defn parse-active-player [request]
  (keyword (.getParameterValue request "current_player")))

(defn get-game-state [request]
  (let [board (parse-board request)
        active-player (parse-active-player request)
        inactive-player (other-mark active-player [:x :o])]
    (new-state active-player inactive-player board)))

(defn generate-response [body]
  (.build
  (doto (new ResponseBuilder)
    (.setStatus (Status/OK))
    (.setHeader "Access-Control-Allow-Origin" "*")
    (.setBody (byte-array (map byte (str body)))))))

(def client-error-response
  (.build (doto (new ResponseBuilder)
    (.setStatus (Status/BAD_REQUEST))
    (.setBody (byte-array (map byte (str "400 Bad Request")))))))

(defn game-state-label [{:keys [board active-player inactive-player]}]
  (cond
    (is-draw? board) "tied"
    (game-over? board) "won"
    :else "inProgress"))

(defn includes? [element collection]
  ((comp boolean some) #{element} collection))

(defn has-parameters? [expected-parameters request]
  (let [request-parameters (into [] (.getParameterNames request))]
    (every? #(includes? % request-parameters) expected-parameters)))

(defn valid-board-parameter? [board]
  (let [marks (str/split board #",")]
    (and (< 8 (count marks)) (not (includes? "" marks)))))

(defn invalid? [request]
  (or
    (not (has-parameters? ["board" "current_player"] request))
    (not (valid-board-parameter? (.getParameterValue request "board")))))

(defn game-state-service []
  (reify Function (apply [this request]
    (if (invalid? request)
      client-error-response
      (let [game-state (get-game-state request)]
        (generate-response
          (json/write-str {
            :bestMove (minimax game-state)
            :gameState (game-state-label game-state)})))))))

(defn best-move-service []
  (reify Function (apply [this request]
    (if (invalid? request)
      client-error-response
      (generate-response (minimax (get-game-state request)))))))
