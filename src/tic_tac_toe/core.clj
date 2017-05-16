(ns tic-tac-toe.core
  (:require [clojure.core.matrix :as matrix]
            [tic-tac-toe.const :as const]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.move :as move]
            [clojure.spec.alpha :as s]))

;; add spec at least for the configuration file, since that's user
;; defined and needs to be vaildated and cleaned up anyway first
;; (s/def ::algorithm (s/or :random keyword? :dont-lose keyword?))

(def P-TO-VAL
  {:p1 const/P1
   :p2 const/P2})

(defn other-player
  [player]
  (if (= player :p1) :p2 :p1))

(defn play
  "Entry point to start a tic-tac-toe game"
  ([game-config board player]
   (let [found-winner (move/winner board)
         player-val (get P-TO-VAL player)
         other (other-player player)]

     (if (nil? found-winner)
       (if (board/full-board? board)
         (do
           {:winner nil :board board})

         (let [algorithm (get-in game-config [:players player :algorithm])
               [next-x next-y] (move/next-move algorithm board player-val)
               new-board (board/set-cell board next-x next-y player-val)]
           (play game-config new-board other)))

       (do
         {:winner other :board board}))))

  ([game-config]
   ;; play is where we keep track of the history
   (prn game-config)
   (let [initial-board (board/make-board (:size game-config))
         initial-player (:initial-player game-config)]

     (play game-config initial-board initial-player)))
  ;; what could be returned is the full list of moves, so it's
  ;; possible to go back and forth?
  )
