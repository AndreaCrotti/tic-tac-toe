(ns tic-tac-toe.core-test
  (:require [clojure.test :as t]
            [tic-tac-toe.core :as core]
            #_[clojure.test.check.properties :as prop]
            #_[clojure.test.check.clojure-test :refer [defspec]]))

(t/deftest board-test
  (t/testing "A new board is empty"
    (let [empty-board (core/make-board)]
      (t/is (true? (core/is-empty-cell? empty-board 0 0)))
      (t/is (true? (core/is-empty-cell? empty-board 2 2))))))

(t/deftest board-set-and-get-test
  (t/testing "Get after set"
    (let [initial-board (core/make-board)
          first-move (core/set-cell initial-board 0 0 core/P1)]
      (t/is (= core/P1 (core/get-cell first-move 0 0))))))

;; start some generative testing if possible
(t/deftest winner-row-test
  (t/testing "Winner row"
    (t/is (= (core/winner-sequence [core/P1 core/P1 core/P1]) core/P1))
    (t/is (= (core/winner-sequence [core/P1 core/EMPTY core/P1]) nil))))

(t/deftest winner-board-test
  (t/testing "Empty board has no winners"
    (t/is (nil? (core/winner (core/make-board)))))

  (t/testing "P1 winning"
    (t/is (= core/P1 (core/winner [[core/P1 core/P1] [core/EMPTY core/EMPTY]])))))

(t/deftest random-games-test
  (t/testing "Should only be max n*n iterations"
    (let [initial-board (core/make-board)
          game-output (core/fill-board-randomly)]
      (t/is (<=
             (:iterations game-output)
             (core/board-size initial-board))))))

(t/deftest empty-cells-test
  (t/testing "New board has all empty cells"
    (let [board (core/make-board)]
      (t/is (=
             (count (core/empty-cells board))
             (core/board-size board))))))
