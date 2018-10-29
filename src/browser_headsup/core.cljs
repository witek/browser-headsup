(ns browser-headsup.core
  (:require
   [reagent.core :as r]
   [browser-headsup.localstorage :as localstorage]))


(def localstorage-key "witek.browser-headsup.db")


(defn store-db!
  [db]
  (localstorage/set-edn!
   localstorage-key
   {:minimized? (:minimized? db)
    :selected-tab (:selected-tab db)})
  db)


(defn on-toggle
  [db]
  (-> db
      (update :minimized? not)
      (store-db!)))


(defn on-tab
  [db tab-id]
  (-> db
      (assoc :selected-tab tab-id)
      (store-db!)))


(defn load-db
  []
  (localstorage/get-edn localstorage-key))


(defonce !db (r/atom (merge
                      {:title "developer heads-up"
                       :on-toggle #(swap! !db on-toggle)
                       :on-tab #(swap! !db on-tab %)
                       :tabs-order '()
                       :tabs {}
                       :size "50%"}
                      (load-db))))


(defn with-db
  [f]
  (swap! !db f))


(defn conj-if-missing
  [collection value]
  (if (some #{value} collection)
    collection
    (conj collection value)))


(defn def-tab
  [{:as tab :keys [id]}]
  (-> @!db
      (update :tabs-order conj-if-missing id)
      (assoc-in [:tabs id] tab)
      (assoc :selected-tab id)
      (->> (reset! !db))))


