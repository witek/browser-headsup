(ns browser-headsup.core
  (:require
   [reagent.core :as r]))


(defn on-toggle
  [db]
  (update db :minimized? not))


(defn on-tab
  [db tab-id]
  (assoc db :selected-tab tab-id))


(defonce !db (r/atom {:title "developer heads-up"
                      :minimized? true
                      :on-toggle #(swap! !db on-toggle)
                      :on-tab #(swap! !db on-tab %)
                      :tabs-order '()
                      :tabs {}}))


(defn conj-if-missing
  [collection value]
  (if (some #{value} collection)
    collection
    (conj collection value)))


(defn def-tab
  [id title component]
  (-> @!db
      (update :tabs-order conj-if-missing id)
      (assoc-in [:tabs id] {:id id
                            :title title
                            :component component})
      (assoc :selected-tab id)
      (->> (reset! !db))))


(def-tab "headsup-options" "Options" [:div "TODO developer heads-up options"])
(def-tab "bindscript" "bindscript" [:div "bindscripts here"])
