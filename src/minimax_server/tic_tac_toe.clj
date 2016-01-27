(ns minimax-server.tic-tac-toe)

(def any? (comp boolean some))

(defn winning-line? [line-indexes mark board]
  (every? #(= mark %) (map #(nth board %) line-indexes)))

(defn player-won? [player-mark board]
  (let [board-line-indexes [[0 1 2] [3 4 5] [6 7 8] [0 3 6] [1 4 7] [2 5 8] [0 4 8] [2 4 6]]]
    (any? #(winning-line? % player-mark board) board-line-indexes)))

(defn is-full? [board]
  (not-any? #(= :_ %) board))

(defn is-draw? [board]
  (and
    (is-full? board)
    (not (player-won? :x board))
    (not (player-won? :o board))))

(defn game-over? [board]
  (or (is-draw? board) (player-won? :x board) (player-won? :o board)))

(defn mark-board [board mark space]
  (if (= (nth board space) :_)
      (assoc board space mark)))

(defn available-spaces [board]
  (keys (filter
    #(= (second %) :_)
    (zipmap (range (count board)) board))))
