(ns tic-tac-toe.move
  (:require [tic-tac-toe.board :as board]))

(defn random-el
  "Given a countable and indexed collection, pick a random element from it"
  [coll]
  (when (> (count coll) 0)
    (let [rand-idx (Math/round (* (Math/random) (dec (count coll))))]
      (nth coll rand-idx))))

(defn next-random-move
  "Generate the next random move picking one of the empty cell coordinates pair"
  [board]
  (random-el
   (board/empty-cells board)))

(defmulti next-move (fn [algorithm board player] algorithm))

(defmethod next-move :random
  [_ board player]
  (let [[x y] (next-random-move board)]
    (board/set-cell board x y player)))

(defmethod next-move :dont-lose
  [_ board player])

(defmethod next-move :win
  [_ board player])
