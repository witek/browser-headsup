(ns browser-headsup.localstorage
  (:require
   [cljs.reader :as reader]))


(def ls (.-localStorage js/window))


(defn get-string
  "Return value of `key' from browser's localStorage."
  [key]
  (.getItem ls key))


(defn set-string!
  "Set `key' in browser's localStorage to `val`."
  [key val]
  (.setItem ls key val)
  val)


(defn set-edn!
  "Set `key' in browser's localStorage to `val`."
  [key val]
  (set-string! key (prn-str val))
  val)


(defn get-edn
  [key]
  "Returns value of `key' from browser's localStorage."
  (if-let [s (get-string key)]
    (reader/read-string s)
    nil))


(defn clear!
  "Remove all values from the browser's localStorage."
  []
  (.clear ls)
  nil)
