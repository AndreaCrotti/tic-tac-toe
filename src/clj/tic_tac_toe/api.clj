(ns tic-tac-toe.api
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :as r-def]
            [ring.util.response :as resp]
            [environ.core :refer [env]]
            [compojure.core :refer [defroutes GET POST]]))

(def default-port 3000)

(defn- get-port
  []
  (Integer. (or (env :port) default-port)))

(defroutes app-routes
  (GET "/" [] {:status 200 :body "hello tic tac toe"}))

(def app
  (-> app-routes
      #_(resources/wrap-resource "public")
      #_(r-def/wrap-defaults)))

(defn -main [& args]
  (jetty/run-jetty app {:port (get-port)}))
