(ns tic-tac-toe.core-test
  (:require [clojure.test :as t]
            [tic-tac-toe.core :as core]))

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
