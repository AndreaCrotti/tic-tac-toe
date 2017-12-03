(defproject tic-tac-toe "0.1.0-SNAPSHOT"
  :description "Sample tic-tac-toe implementation"
  :url "https://github.com/AndreaCrotti/tic-tac-toe"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-RC2"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [environ "1.1.0"]
                 [org.clojure/spec.alpha "0.1.143"]
                 [org.clojure/core.specs.alpha "0.1.24"]
                 [net.mikera/core.matrix "0.61.0"]
                 [net.mikera/core.matrix.stats "0.7.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.clojure/core.match "0.2.2"]
                 [org.clojure/core.unify "0.5.7"]
                 [org.hugoduncan/core.logic "0.8.11.1"]
                 [org.clojure/test.check "0.9.0"]
                 [datascript "0.16.3"]
                 
                 [hiccup "1.0.5"]
                 [ns-tracker "0.3.1"]
                 [ring "1.6.3"]
                 [ring/ring-defaults "0.3.1"]
                 [compojure "1.6.0"]

                 [clj-http "3.7.0"]
                 [garden "1.3.3"]

                 [cljs-http "0.1.43"]

                 [day8.re-frame/test "0.1.5"]
                 [com.rpl/specter "1.0.3"]

                 [org.clojure/clojurescript "1.9.854"]
                 [re-frisk "0.5.0"]
                 [re-frame "0.10.1"]]

  :plugins [[environ/environ.lein "0.3.1"]
            [lein-ring "0.9.7"]
            [lein-cljsbuild "1.1.4"]
            [lein-cljfmt "0.5.7"]
            [lein-garden "0.2.8"]]

  :uberjar-name "tic-tac-toe.jar"

  :min-lein-version "2.7.1"
  :source-paths ["src/clj" "src/cljc"]
  :test-paths ["test/clj" "test/cljc"]

  :resource-paths ["config" "resources"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "target"
                                    "test/js"
                                    "resources/public/css"
                                    "out"]

  :figwheel {:css-dirs ["resources/public/css"]
             :open-file-command "lein_opener.sh"
             :ring-handler tic-tac-toe.api/app
             :server-logfile "log/figwheel.log"}

  :ring {:handler tic-tac-toe.api/app}
  :main ^{:skip-aot true} tic-tac-toe.cli
  :target-path "target/%s"

  :garden {:builds [{:id           "screen"
                     :source-paths ["src/clj"]
                     :stylesheet tic-tact-toe.css/screen
                     :compiler     {:output-to     "resources/public/css/screen.css"
                                    :pretty-print? true}}]}

  :profiles
  {:production {:env {:production true}}
   :uberjar {:hooks []
             :source-paths ["src/clj" "src/cljc"]
             :prep-tasks [["compile"]
                          ["garden" "once"]
                          ["cljsbuild" "once" "min"]]

             :omit-source true
             :aot :all
             :main tic-tac-toe.api}
   :dev
   {:plugins [[lein-figwheel "0.5.14"]
              [lein-doo "0.1.7"]
              [migratus-lein "0.5.0"]]

    :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
    :dependencies [[binaryage/devtools "0.9.4"]
                   [com.cemerick/piggieback "0.2.2"]
                   [figwheel "0.5.14"]
                   [figwheel-sidecar "0.5.14"]
                   [javax.servlet/servlet-api "2.5"]
                   [lambdaisland/garden-watcher "0.3.2"]
                   ;; dependencies for the reloaded workflow
                   [reloaded.repl "0.2.3"]
                   [ring/ring-mock "0.3.1"]]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs" "src/cljc"]
     :figwheel     {:on-jsload "tic-tact-toe.core/mount-root"}
     :compiler     {:main                 tic-tact-toe.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :optimizations :none
                    :source-map true
                    :source-map-timestamp true
                    :preloads             [devtools.preload]
                    :external-config      {:devtools/config {:features-to-install :all}}}}

    {:id           "min"
     :source-paths ["src/cljs" "src/cljc"]
     :compiler     {:main            tic-tact-toe.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :output-dir "resources/public/js/compiled"
                    :source-map "resources/public/js/compiled/app.js.map"
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]})

