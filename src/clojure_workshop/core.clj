(ns clojure-workshop.core
  (:require [compojure.core :as compojure]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(compojure/defroutes app
  (compojure/GET "/" [] {:body "Hello World!"
                         :status 200
                         :headers {"Content-Type" "text/plain"}}))

(def wrapped-app (-> app
                     (wrap-defaults site-defaults)))

(def server (atom nil))

(defn start []
  (reset! server (jetty/run-jetty #'core/wrapped-app {:join? false :port 8080})))

(defn stop []
  (.stop @server))

(defn restart []
  (when @server (stop))
  (start))

(defn -main []
  (jetty/run-jetty #'wrapped-app {:join? false :port 8080}))
