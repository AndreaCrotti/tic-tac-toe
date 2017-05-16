(ns tic-tac-toe.core
  (:require [clojure.core.matrix :as matrix]
            [tic-tac-toe.const :as const]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.move :as move]
            ;; TODO add a few specs
            #_[clojure.spec :as s]))

;; add a spec to define what should be a valid move

 
(defn fill-board-randomly
  "Keep filling up the board"
  ([board value iteration]
   (println "\nBoard at iteration" iteration ":")
   (print (board/format-board board))

   (let [winner-sym (move/winner board)]
     (if (nil? winner-sym)
       (if (board/full-board? board)
         (do
           (println "\nGame over and no winners")
           {:winner "Noone" :iterations iteration})

         (fill-board-randomly (move/next-move :random board value) (move/next-value value) (inc iteration)))
       (let [winner-name (:name (get const/SYMBOLS winner-sym))]
         (println "\nGame won by" winner-name)
         {:winner winner-name :iterations iteration}))))

  ([value]
   (fill-board-randomly (board/make-board) value 0))

  ([]
   (fill-board-randomly (board/make-board) const/P1 0)))

(defn play
  "Entry point to start a tic-tac-toe game"
  ([game-config player boards]
   )
  ([game-config]
  ;; play is where we keep track of the history
   (fill-board-randomly))
  ;; what could be returned is the full list of moves, so it's
  ;; possible to go back and forth?
  )
