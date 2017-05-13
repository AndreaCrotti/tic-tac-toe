(ns tic-tac-toe.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [tic-tac-toe.core :as core]))

(def cli-options
  [["-s" "--size" "Board size to use"
    :default core/DEFAULT-BOARD-SIZE
    :validate [#(pos? ())]]])

(defn -main
  [& args]
  (let [parsed-args (parse-opts args cli-options)
        board-size (-> parsed-args :options :size)]
    (println "Welcome to the tic-tac-toe implementation, board size " board-size)

    (doseq [line (line-seq (java.io.BufferedReader. *in*))]
      (print line))))
