(ns tic-tac-toe.move
  (:require [tic-tac-toe.board :as board]))

(defn random-el
  "Given a collection, return a random element"
  [coll]
  (when (> (count coll) 0)
    (let [rand-idx (Math/round (* (Math/random) (dec (count coll))))]
      (nth coll rand-idx))))

(defn next-random-move
  "Given the coordinates of a random empty cell"
  [board]
  (random-el
   (board/empty-cells board)))

(defn set-random-cell
  [board val]
  (let [[x y] (next-random-move board)]
    (board/set-cell board x y val)))

(defmulti next-move
  "Next move to play, dispatching on the algorithm"
  :algorithm)

(defmethod next-move :random
  [board player])

(defmethod next-move :dont-lose
  [board player])

(defmethod next-move :win
  [board player])
