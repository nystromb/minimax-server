(ns minimax-server.tic-tac-toe)

(def win-lines '((0 1 2) (3 4 5) (6 7 8) (0 3 6) (1 4 7) (2 5 8) (1 4 8) (2 4 6)))

(defn check-win [mark board]
  (loop [index 0
         curr (first win-lines)]
    (if (= mark (and (nth board (first curr)) 
                     (nth board (second curr)) 
                     (nth board (last curr)))) 
                        true 
                        (recur (inc index) (nth win-lines index)))))

(defn is-draw? [board]
  (nil? (some #(= "_" %) board)))

(defn game-won? [mark board]
  (check-win mark board))

(defn game-over? [board]
  (or (is-draw? board) (game-won? "x" board)))
      

