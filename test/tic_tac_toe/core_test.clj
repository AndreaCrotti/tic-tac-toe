(ns tic-tac-toe.core-test
  (:require [clojure.test :as t]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.const :refer [P1 P2 EMPTY]]
            [tic-tac-toe.board :refer [make-board board-size]]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]))

(t/deftest random-games-test
  (t/testing "Should only be max n*n iterations"
    (let [initial-board (make-board)
          game-output (core/fill-board-randomly)]
      (t/is (<=
             (:iterations game-output)
             (board-size initial-board))))))
