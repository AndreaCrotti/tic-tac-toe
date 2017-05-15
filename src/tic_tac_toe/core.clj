(ns tic-tac-toe.core
  (:require [clojure.core.matrix :as matrix]
            [tic-tac-toe.const :as const]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.move :as move]
            ;; TODO add a few specs
            #_[clojure.spec :as s]))

;; add a spec to define what should be a valid move


(defn winner-sequence
  "Check if a particular sequence of cell values are winning
  and return the winning player or nil"
  [values]
  (let [unique (set values)
        first-sym (nth values 0)]
    (when
        ;; check that all the values are the same
        ;; and they correspond to a filled in cell
        (and
         (= 1 (count unique))
         (contains? const/PLAYED-VALUES first-sym))
      first-sym)))

(defn all-rows
  "Generate a sequence of all the rows/columns and diagonals to consider"
  [board]
  (concat
   (matrix/rows board)
   (matrix/columns board)
   [(matrix/diagonal board)
    (matrix/diagonal (matrix/transpose board))]))


(defn winner
  "Return the winner or nil if no rows/columns/diagonals are winning"
  [board]
  (first
   (filter (complement nil?)
           (map winner-sequence (all-rows board)))))

(defn- next-value
  [value]
  (* value -1))

(defn winner-moves
  "Return all the possible winning moves for a specific player"
  [board player]
  ;; loop over all the possible empty cells and check
  ;; of all the produced boards actually produce a winning board
  (filter
   (complement nil?)
   (for [[x y] (board/empty-cells board)]
     (let [next-board (board/set-cell board x y player)]
       (if (= (winner next-board) player)
         [x y])))))

(defn fill-board-randomly
  "Keep filling up the board"
  ([board value iteration]
   (println "\nBoard at iteration" iteration ":")
   (print (board/format-board board))

   (let [winner-sym (winner board)]
     (if (nil? winner-sym)
       (if (board/full-board? board)
         (do
           (println "\nGame over and no winners")
           {:winner "Noone" :iterations iteration})

         (fill-board-randomly (move/set-random-cell board value) (next-value value) (inc iteration)))
       (do
         (let [winner-name (:name (get const/SYMBOLS winner-sym))]
           (println "\nGame won by" winner-name)
           {:winner winner-name :iterations iteration})))))

  ([value]
   (fill-board-randomly (board/make-board) value 0))

  ([]
   (fill-board-randomly (board/make-board) const/P1 0)))

(defn play
  "Entry point to start a tic-tac-toe game"
  [game-config]
  (fill-board-randomly)
  )
