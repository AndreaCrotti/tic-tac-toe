(ns tic-tac-toe.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [tic-tac-toe.core :as core]
            [tic-tac-toe.board :refer [format-board]]
            [clojure.edn :as edn]))

(def ^:const DEFAULT-CONFIG-FILE "resources/config.edn")

(def cli-options
  [["-c" "--config CONFIG-FILE" "Config file to load"
    :default DEFAULT-CONFIG-FILE]

   ["-p" "--profile PROFILE" "Profile to run given the config file"
    :default :random-random
    :parse-fn keyword]

   ["-h" "--help"]])

(defn get-config
  [config-file profile]
  (let [config (-> config-file slurp edn/read-string)]
    (profile config)))

(defn -main
  [& args]
  (let [parsed-args (parse-opts args cli-options)
        config-file (-> parsed-args :options :config)
        profile (-> parsed-args :options :profile)
        game-config (get-config config-file profile)
        result (core/play game-config)]

    (println "Winner: " (:winner result))
    (println (format-board (:board result)))))
