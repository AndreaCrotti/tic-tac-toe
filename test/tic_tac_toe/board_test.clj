(ns tic-tac-toe.board-test
  (:require [tic-tac-toe.board :as board]
            [tic-tac-toe.const :refer [P1]]
            [clojure.test :as t]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]))

(t/deftest board-creation-test
  (t/testing "A new board is empty"
    (let [empty-board (board/make-board)]
      (t/is (true? (board/is-empty-cell? empty-board 0 0)))
      (t/is (true? (board/is-empty-cell? empty-board 2 2))))))

(t/deftest empty-cells-test
  (t/testing "New board has all empty cells"
    (let [board (board/make-board)]
      (t/is (=
             (count (board/empty-cells board))
             (board/board-size board))))))

(t/deftest board-set-and-get-test
  ;; TODO: Add tests that actually test the pre condition set
  ;; which should throw assertion errors accordingly
  (t/testing "Get after set"
    (let [initial-board (board/make-board)
          first-move (board/set-cell initial-board 0 0 P1)]
      (t/is (= P1 (board/get-cell first-move 0 0))))))
