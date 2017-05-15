(ns tic-tac-toe.move-test
  (:require [tic-tac-toe.move :as move]
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

