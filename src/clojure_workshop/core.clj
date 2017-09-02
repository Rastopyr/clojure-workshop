(ns clojure-workshop.core
  (:require [compojure.core :as compojure]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [clojure-workshop.handlers :as handlers]))

(compojure/defroutes app
  (compojure/GET "/" [] (handlers/index))
  (compojure/GET "/parse" [& params] (handlers/parse params))
  ; (compojure/GET "/db" [& params] (handlers/db params))
  (compojure/GET "/favicon.ico" [] {:body "facivon"
                                    :status 200
                                    :headers {"Content-Type" "text/plain"}}))

(def wrapped-app (-> app
                     (wrap-defaults (assoc-in site-defaults [:security :anti-forgery] false))))

(def server (atom nil))

(defn start []
  (reset! server (jetty/run-jetty #'wrapped-app {:join? false :port 8080})))

(defn stop []
  (.stop @server))

(defn restart []
  (when @server (stop))
  (start))

(defn -main []
  (jetty/run-jetty #'wrapped-app {:join? false :port 8080}))
