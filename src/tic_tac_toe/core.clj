(ns tic-tac-toe.core
  (:require [clojure.core.matrix :as matrix]
            ;; TODO add a few specs
            #_[clojure.spec :as s]))

(def ^:const DEFAULT-BOARD-SIZE 3)
(def ^:const EMPTY 0.0)
(def ^:const P1 1)
(def ^:const P2 -1)
(def ^:const PLAYED-VALUES [P1 P2])
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

(defn winner?
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
         (contains? PLAYED-VALUES (nth values 0)))
      first-sym)))

(defn empty-cells
  "Generate all the currently empty cells"
  [board]
  (let [board-size (count board)]
    (for [x (range board-size)]
      (for [y (range board-size)]
        (if (is-empty-cell? board x y)
          [x y])))))

(defn format-row
  [row]
  (clojure.string/join " " (map #(:symbol (get SYMBOLS %)) row)))

(defn format-board
  "Return a simple string representation of the board"
  [board]
  (clojure.string/join
   "\n"
   (map format-row board)))
