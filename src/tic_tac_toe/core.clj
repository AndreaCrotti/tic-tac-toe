(ns tic-tac-toe.core
  (:require [clojure.core.matrix :as matrix]))

(def ^:const DEFAULT-BOARD-SIZE 3)
(def ^:const EMPTY 0.0)
(def ^:const P1 1)
(def ^:const P2 -1)

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
