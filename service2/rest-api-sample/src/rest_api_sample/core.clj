(ns rest-api-sample.core
  (:require [compojure.core :as core]
            [ring.middleware.defaults :refer :all]
            [ring.adapter.jetty :refer [run-jetty]]
            [rest-api-sample.handlers.data-handler :as data-handler])
  (:gen-class))

(core/defroutes app-routes
  (core/GET "/status" [] 
    (let [state (data-handler/get-status)]
    {:status 200 :body (pr-str state)})))

(defn -main [& args]
  (println "Starting service2 on port 3002...")
  (run-jetty app-routes {:port 3002 :host "0.0.0.0" :join? false}))
