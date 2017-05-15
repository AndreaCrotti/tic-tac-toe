(ns tic-tac-toe.core-test
  (:require [clojure.test :as t]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.const :refer [P1 P2 EMPTY]]
            [tic-tac-toe.board :refer [make-board board-size]]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]))

;; start some generative testing if possible
(t/deftest winner-row-test
  (t/testing "Winner row"
    (t/is (= (core/winner-sequence [P1 P1 P1]) P1))
    (t/is (= (core/winner-sequence [P1 EMPTY P1]) nil))))

(t/deftest winner-board-test
  (t/testing "Empty board has no winners"
    (t/is (nil? (core/winner (make-board)))))

  (t/testing "P1 winning"
    (t/is (= P1 (core/winner [[P1 P1] [EMPTY EMPTY]])))))

(t/deftest random-games-test
  (t/testing "Should only be max n*n iterations"
    (let [initial-board (make-board)
          game-output (core/fill-board-randomly)]
      (t/is (<=
             (:iterations game-output)
             (board-size initial-board))))))

(t/deftest find-winner-move-test
  (t/testing "Empty board has no winner moves"
    (t/is (= [] (core/winner-moves (make-board) P1)))
    (t/is (= [] (core/winner-moves (make-board) P2))))

  (let [board [[P1 EMPTY] [EMPTY EMPTY]]]
    (t/testing "Winner move for P1 should be returned"
      (t/is (= '([0 1] [1 0] [1 1]) (core/winner-moves board P1))))

    (t/testing "P2 should not win in this case"
      (t/is (= '() (core/winner-moves board P2))))))
