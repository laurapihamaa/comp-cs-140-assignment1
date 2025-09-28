(ns rest-api-sample.handlers.data-handler
  (:require [clj-http.client :as http]))

(def get-uptime
  (let [start-time (System/currentTimeMillis)]
    (fn []
      (let [current-time (System/currentTimeMillis)]
        (/ (- current-time start-time) 1000)))))

(def get-free-disk-inmb
  (let [runtime (Runtime/getRuntime)]
    (fn []
      (let [free-bytes (.freeMemory runtime)]
        (/ free-bytes (* 1024 1024))))))

(def store-to-storage-service
  (fn [state]
    (let [response (http/post "http://storage:8080/log"
                              {:body (pr-str state)
                               :headers {"Content-Type" "plain/text"}})]
      (= 200 (:status response)))))

(def store-to-volume-mount
  (fn [state]
    (let [file (clojure.java.io/writer "/vstorage/state.log" :append true)]
      (.write file (str state "\n"))
      (.close file))))

(defn get-status "get-all-data"
  []
  (let [uptime (get-uptime)
        free-disk (get-free-disk-inmb)
        message (str "Timestamp2: uptime " uptime
                     " hours, free disk in root: " free-disk " MB")
        state {:message message}]
    (store-to-storage-service state)
    (store-to-volume-mount state)
    state))

