(ns tic-tac-toe.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [tic-tac-toe.core :as core]
            [clojure.edn :as edn]))

(def ^:const DEFAULT-CONFIG-FILE "resources/config.edn")

(def cli-options
  [["-c" "--config CONFIG-FILE" "Config file to load"
    :default DEFAULT-CONFIG-FILE]

   ["-p" "--profile PROFILE" "Profile to run given the config file"
    :default :computer-computer-random
    :parse-fn keyword]

   ["-h" "--help"]])

(defn -main
  [& args]
  (let [parsed-args (parse-opts args cli-options)
        config-file (-> parsed-args :options :config)
        profile (-> parsed-args :options :profile)
        config (-> config-file slurp edn/read-string)
        game-config (get config profile)]

    (prn parsed-args)
    (core/play game-config)))

