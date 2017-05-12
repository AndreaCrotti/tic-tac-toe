(ns tic-tac-toe.cli
  (:require #_[clojure.tools.cli :refer [parse-opts]]
            [tic-tac-toe.core :as core]))

(def cli-options
  [["-s" "--size" "Board size to use"
    :deafult core/DEFAULT-BOARD-SIZE
    :validate [#(pos? ())]]])

(defn -main
  [& args]
  (let [options (parse-opts args cli-options)]
    (println "Welcome to tic-tac-toe")))
