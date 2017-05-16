(ns tic-tac-toe.core-test
  (:require [clojure.test :as t]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.board :refer [make-board board-size full-board?]]
            [tic-tac-toe.move :refer [winner]]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]))

(def SAMPLE-GAME-CONFIG
  {:size 3
   :initial-player :p1
   :players
   {:p1
    {:algorithm :random}

    :p2
    {:algorithm :random}}})

(t/deftest random-games-test
  (t/testing "Should only be max n*n iterations"
    (let [initial-board (make-board)
          game-output (core/play SAMPLE-GAME-CONFIG)
          final-board (:board game-output)]

      (t/is (or
             (full-board? final-board)
             (not (nil? (winner final-board))))))))
