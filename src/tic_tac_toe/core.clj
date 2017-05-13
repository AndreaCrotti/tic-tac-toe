(ns tic-tac-toe.core
  (:require [clojure.core.matrix :as matrix]
            ;; TODO add a few specs
            #_[clojure.spec :as s]))

(def ^:const DEFAULT-BOARD-SIZE 3)
(def ^:const EMPTY 0.0)
(def ^:const P1 1)
(def ^:const P2 -1)
(def ^:const PLAYED-VALUES #{P1 P2})
;; TODO: the player name should be able to be updated
;; somehow interactively
(def ^:const SYMBOLS
  {P1 {:symbol \x
       :name "p1"}

   P2 {:symbol \o
       :name "p2"}

   EMPTY {:symbol \_
          :name "empty"}})


;; add a spec to define what should be a valid move

(defn make-board
  "Create a new board with the given board size"
  ([board-size]
   (matrix/zero-matrix board-size board-size))

  ([]
   (make-board DEFAULT-BOARD-SIZE)))

(defn cells
  [board]
  (apply concat board))

(defn board-size
  [board]
  (count (cells board)))

(defn get-cell
  "Return the value in the given cell position"
  [board x y]
  (get-in board [x y]))

(defn is-empty-cell?
  "Check if the given cell is set"
  [board x y]
  ;; is there a way to avoid using this double nth?
  (= EMPTY (get-cell board x y)))

(defn set-cell
  "Set the cell to the given value"
  [board x y value]
  (update-in board [x y] (fn [_] value)))

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
         (contains? PLAYED-VALUES first-sym))
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

(defn full-board?
  "Check if the whole board was filled in"
  [board]
  (every? #(not= % EMPTY) (cells board)))

(defn empty-cells
  "Generate all the currently empty cells"
  [board]
  (let [board-size (count board)]
    ;; this nested loop might need to be improved
    (filter (complement nil?)
            (apply concat
                   (for [x (range board-size)]
                     (for [y (range board-size)]
                       (if (is-empty-cell? board x y)
                         [x y])))))))


(defn format-row
  [row]
  (clojure.string/join " " (map #(:symbol (get SYMBOLS %)) row)))

(defn format-board
  "Return a simple string representation of the board"
  [board]
  (clojure.string/join
   "\n"
   (map format-row board)))

(defn random-el
  "Given a collection, return a random element"
  [coll]
  (let [rand-idx (Math/round (* (Math/random) (dec (count coll))))]
    (nth coll rand-idx)))

(defn next-random-move
  "Given the coordinates of a random empty cell"
  [board]
  (random-el
   (empty-cells board)))

(defn set-random-cell
  [board val]
  (let [[x y] (next-random-move board)]
    (set-cell board x y val)))

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
   (for [[x y] (empty-cells board)]
     (let [next-board (set-cell board x y player)]
       (if (= (winner next-board) player)
         [x y])))))

(defn fill-board-randomly
  "Keep filling up the board"
  ([board value iteration]
   (println "\nBoard at iteration" iteration ":")
   (print (format-board board))

   (let [winner-sym (winner board)]
     (if (nil? winner-sym)
       (if (full-board? board)
         (do
           (println "\nGame over and no winners")
           {:winner "Noone" :iterations iteration})

         (fill-board-randomly (set-random-cell board value) (next-value value) (inc iteration)))
       (do
         (let [winner-name (:name (get SYMBOLS winner-sym))]
           (println "\nGame won by" winner-name)
           {:winner winner-name :iterations iteration})))))

  ([value]
   (fill-board-randomly (make-board) value 0))

  ([]
   (fill-board-randomly (make-board) P1 0)))
