(ns tic-tac-toe.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [tic-tac-toe.core :as core]))

(defmulti tic-tac-run
  "Dispatch on algorithm and modality"
  :algorithm)

;; TODO: this should be moved in core and tested accordingly
(defmethod tic-tac-run :random
  [this]
  (core/fill-board-randomly))

(defmethod tic-tac-run :semi
  [this]
  (println "Run semi mode"))

(def cli-options
  [["-m" "--mode" "Different modality to use"
    :choices ["auto" "human"]
    :default "auto"]

   ["-a" "--algorithm" "Algorithm to use"
    :choices ["random" "semi" "full"]
    :default "random"]

   ["-s" "--size" "Board size to use"
    :default core/DEFAULT-BOARD-SIZE
    :validate [#(pos? (read-string %))]]])

(defn -main
  [& args]
  (let [parsed-args (parse-opts args cli-options)
        board-size (-> parsed-args :options :size)
        algorithm (-> parsed-args :options :algorithm keyword)]
    
    (println "Welcome to the tic-tac-toe implementation, board size " board-size)
    (tic-tac-run {:algorithm algorithm})))
