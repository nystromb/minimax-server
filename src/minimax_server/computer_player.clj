(ns minimax-server.computer-player)

(defn zip-indexes [board]
  (map vector (range (count board)) board))

(defn stupid-move [current-player board]
  (first (first (filter (fn [[index mark]] (= "_" mark)) (zip-indexes board)))))
