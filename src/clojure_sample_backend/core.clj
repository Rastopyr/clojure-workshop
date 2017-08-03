(ns clojure-sample-backend.core
  (:require [compojure.core :as compojure]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(compojure/defroutes app
  (compojure/GET "/" [] {:body "Hello World!"
                         :status 200
                         :headers {"Content-Type" "text/plain"}}))

(def wrapped-app (-> app
                     (wrap-defaults site-defaults)))
(defn -main []
  (jetty/run-jetty #'wrapped-app {:join? false :port 8080}))
