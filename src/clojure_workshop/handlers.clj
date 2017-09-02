(ns clojure-workshop.handlers
  (:require [clojure.string :as str]
            [clojure-workshop.db :as db]
            [clojure-workshop.view :as view]
            [net.cgrand.enlive-html :as enlive]))

(defn get-by-url [url]
  (enlive/html-resource (java.net.URL. url)))

(defn parse-cards [resp]
  (enlive/select resp [:div.catalogCard-info]))

(defn price->int [price-str]
  (Integer/parseInt
   (str/join
    (re-seq #"\d+" price-str))))

(defn code->id [code]
  (str/trim (last (str/split code (re-pattern ":")))))

(defn select-title [item]
  (-> (enlive/select item [:div.catalogCard-title :a])
      first
      :content
      first))

(defn select-price [item]
  (-> (enlive/select item [:div.catalogCard-price])
      first
      :content
      first))

(defn select-code [item]
  (-> (enlive/select item [:div.catalogCard-code])
      first
      :content
      first))

(defn map-info [data]
  (map (fn [item]
         {:id (code->id (select-code item))
          :title (select-title item)
          :price (price->int (select-price item))}) data))

(defn data-by-url [url]
  (->> url
      get-by-url
      parse-cards
      map-info))

(defn parse-page
  [url]
  (if url
    (-> (data-by-url url)
         db/write-uniq
         (view/parsed-list-page url))
    (view/parse-page)))

(defn index []
  {:status 200
   :headers { "Content-Type" "text/html"}
   :body (view/index)})

(defn parse [params]
  {:status 200
   :headers { "Content-Type" "text/html"}
   :body (parse-page (:url params))})

; (defn db [params]
;   {:status 200
;     :headers { "Content-Type" "text/html"}
;     :body (let [{search :search} params]
;             (if search
;               (view/search-form (db/read-db (:search params)))
;               (view/parse-page)))})
