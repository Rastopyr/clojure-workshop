(ns clojure-workshop.db
  (:require [clojure.edn]))

(defn read-db "reads from data.edn or returns empty list" []
  (or (clojure.edn/read-string (slurp "data.edn")) (list)))

(defn contains-val?
  [coll val]
  (when (seq coll)
    (or (= val (first coll))
        (recur (next coll) val))))

(defn new-items [db data]
  (filter #(not (contains-val? db %)) data))

(defn existed-items [db data]
  (filter #(contains-val? db %) data))

(defn write-db [data]
  (spit "data.edn" (into (read-db) data))
  data)

(defn write-uniq [data]
  (let [db (read-db)
        toInsert (new-items db data)]
    (write-db toInsert)
    data))
        ; toUpdate (existed-items db data)]))
