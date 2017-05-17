(ns tic-tac-toe.core
  (:require [clojure.core.matrix :as matrix]
            [tic-tac-toe.board :as board]
            [tic-tac-toe.move :as move]
            [clojure.spec.alpha :as s]))

;; add spec at least for the configuration file, since that's user
;; defined and needs to be vaildated and cleaned up anyway first
;; (s/def ::algorithm (s/or :random keyword? :dont-lose keyword?))

(defn play
  "Entry point to start a tic-tac-toe game"
  ([game-config board player boards]
   (let [found-winner (move/winner board)
         other (move/other-player player)]

     (if (nil? found-winner)
       (if (board/full-board? board)
         {:winner nil :board board :boards boards}

         (let [algorithm (get-in game-config [:players player :algorithm])
               next-coord (move/next-move algorithm board player)
               new-board (board/set-cell board next-coord player)]

           (play game-config new-board other (conj boards board))))

       {:winner other :board board :boards boards})))

  ([game-config]
   ;; play is where we keep track of the history
   (let [initial-board (board/make-board (:size game-config))
         initial-player (:initial-player game-config)]

     (play game-config initial-board initial-player [initial-board])))
  ;; what could be returned is the full list of moves, so it's
  ;; possible to go back and forth?
  )
