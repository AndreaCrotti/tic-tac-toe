(ns tic-tac-toe.cli
  (:require [clojure.tools.cli :refer [parse-opts]]
            [tic-tac-toe.core :as core]
            [clojure.edn :as edn]))

(def ^:const DEFAULT-CONFIG-FILE "resources/config.edn")

(def cli-options
  [["-c" "--config" "Config file to load"
    :default DEFAULT-CONFIG-FILE]

   ["-p" "--profile" "Profile to run given the config file"
    :default "computer-computer-random"]])

(defn -main
  [& args]
  (let [parsed-args (parse-opts args cli-options)
        config-file (-> parsed-args :options :config)
        profile (-> parsed-args :options :profile)
        config (-> config-file slurp edn/read-string)
        game-config (get config (keyword profile))]
    
    (core/play game-config)))

