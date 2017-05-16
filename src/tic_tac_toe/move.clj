(ns tic-tac-toe.move
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.const :refer [PLAYED-VALUES]]))

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

(defn winner
  "Return the winner or nil if no rows/columns/diagonals are winning"
  [board]
  (first
   (remove nil?
           (map winner-sequence (board/all-rows board)))))

(defn next-value
  [value]
  (* value -1))

(defn winner-moves
  "Return all the possible winning moves for a specific player"
  [board player]
  ;; loop over all the possible empty cells and check
  ;; of all the produced boards actually produce a winning board
  (remove nil?
   (for [[x y] (board/empty-cells board)]
     (let [next-board (board/set-cell board x y player)]
       (if (= (winner next-board) player)
         [x y])))))


(defn random-el
  "Given a countable and indexed collection, pick a random element from it"
  [coll]
  (when (pos? (count coll))
    (let [rand-idx (Math/round (* (Math/random) (dec (count coll))))]
      (nth coll rand-idx))))

(defn next-random-move
  "Generate the next random move picking one of the empty cell coordinates pair"
  [board]
  (random-el
   (board/empty-cells board)))

(defmulti next-move
  "Dispatch on the algorithm used"
  (fn [algorithm board player] algorithm))

(defmethod next-move :random
  [_ board player]
  (next-random-move board))

(defmethod next-move :dont-lose
  [_ board player]
  ;; check if there are winning positions for the other player
  ;; falling back to a random choice if there are no blocking moves
  (let [opponent (next-value player)
        ;; instead of first it could be random-el, to make the
        ;; games a bit less deterministic
        other-winner (first (winner-moves board opponent))]

    (if (nil? other-winner)
      (next-move :random board player)
      other-winner)))

(defmethod next-move :win
  [_ board player]
  ;; try to actively win first and fall back to not losing
  ;; if there are no winning moves
  (let [mywinner (first (winner-moves board player))]
    (if (nil? mywinner)
      (next-move :dont-lose board player)
      mywinner)))

(defmethod next-move :ai
  [_ board player]
  ;; actually do some smart analysis of the whole tree
  ;; to see what really be the next move, this method
  ;; would not need to fallback on anything else since
  ;; there should be always a best computed move
  )
