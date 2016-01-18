 (ns minimax-server.core
  (:import [scarvill.httpserver.server Server])
  (:require [minimax-server.computer-player :as computer]
            [minimax-server.server-configuration :refer [minimax-server-config]]))

(def ttt-server
  (new Server (minimax-server-config 5000 computer/stupid-move)))

(defn main []
  (.start ttt-server))
