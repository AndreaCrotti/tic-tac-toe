(ns tic-tac-toe.core
  (:require [clojure.core.matrix :as matrix]))

(def ^:const DEFAULT-BOARD-SIZE 3)
(def ^:const EMPTY 0.0)
(def ^:const P1 1)
(def ^:const P2 -1)
(def ^:const SYMS [P1 P2])

;; add a spec to define what should be a valid move

(defn make-board
  "Create a new board with the given board size"
  ([board-size]
   (matrix/zero-matrix board-size board-size))
  ([]
   (make-board DEFAULT-BOARD-SIZE)))

(defn get-cell
  [board x y]
  (get-in board [x y]))

(defn is-empty-cell?
  "Check if the given cell is set"
  [board x y]
  ;; is there a way to avoid using this double nth?
  (= EMPTY (get-cell board x y)))

(defn set-cell
  [board x y player]
  (update-in board [x y] (fn [_] player)))

(defn winner?
  "Check if a particular sequence of cell values are winning
  and return the winning player or nil"
  [values]
  (let [unique (set values)
        first-sym (nth values 0)]
    (when
        (and
         (= 1 (count unique))
         (contains? SYMS (nth values 0)))
      first-sym)))

(defn empty-cells
  "Generate all the currently empty cells"
  [board]
  (let [board-size (count board)]
    (for [x (range board-size)]
      (for [y (range board-size)]
        (if (is-empty-cell? board x y)
          [x y])))))
