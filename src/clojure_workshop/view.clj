(ns clojure-workshop.view
  (:require [hiccup.core :as hiccup]
            [clojure.string :as str]))

(defn layout [nested]
  (hiccup/html [:div { :class "layout"} nested]))

(defn form [url]
  (hiccup/html
   [:div { :class "parse index-parse"}
      [:h1 "Parser form"]
      [:form {:action "parse"}
       [:button {:type "submit"} "Parse"]
       [:input {:type "text"
                :name "url"
                :value url}]]]))

(defn index []
  (layout [:div { :class "page index-page"}
           [:h1 "Hello world"]
           [:div [:b "Parser"]]]))

(defn parse-page [] (layout [:div (form nil)]))

(defn list-items
  [data]
  (hiccup/html
    (for [{title :title price :price} data]
      [:dl
       [:dt title]
       [:dd price]])))

(defn parsed-list-page
  [data url]
  (layout [:div
            (form url)
            [:h2 "Data parsed"]
            (list-items data)]))
