(ns user
  (:require [clojure-sample-backend.core :as core]
            [ring.adapter.jetty :as jetty]))

(def server (atom nil))

(defn start []
  (reset! server (jetty/run-jetty #'core/wrapped-app {:join? false :port 8080})))

(defn stop []
  (.stop @server))

(defn restart []
  (when @server (stop))
  (start))
