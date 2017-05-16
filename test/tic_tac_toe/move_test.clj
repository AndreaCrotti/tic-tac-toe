(ns tic-tac-toe.move-test
  (:require [tic-tac-toe.move :as move]
            [tic-tac-toe.board :refer [make-board empty-cells get-cell set-cell]]
            [tic-tac-toe.const :refer [P1 P2]]
            [clojure.test :as t]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer [defspec]]))

(defspec random-el-picked-in-collection
  100
  (prop/for-all [coll (gen/vector gen/pos-int)]
                (let [fetched-el (move/random-el coll)]
                  (or (nil? fetched-el)
                      ;; transforming into a set it's an easy way to support vectors & co
                      (contains? (set coll) fetched-el)))))

(t/deftest random-move-test
  (t/testing "Moving randomly generates one less empty cell"
    (let [board (make-board)
          [x y] (move/next-move :random board P1)
          new-board (set-cell board x y P1)]

      (t/is (= 9 (count (empty-cells board))))
      (t/is (= 8 (count (empty-cells new-board)))))))

(t/deftest dont-lose-move-test
  (t/testing "Take your opponent winning move before he does"
    (let [board [[P1 P1 :empty]
                 [:empty :empty :empty]
                 [:empty :empty :empty]]
          next-move (move/next-move :dont-lose board P2)]
      ;; now P1 could move to [0, 2] to win, so P2 has
      ;; to do that first
      (t/is (= next-move [0 2])))))

(t/deftest winner-move-test
  (t/testing "Actually win when you can"
    (let [board [[P1 P1 :empty]
                 [:empty :empty :empty]
                 [P2 P2 :empty]]
          next-move (move/next-move :win board P1)]
      (t/is (= [0 2] next-move)))))

(t/deftest find-winner-move-test
  (t/testing "Empty board has no winner moves"
    (t/is (= [] (move/winner-moves (make-board) P1)))
    (t/is (= [] (move/winner-moves (make-board) P2))))

  (let [board [[P1 :empty] [:empty :empty]]]
    (t/testing "Winner move for P1 should be returned"
      (t/is (= '([0 1] [1 0] [1 1]) (move/winner-moves board P1))))

    (t/testing "P2 should not win in this case"
      (t/is (= '() (move/winner-moves board P2))))))

;; start some generative testing if possible
(t/deftest winner-row-test
  (t/testing "Winner row"
    (t/is (= (move/winner-sequence [P1 P1 P1]) P1))
    (t/is (= (move/winner-sequence [P1 :empty P1]) nil))))

(t/deftest winner-board-test
  (t/testing "Empty board has no winners"
    (t/is (nil? (move/winner (make-board)))))

  (t/testing "P1 winning"
    (t/is (= P1 (move/winner [[P1 P1] [:empty :empty]])))))
