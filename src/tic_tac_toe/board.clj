(ns tic-tac-toe.board
  (:require [clojure.core.matrix :as matrix]
            [tic-tac-toe.const :as const]))

(defn cells
  [board]
  (apply concat board))

(defn board-size
  [board]
  (count (cells board)))

(defn- in-board?
  "Helper function to check if indices are valid for the given board"
  [n board]
  (and (< n (board-size board)) (>= n 0)))

(defn get-cell
  "Return the value in the given cell position"
  [board x y]
  {:pre [(in-board? x board)
         (in-board? y board)]}

  (get-in board [x y]))

(defn set-cell
  "Set the cell to the given value"
  [board x y value]
  {:pre [(in-board? x board)
         (in-board? y board)
         (= :empty (get-cell board x y))]}

  (update-in board [x y] (fn [_] value)))

(defn make-board
  "Create a new board with the given board size"
  ([board-size]
   (->
    (matrix/fill (matrix/zero-matrix board-size board-size) :empty)
    matrix/matrix))

  ([]
   (make-board const/DEFAULT-BOARD-SIZE)))

(defn full-board?
  "Check if the whole board was filled in"
  [board]
  (every? #(not= % :empty) (cells board)))

(defn is-empty-cell?
  "Check if the given cell is set"
  [board x y]
  ;; is there a way to avoid using this double nth?
  (= :empty (get-cell board x y)))

(defn empty-cells
  "Generate all the currently empty cells"
  [board]
  (let [board-size (count board)]
    ;; this nested loop might need to be improved
    (remove
     nil?
     (apply concat
            (for [x (range board-size)]
              (for [y (range board-size)]
                (if (is-empty-cell? board x y)
                  [x y])))))))

(defn all-rows
  "Generate a sequence of all the rows/columns and diagonals to consider"
  [board]
  (concat
   (matrix/rows board)
   (matrix/columns board)
   [(matrix/diagonal board)
    (matrix/diagonal (matrix/transpose board))]))

(defn format-row
  [row]
  (clojure.string/join " " (map #(:symbol (get const/SYMBOLS %)) row)))

(defn format-board
  "Return a simple string representation of the board"
  [board]
  (clojure.string/join
   "\n"
   (map format-row board)))
