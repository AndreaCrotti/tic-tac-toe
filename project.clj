(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "Sample tic-tac-toe implementation"
  :url "https://github.com/AndreaCrotti/tic-tac-toe"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [environ "1.1.0"]
                 [net.mikera/core.matrix "0.59.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/core.match "0.2.2"]
                 [org.clojure/core.unify "0.5.7"]
                 [org.clojure/test.check "0.9.0"]
                 [org.hugoduncan/core.logic "0.8.11.1"]
                 [datascript "0.16.1"]]

  :main tic-tac-toe.cli)
