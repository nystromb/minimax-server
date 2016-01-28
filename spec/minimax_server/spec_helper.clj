(ns minimax-server.spec-helper
  (:import
    [scarvill.httpserver.request RequestBuilder]))

(defn ttt-request [current-player board]
  "Constructs a new Request object with the given current player and board"
  (.build (doto (new RequestBuilder)
    (.setParameter "current_player" current-player)
    (.setParameter "board" board)
    (.setURI "/"))))

(defn get-body-as-str [response]
  (String. (.getBody response)))
