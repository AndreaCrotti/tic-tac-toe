(ns tic-tac-toe.const)

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

(def ^:const BOARD-CONFIG
  {:p1 {:symbol \x}
   :p2 {:symbol \o}
   :empty {:symbol \_}})
